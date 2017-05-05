package com.analysis.graph.web.library.constant;

import com.analysis.graph.web.library.component.CronjobExecutor;
import org.quartz.Job;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cwc on 2017/4/24 0024.
 */
public enum CronjobType {
    EMAIL(CronjobExecutor.EmailJobExecutor.class);
    private Class<? extends Job> jobExecutorClass;
    private static final Map<String, CronjobType> LIBRARY = new HashMap<>();

    static {
        for (CronjobType job : values()) {
            LIBRARY.put(job.name().toLowerCase(), job);
        }
    }

    CronjobType(Class<? extends Job> jobTypeClass) {
        this.jobExecutorClass = jobTypeClass;
    }

    public static Class<? extends Job> getJobExecutorClass(String jobType) {
        return getJobType(jobType).jobExecutorClass;
    }

    public static CronjobType getJobType(String jobType) {
        return LIBRARY.get(jobType);
    }

}
