package com.analysis.graph.web.api.controller;

import com.analysis.graph.common.domain.dbo.Client;
import com.analysis.graph.common.domain.dbo.Cronjob;
import com.analysis.graph.web.library.repository.CronjobRepository;
import com.analysis.graph.web.library.repository.SessionRepository;
import com.analysis.graph.web.library.service.CronjobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cwc on 2017/4/24 0024.
 */
@RestController
@RequestMapping("/api/cronjob")
public class CronjobAPI {
    @Resource
    private SessionRepository sessionRepository;

    @Resource
    private CronjobRepository cronJobRepository;

    @Resource
    private CronjobService cronJobService;

    @RequestMapping(value = "/type/list", method = RequestMethod.GET)
    public List<String> cronJobTypeSupportList() {
        return cronJobService.getSupportCronjobTypes();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Cronjob createCronjob(Cronjob cronJob) {
        Client client = sessionRepository.getCurrentOnlineClient();
        cronJob.setClientId(client.getId());
        cronJob = cronJobRepository.saveCronjob(cronJob);
        cronJobService.configScheduler();
        return cronJob;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Cronjob modifyCronjob(Cronjob cronJob) {
        cronJob = cronJobRepository.updateCronjob(cronJob);
        cronJobService.configScheduler();
        return cronJob;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Cronjob> getCronjobList() {
        Client client = sessionRepository.getCurrentOnlineClient();
        return cronJobRepository.listCronjobForClient(client.getId());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCronjob(@PathVariable Long id) {
        cronJobRepository.removeCronjob(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/execute", method = RequestMethod.GET)
    public ResponseEntity<?> executeCronjob(@RequestParam Long id) {
        cronJobService.executeCronjob(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
