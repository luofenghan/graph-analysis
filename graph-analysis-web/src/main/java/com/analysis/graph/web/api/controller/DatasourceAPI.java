package com.analysis.graph.web.api.controller;

import com.alibaba.fastjson.JSON;
import com.analysis.graph.common.domain.dbo.Client;
import com.analysis.graph.common.domain.dbo.Datasource;
import com.analysis.graph.web.library.repository.DatasourceRepository;
import com.analysis.graph.web.library.repository.SessionRepository;
import com.analysis.graph.web.library.service.DatasourceService;
import com.google.common.base.Functions;
import com.google.common.collect.Maps;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

/**
 * Created by cwc on 2017/4/19 0019.
 */
@RestController
@RequestMapping("/api/datasource")
public class DatasourceAPI {
    @Resource
    private DatasourceService dataSourceService;

    @Resource
    private DatasourceRepository dataSourceRepository;

    @Resource
    private SessionRepository sessionRepository;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Datasource saveDataSource(Datasource dataSourceInfo) {
        return dataSourceRepository.insertDataSource(dataSourceInfo);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Datasource updateDataSource(Datasource dataSourceInfo) {
        return dataSourceRepository.updateDataSource(dataSourceInfo);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDatasource(@PathVariable Integer id) {
        dataSourceRepository.deleteDataSource(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseEntity<?> testDataSource(@RequestParam("uri") String uri,
                                            @RequestParam(required = false, defaultValue = "{}") String params) {

        dataSourceService.getFreshData(sessionRepository.getUserId(), URI.create(uri),
                Maps.transformValues(JSON.parseObject(params), Functions.toStringFunction()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Datasource> dataSourceList() {
        Client client = sessionRepository.getCurrentOnlineClient();
        return dataSourceRepository.queryDataSourceListByClientId(client.getId());
    }

    @RequestMapping(value = "/type/list", method = RequestMethod.GET)
    public List<String> dataSourceTypeList() {
        return dataSourceService.getDataSourceSupportList();
    }


}
