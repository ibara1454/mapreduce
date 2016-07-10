package mapreduce;

import mapreduce.Reducer;

@FunctionalInterface
public interface ReducerFactory<KeyInter, ValueInter, KeyOut, ValueOut> {
    Reducer<KeyInter, ValueInter, KeyOut, ValueOut> create();
}
