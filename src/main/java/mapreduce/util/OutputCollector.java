package mapreduce.util;

import java.lang.*;
import java.util.*;

public class OutputCollector<Key extends Comparable<Key>, Value> {
    private List<KeyValuePair<Key, Value>> list;

    public OutputCollector() {
        this.list = new LinkedList<>();
    }

    public void collect(Key key, Value value) {
        this.list.add(new KeyValuePair<Key, Value>(key, value));
    }
}

class KeyValuePair<K extends Comparable<K>, V> implements Comparable<KeyValuePair<K, V>> {
    private final K key;
    private final V value;

    public KeyValuePair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() { return this.key; }
    public V getValue() { return this.value; }

    @Override
    public int compareTo(KeyValuePair<K, V> rhs) {
        return this.getKey().compareTo(rhs.getKey());
    }
}
