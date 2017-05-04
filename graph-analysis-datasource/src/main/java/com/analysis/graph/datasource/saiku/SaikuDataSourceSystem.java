package com.analysis.graph.datasource.saiku;

import com.analysis.graph.datasource.*;
import com.analysis.graph.datasource.aggregation.DataAggregator;
import com.analysis.graph.datasource.DataProvider;

import java.util.Map;

/**
 * Created by cwc on 2017/4/28 0028.
 */
@DataSource(type = DataSourceType.SAIKU)
public class SaikuDataSourceSystem extends DataSourceSystem {
    @Override
    public boolean isAggregatable() {
        return false;
    }

    @Override
    public DataProvider getDataProvider(Map<String, String> query) throws Exception {
        return null;
    }

    @Override
    public DataAggregator getDataAggregator(DataProvider dataProvider) {
        return null;
    }


}
