package com.analysis.graph.web.api.controller;

import com.analysis.graph.common.domain.dbo.Client;
import com.analysis.graph.common.domain.dbo.DataSet;
import com.analysis.graph.web.library.repository.DataSetRepository;
import com.analysis.graph.web.library.repository.SessionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by cwc on 2017/4/23 0023.
 */
@RestController
@RequestMapping("/api/data-set")
public class DataSetAPI {

    @Resource
    private DataSetRepository dataSetRepository;

    @Resource
    private SessionRepository sessionRepository;

    @RequestMapping(method = RequestMethod.POST)
    public DataSet createDataSet(DataSet dataSet) {
        Client client = sessionRepository.getCurrentOnlineClient();
        dataSet.setClientId(client.getId());
        return dataSetRepository.insertDataSet(dataSet);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public DataSet updateDataSet(DataSet dataSet) {
        return dataSetRepository.updateDataSet(dataSet);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDataSet(@RequestParam Long id) {
        dataSetRepository.deleteDataSet(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<DataSet> getAllDataSets() {
        Client client = sessionRepository.getCurrentOnlineClient();
        return dataSetRepository.queryClientDataSet(client.getId());
    }

    @RequestMapping(value = "/category/list", method = RequestMethod.GET)
    public List<String> getDataSetCategoryList() {
        Client client = sessionRepository.getCurrentOnlineClient();
        return dataSetRepository.queryClientDataSet(client.getId())
                .stream()
                .map(DataSet::getCategoryName)
                .collect(Collectors.toList());
    }


}
