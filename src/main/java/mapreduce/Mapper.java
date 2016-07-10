package mapreduce;

import java.util.List;
import java.lang.Runnable;
import mapreduce.util.KeyValuePair;

public abstract class Mapper<KeyIn, ValueIn, KeyInter, ValueInter> implements Runnable {
    private KeyIn                                       key;
    private ValueIn                                   value;
    private List<KeyValuePair<KeyInter, ValueInter>> output;

    public final void setInput(KeyIn key, ValueIn value) {
        this.key = key;
        this.value = value;
    }

    public final List<KeyValuePair<KeyInter, ValueInter>> getOutput() {
        return this.output;
    }

    @Override
    public void run() {
        this.output = this.map(this.key, this.value);
    }

    abstract public List<KeyValuePair<KeyInter, ValueInter>> map(KeyIn key, ValueIn value);
}
