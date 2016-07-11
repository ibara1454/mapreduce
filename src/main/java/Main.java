import java.lang.*;
import java.util.*;
import java.io.*;
import java.util.stream.*;
import mapreduce.*;
import mapreduce.util.*;
import mapreduce.job.*;
import static java.util.Comparator.*;

class TokenizerMapper extends Mapper<String, String, String, Integer> {
    private static String delimiter = " ,:";
    @Override
    public List<KeyValuePair<String, Integer>> map(String key, String value) {
        StringTokenizer tokenizer = new StringTokenizer(value, delimiter);
        List<KeyValuePair<String, Integer>> list = new ArrayList<>();
        while(tokenizer.hasMoreTokens()) {
            String word = tokenizer.nextToken().toLowerCase();
            if(word.length() > 0) {
                list.add(new KeyValuePair<>(word.toLowerCase(), 1));
            }
        }
        return list;
    }
}

class IntSumReducer extends Reducer<String, Integer, String, Integer> {
    @Override
    public KeyValuePair<String, Integer> reduce(String key, List<Integer> values) {
        Integer sum = values.stream().mapToInt(Integer::intValue).sum();
        return new KeyValuePair<>(key, sum);
    }
}

class SplitByPeriodTransformer implements InputTransformer<String, String> {
    @Override
    public List<KeyValuePair<String, String>> transform(String text) {
        // Split input string by periods
        String[] parts = text.split("\\.", 0);
        return Stream.of(parts)
            .collect(
                Collectors.mapping(
                    part -> new KeyValuePair<>("", part),
                    Collectors.toList()));
    }
}

class CombineTransformer implements OutputTransformer<String, Integer> {
    @Override
    public String transform(List<KeyValuePair<String, Integer>> pairs) {
        return pairs.stream()
            .sorted(comparing(KeyValuePair<String, Integer>::getValue).reversed())
            .collect(
                Collectors.mapping(
                    pair -> pair.getKey() + ": " + pair.getValue(),
                    Collectors.joining("\n")));
    }
}

public class Main {
    static String text = "MapReduce is a programming model and an associated implementation for processing and generating large data sets. Users specify a map function that processes a key/value pair to generate a set of intermediate key/value pairs, and a reduce function that merges all intermediate values associated with the same intermediate key. Many real world tasks are expressible in this model, as shown in the paper. Programs written in this functional style are automatically parallelized and executed on a large cluster of commodity machines. The run-time system takes care of the details of partitioning the input data, scheduling the program's execution across a set of machines, handling machine failures, and managing the required inter-machine communication. This allows programmers without any experience with parallel and distributed systems to easily utilize the resources of a large distributed system. Our implementation of MapReduce runs on a large cluster of commodity machines and is highly scalable: a typical MapReduce computation processes many terabytes of data on thousands of machines. Programmers find the system easy to use: hundreds of MapReduce programs have been implemented and upwards of one thousand MapReduce jobs are executed on Google's clusters every day.";

    public static void main(String[] args) throws IOException {
        JobBuilder<String, String, String, Integer, String, Integer> jobBuilder = new SingleJobBuilder<>();
        jobBuilder.setMapper(() -> new TokenizerMapper());
        jobBuilder.setReducer(() -> new IntSumReducer());
        jobBuilder.setInputTransformer(new SplitByPeriodTransformer());
        jobBuilder.setOutputTransformer(new CombineTransformer());
        jobBuilder.setInput(text);
        jobBuilder.run();

        System.out.println(jobBuilder.getOutput());
    }
}
