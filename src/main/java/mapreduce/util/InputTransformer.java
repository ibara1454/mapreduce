package mapreduce.util;

import java.util.List;
import mapreduce.util.KeyValuePair;

@FunctionalInterface
public interface InputTransformer<Key, Value> {
    List<KeyValuePair<Key, Value>> transform(String text);
}
