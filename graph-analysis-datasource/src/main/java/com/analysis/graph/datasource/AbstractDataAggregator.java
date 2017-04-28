package com.analysis.graph.datasource;

import com.analysis.graph.datasource.aggregation.AggregationResult;
import com.analysis.graph.datasource.aggregation.AggregationView;

/**
 * Created by cwc on 2017/4/28 0028.
 */
public abstract class AbstractDataAggregator implements DataAggregator {
    @Override
    public void aggregate(String columnName, AggregationView aggregationView) throws Exception {

    }

    @Override
    public void aggregate(AggregationView aggregationView) throws Exception {

    }

    @Override
    public AggregationResult getAggregateResult() {
        return null;
    }
}
