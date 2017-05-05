package com.analysis.graph.web.library.component;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by cwc on 2017/4/24 0024.
 */
public interface CronjobExecutor {
    String CRON_JOB_CONFIG = "config";
    String CRON_JOB_TYPE = "type";
    String CRON_JOB_EXECUTOR = "executor";

    void execute(String type, String jsonConfig);

    class EmailJobExecutor implements Job {

        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
            CronjobExecutor executor = (CronjobExecutor) jobDataMap.get(CRON_JOB_EXECUTOR);

            executor.execute(jobDataMap.getString(CRON_JOB_TYPE), jobDataMap.getString(CRON_JOB_CONFIG));
        }
    }
}
