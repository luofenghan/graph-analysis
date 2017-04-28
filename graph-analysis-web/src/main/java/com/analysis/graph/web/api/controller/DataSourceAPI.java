package com.analysis.graph.web.api.controller;

import com.alibaba.fastjson.JSON;
import com.analysis.graph.common.domain.dbo.Client;
import com.analysis.graph.common.domain.dbo.DataSourceInfo;
import com.analysis.graph.web.library.domain.dto.DataSourceInfoDTO;
import com.analysis.graph.web.library.repository.DataSourceRepository;
import com.analysis.graph.web.library.repository.SessionRepository;
import com.analysis.graph.web.library.service.DataSourceService;
import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

/**
 * Created by cwc on 2017/4/19 0019.
 */
@RestController
@RequestMapping("/api/data-source")
public class DataSourceAPI {
    @Resource
    private DataSourceService dataSourceService;

    @Resource
    private DataSourceRepository dataSourceRepository;

    @Resource
    private SessionRepository sessionRepository;

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public DataSourceInfoDTO saveDataSource(DataSourceInfoDTO dataSourceInfoDTO) {
        DataSourceInfo dataSourceInfo = new DataSourceInfo();
        dataSourceInfo.setName(dataSourceInfoDTO.getName());
        dataSourceInfo.setType(dataSourceInfoDTO.getType());
        dataSourceInfo.setConfig(JSON.toJSONString(dataSourceInfoDTO.getConfig()));
        dataSourceInfo.setClientId(dataSourceInfo.getClientId());

        dataSourceInfo = dataSourceRepository.insertDataSource(dataSourceInfo);

        return new DataSourceInfoDTO(dataSourceInfo);
    }

    @RequestMapping(method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public DataSourceInfoDTO updateDataSource(DataSourceInfoDTO dataSourceInfoDTO) {
        DataSourceInfo dataSourceInfo = new DataSourceInfo();
        dataSourceInfo.setId(dataSourceInfoDTO.getId());
        dataSourceInfo.setName(dataSourceInfoDTO.getName());
        dataSourceInfo.setType(dataSourceInfoDTO.getType());
        dataSourceInfo.setConfig(JSON.toJSONString(dataSourceInfoDTO.getConfig()));
        dataSourceInfo.setClientId(dataSourceInfo.getClientId());
        dataSourceInfo.setCreatedTime(dataSourceInfoDTO.getCreatedTime());

        dataSourceInfo = dataSourceRepository.updateDataSource(dataSourceInfo);

        return new DataSourceInfoDTO(dataSourceInfo);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDataSourceInfo(@RequestParam Integer id) {
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
    public List<DataSourceInfoDTO> dataSourceList() {
        Client client = sessionRepository.getCurrentOnlineClient();
        List<DataSourceInfo> dataSourceInfoList = dataSourceRepository.queryDataSourceListByClientId(client.getId());
        return Lists.transform(dataSourceInfoList, DataSourceInfoDTO::new);
    }

    @RequestMapping(value = "/type/list", method = RequestMethod.GET)
    public List<String> dataSourceTypeList() {
        return dataSourceService.getDataSourceSupportList();
    }


}
