package mapreduce.job;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import mapreduce.job.JobBuilder;

public class ParallelJobBuilder<KIn, VIn, KInter, VInter, KOut, VOut> 
    extends JobBuilder<KIn, VIn, KInter, VInter, KOut, VOut> {
    @Override
    protected void executeTasks(List<? extends Runnable> tasks) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        tasks.stream().forEach(executorService::execute);
        executorService.shutdown();
    }
}
