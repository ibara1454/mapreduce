package mapreduce;

import java.util.List;
import java.lang.Runnable;
import mapreduce.util.KeyValuePair;

public abstract class Reducer<KeyInter, ValueInter, KeyOut, ValueOut> implements Runnable {
    private KeyInter                       key;
    private List<ValueInter>               values;
    private KeyValuePair<KeyOut, ValueOut> output;

    public final void setInput(KeyInter key, List<ValueInter> values) {
        this.key = key;
        this.values = values;
    }

    public final KeyValuePair<KeyOut, ValueOut> getOutput() {
        return this.output;
    }

    @Override
    public void run() {
        this.output = this.reduce(this.key, this.values);
    }

    abstract public KeyValuePair<KeyOut, ValueOut> reduce(KeyInter key, List<ValueInter> values);
}
