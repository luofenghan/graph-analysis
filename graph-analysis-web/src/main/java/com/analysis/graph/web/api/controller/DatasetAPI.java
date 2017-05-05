package com.analysis.graph.web.api.controller;

import com.analysis.graph.common.domain.dbo.Client;
import com.analysis.graph.common.domain.dbo.Dataset;
import com.analysis.graph.web.library.repository.DatasetRepository;
import com.analysis.graph.web.library.repository.SessionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by cwc on 2017/4/23 0023.
 */
@RestController
@RequestMapping("/api/dataset")
public class DatasetAPI {

    @Resource
    private DatasetRepository dataSetRepository;

    @Resource
    private SessionRepository sessionRepository;

    @RequestMapping(method = RequestMethod.POST)
    public Dataset createDataset(Dataset dataSet) {
        Client client = sessionRepository.getCurrentOnlineClient();
        dataSet.setClientId(client.getId());
        return dataSetRepository.insertDataset(dataSet);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Dataset updateDataset(Dataset dataSet) {
        return dataSetRepository.updateDataset(dataSet);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDataset(@PathVariable Long id) {
        dataSetRepository.deleteDataset(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Dataset> getAllDatasets() {
        Client client = sessionRepository.getCurrentOnlineClient();
        return dataSetRepository.queryClientDataset(client.getId());
    }

    @RequestMapping(value = "/category/list", method = RequestMethod.GET)
    public List<String> getDatasetCategoryList() {
        Client client = sessionRepository.getCurrentOnlineClient();
        return dataSetRepository.queryClientDataset(client.getId())
                .stream()
                .map(Dataset::getCategory)
                .collect(Collectors.toList());
    }


}
