package com.analysis.graph.web.library.service;

import com.analysis.graph.datasource.DataProvider;
import com.analysis.graph.datasource.DataSourceSystem;
import com.analysis.graph.datasource.DsType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by cwc on 2017/4/19 0019.
 */
@Service
public class DatasourceService {
    private static final Logger logger = LoggerFactory.getLogger(DatasourceService.class);


    public List<String> getDataSourceSupportList() {
        return Arrays.stream(DsType.values()).map(type -> type.name().toLowerCase()).collect(Collectors.toList());
    }

    public String[] getColumnLabels(Integer id, URI uri, Map<String, String> query) {
        DataSourceSystem dataSourceSystem = DataSourceSystem.get(id, uri);
        try (DataProvider dataProvider = dataSourceSystem.getDataProvider(query)) {
            return dataProvider.getColumnLabels();
        } catch (Exception e) {
            throw new IllegalStateException();
        }
    }

    @Cacheable(value = "data-provider-cache", key = "#root.args.uri.toString() + #root.args.query")
    public Object[][] getCacheData(Integer id, URI uri, Map<String, String> query) {
        return getFreshData(id, uri, query);
    }

    public Object[][] getFreshData(Integer id, URI uri, Map<String, String> query) {
        DataSourceSystem dataSourceSystem = DataSourceSystem.get(id, uri);
        try (DataProvider dataProvider = dataSourceSystem.getDataProvider(query)) {
            return dataProvider.readFully();
        } catch (Exception e) {
            throw new IllegalStateException();
        }
    }
}
