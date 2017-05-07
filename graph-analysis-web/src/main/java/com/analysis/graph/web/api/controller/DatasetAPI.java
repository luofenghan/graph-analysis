package com.analysis.graph.web.api.controller;

import com.analysis.graph.common.domain.dbo.Client;
import com.analysis.graph.common.domain.dbo.Dataset;
import com.analysis.graph.datasource.aggregation.AggregationResult;
import com.analysis.graph.web.library.repository.DatasetRepository;
import com.analysis.graph.web.library.repository.SessionRepository;
import com.analysis.graph.web.library.service.AggregationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @Resource
    private AggregationService aggregationService;

    @RequestMapping(method = RequestMethod.POST)
    public Dataset createDataset(Dataset dataSet) {
        dataSet.setClientId(sessionRepository.getUserId());
        return dataSetRepository.saveDataset(dataSet);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Dataset updateDataset(Dataset dataSet) {
        return dataSetRepository.updateDataset(dataSet);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDataset(@PathVariable Long id) {
        dataSetRepository.removeDataset(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Dataset> getAllDatasetList() {
        Client client = sessionRepository.getCurrentOnlineClient();
        return dataSetRepository.listDatasetForClient(client.getId());
    }

    @RequestMapping(value = "/category/list", method = RequestMethod.GET)
    public List<String> getDatasetCategoryList() {
        return dataSetRepository.listDatasetForClient(sessionRepository.getUserId())
                .stream()
                .map(Dataset::getCategory)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/load", method = RequestMethod.GET)
    public AggregationResult loadDataByDataset(Dataset dataSet) {
        return aggregationService.aggregate(sessionRepository.getUserId(), dataSet);
    }

}
