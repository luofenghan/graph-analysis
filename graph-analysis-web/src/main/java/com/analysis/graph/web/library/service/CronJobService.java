package com.analysis.graph.web.library.service;

import com.analysis.graph.common.domain.dbo.Client;
import com.analysis.graph.common.domain.dbo.CronJob;
import com.analysis.graph.web.library.component.CronJobExecutor;
import com.analysis.graph.web.library.constant.CronJobType;
import com.analysis.graph.web.library.repository.CronJobRepository;
import com.analysis.graph.web.library.repository.SessionRepository;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.analysis.graph.web.library.component.CronJobExecutor.*;

/**
 * Created by cwc on 2017/4/24 0024.
 */
@Service
public class CronJobService {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Resource
    private CronJobRepository cronJobRepository;

    @Resource
    private EmailService emailService;

    @Resource
    private SessionRepository sessionRepository;

    public void executeCronJob(Long jobId) {
        Optional<CronJob> cronJobOptional = cronJobRepository.queryCronJob(jobId);
        if (!cronJobOptional.isPresent()) {
            throw new IllegalArgumentException("Can not find cron job with id:" + jobId);
        }
        CronJob cronJob = cronJobOptional.get();
        startJob(cronJob.getType(), cronJob.getConfig());
    }

    private void startJob(String cronJobType, String cronConfig) {
        switch (CronJobType.getJobType(cronJobType)) {
            case EMAIL:
                emailService.sendEmail(cronConfig);
                break;
        }
    }

    public void configScheduler() {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            scheduler.clear();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        Client client = sessionRepository.getCurrentOnlineClient();
        List<CronJob> cronJobList = cronJobRepository.queryCronJobList(client.getId());
        for (CronJob cronJob : cronJobList) {
            try {
                long startTime = cronJob.getStartTime().getTime();
                long endTime = cronJob.getEndTime().getTime();
                if (endTime < System.currentTimeMillis()) {
                    continue;
                }
                JobDataMap jobDataMap = new JobDataMap();
                jobDataMap.put(CRON_JOB_CONFIG, cronJob.getConfig());
                jobDataMap.put(CRON_JOB_TYPE, cronJob.getType());
                jobDataMap.put(CRON_JOB_EXECUTOR, (CronJobExecutor) this::startJob);

                JobDetail jobDetail = JobBuilder.newJob()
                        .ofType(CronJobType.getJobExecutorClass(cronJob.getType()))
                        .withIdentity(String.valueOf(cronJob.getId()))
                        .usingJobData(jobDataMap)
                        .build();


                Trigger trigger = TriggerBuilder.newTrigger()
                        .startAt(new Date().getTime() < startTime ? cronJob.getStartTime() : new Date())
                        .endAt(cronJob.getEndTime())
                        .build();

                scheduler.scheduleJob(jobDetail, trigger);
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }

    }

    public List<String> getSupportCronJobTypes() {
        return Arrays.stream(CronJobType.values())
                .map(cronJobType -> cronJobType.name().toLowerCase())
                .collect(Collectors.toList());
    }


}
