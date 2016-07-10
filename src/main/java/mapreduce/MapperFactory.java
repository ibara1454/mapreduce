package mapreduce;

import mapreduce.Mapper;

@FunctionalInterface
public interface MapperFactory<KeyIn, ValueIn, KeyInter, ValueInter> {
    Mapper<KeyIn, ValueIn, KeyInter, ValueInter> create();
}
