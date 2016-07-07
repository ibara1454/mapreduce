package mapreduce;

public interface Reducer<KeyIn, ValueIn, KeyOut, ValueOut> {
    public void reduce(KeyIn keyin, ValueIn valuein);
}
