package mapreduce.util;

import java.util.List;
import mapreduce.util.KeyValuePair;

@FunctionalInterface
public interface OutputTransformer<Key, Value> {
    String transform(List<KeyValuePair<Key, Value>> pairs);
}
