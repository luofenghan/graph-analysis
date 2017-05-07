package com.analysis.graph.web.library.service;

import com.analysis.graph.common.domain.dbo.Client;
import com.analysis.graph.common.domain.dbo.Cronjob;
import com.analysis.graph.web.library.component.CronjobExecutor;
import com.analysis.graph.web.library.constant.CronjobType;
import com.analysis.graph.web.library.repository.CronjobRepository;
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

import static com.analysis.graph.web.library.component.CronjobExecutor.*;

/**
 * Created by cwc on 2017/4/24 0024.
 */
@Service
public class CronjobService {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Resource
    private CronjobRepository cronJobRepository;

    @Resource
    private EmailService emailService;

    @Resource
    private SessionRepository sessionRepository;

    public void executeCronjob(Long jobId) {
        Cronjob cronJob = cronJobRepository.getCronjob(jobId);
        startJob(cronJob.getType(), cronJob.getConfig());
    }

    private void startJob(String cronJobType, String cronConfig) {
        switch (CronjobType.getJobType(cronJobType)) {
            case EMAIL:
                emailService.sendEmail(cronConfig);
                break;
        }
    }

    public synchronized void configScheduler() {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        try {
            scheduler.clear();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        Client client = sessionRepository.getCurrentOnlineClient();
        List<Cronjob> cronJobList = cronJobRepository.listCronjobForClient(client.getId());
        for (Cronjob cronJob : cronJobList) {
            try {
                long startTime = cronJob.getStartTime().getTime();
                long endTime = cronJob.getEndTime().getTime();
                if (endTime < System.currentTimeMillis()) {
                    continue;
                }
                JobDataMap jobDataMap = new JobDataMap();
                jobDataMap.put(CRON_JOB_CONFIG, cronJob.getConfig());
                jobDataMap.put(CRON_JOB_TYPE, cronJob.getType());
                jobDataMap.put(CRON_JOB_EXECUTOR, (CronjobExecutor) this::startJob);

                JobDetail jobDetail = JobBuilder.newJob()
                        .ofType(CronjobType.getJobExecutorClass(cronJob.getType()))
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

    public List<String> getSupportCronjobTypes() {
        return Arrays.stream(CronjobType.values())
                .map(cronJobType -> cronJobType.name().toLowerCase())
                .collect(Collectors.toList());
    }


}
