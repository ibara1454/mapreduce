package mapreduce.job;

import java.util.List;
import mapreduce.job.JobBuilder;

public class SingleJobBuilder<KIn, VIn, KInter, VInter, KOut, VOut> 
    extends JobBuilder<KIn, VIn, KInter, VInter, KOut, VOut> {
    @Override
    protected void executeTasks(List<? extends Runnable> tasks) {
        tasks.stream().forEach(Runnable::run);
    }
}
