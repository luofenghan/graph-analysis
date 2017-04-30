package com.analysis.graph.web.api.controller;

import com.analysis.graph.common.domain.dbo.Client;
import com.analysis.graph.common.domain.dbo.CronJob;
import com.analysis.graph.web.library.repository.CronJobRepository;
import com.analysis.graph.web.library.repository.SessionRepository;
import com.analysis.graph.web.library.service.CronJobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cwc on 2017/4/24 0024.
 */
@RestController
@RequestMapping("/api/cron-job")
public class CronJobAPI {
    @Resource
    private SessionRepository sessionRepository;

    @Resource
    private CronJobRepository cronJobRepository;

    @Resource
    private CronJobService cronJobService;

    @RequestMapping(value = "/type/list", method = RequestMethod.GET)
    public List<String> cronJobTypeSupportList() {
        return cronJobService.getSupportCronJobTypes();
    }

    @RequestMapping(method = RequestMethod.POST)
    public CronJob createCronJob(CronJob cronJob) {
        Client client = sessionRepository.getCurrentOnlineClient();
        cronJob.setClientId(client.getId());
        cronJob = cronJobRepository.insertCronJob(cronJob);
        cronJobService.configScheduler();
        return cronJob;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public CronJob modifyCronJob(CronJob cronJob) {
        cronJob = cronJobRepository.updateCronJob(cronJob);
        cronJobService.configScheduler();
        return cronJob;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<CronJob> getCronJobList() {
        Client client = sessionRepository.getCurrentOnlineClient();
        return cronJobRepository.queryCronJobList(client.getId());
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCronJob(@RequestParam Long id) {
        cronJobRepository.deleteCronJob(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/execute", method = RequestMethod.GET)
    public ResponseEntity<?> executeCronJob(@RequestParam Long id) {
        cronJobService.executeCronJob(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
