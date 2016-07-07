package mapreduce;

import mapreduce.Reducer;

@FunctionalInterface
public interface ReducerFactory {
    public Reducer create();
}
