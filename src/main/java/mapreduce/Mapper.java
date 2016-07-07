package mapreduce;

import java.util.*;
import mapreduce.util.OutputCollector;

public interface Mapper<KeyIn, ValueIn, KeyOut extends Comparable<KeyOut>, ValueOut> {
    public void map(KeyIn keyIn, ValueIn valuein, OutputCollector<KeyOut, ValueOut> output);
}
