package mapreduce.job;

import java.util.*;
import java.util.stream.Collectors;
import java.util.concurrent.ExecutorService;
import java.lang.Runnable;
import mapreduce.*;
import mapreduce.util.*;

public abstract class JobBuilder<KIn, VIn, KInter, VInter, KOut, VOut> {
    private MapperFactory<KIn, VIn, KInter, VInter>    mapperFactory;
    private ReducerFactory<KInter, VInter, KOut, VOut> reducerFactory;
    private InputTransformer<KIn, VIn>                 inputTransformer;
    private OutputTransformer<KOut, VOut>              outputTransformer;
    private String                                     inputData;
    private String                                     outputData;

    public final void setMapper(MapperFactory<KIn, VIn, KInter, VInter> mapperFactory) {
        this.mapperFactory = mapperFactory;
    }

    public final void setReducer(ReducerFactory<KInter, VInter, KOut, VOut> reducerFactory) {
        this.reducerFactory = reducerFactory;
    }

    public final void setInput(String text) {
        this.inputData = text;
    }

    public final void setInputTransformer(InputTransformer<KIn, VIn> it) {
        this.inputTransformer = it;
    }

    public final void setOutputTransformer(OutputTransformer<KOut, VOut> ot) {
        this.outputTransformer = ot;
    }

    public final String getOutput() {
        return this.outputData;
    }

    public final void run() {
        List<KeyValuePair<KIn, VIn>> contents = this.inputTransformer.transform(this.inputData);
        List<Mapper<KIn, VIn, KInter, VInter>> mappers = contents.stream()
            .map(
                pair -> {
                    Mapper<KIn, VIn, KInter, VInter> mapper = mapperFactory.create();
                    mapper.setInput(pair.getKey(), pair.getValue());
                    return mapper;
                }
            )
            .collect(Collectors.toList());
        this.executeTasks(mappers);
        Map<KInter, List<VInter>> interResult = mappers.stream()
            .map(Mapper<KIn, VIn, KInter, VInter>::getOutput)
            .flatMap(List::stream)
            .collect(
                Collectors.groupingBy(KeyValuePair<KInter, VInter>::getKey,
                    Collectors.mapping(KeyValuePair<KInter, VInter>::getValue,
                        Collectors.toList()
                    )
                )
            );
        List<Reducer<KInter, VInter, KOut, VOut>> reducers = interResult.entrySet().stream()
            .map(
                entry -> {
                    Reducer<KInter, VInter, KOut, VOut> reducer = reducerFactory.create();
                    reducer.setInput(entry.getKey(), entry.getValue());
                    return reducer;
                }
            )
            .collect(Collectors.toList());
        this.executeTasks(reducers);
        List<KeyValuePair<KOut, VOut>> result = reducers.stream()
            .map(Reducer<KInter, VInter, KOut, VOut>::getOutput)
            .collect(Collectors.toList());
        this.outputData = this.outputTransformer.transform(result);
    }

    abstract protected void executeTasks(List<? extends Runnable> tasks);
}
