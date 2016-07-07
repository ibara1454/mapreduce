package mapreduce;

import mapreduce.Mapper;

@FunctionalInterface
public interface MapperFactory {
    public Mapper create();
}
