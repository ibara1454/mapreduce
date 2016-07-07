package mapreduce.job;

import mapreduce.MapperFactory;
import mapreduce.ReducerFactory;

public abstract class JobBuilder {
    protected final MapperFactory mapperFactory;
    protected final ReducerFactory reducerFactory;

    public void setMapperFactory(MapperFactory mf) {
        this.mapperFactory = mf;
    }

    public void setReducerFactory(ReducerFactory rf) {
        this.reducerFactory = rf;
    }

    // abstract public void splitFile(String file);
    abstract public void map();
    abstract public void shuffle();
    abstract public void reduce();
    abstract public void result();
}

