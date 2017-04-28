package com.analysis.graph.datasource;

import com.analysis.graph.datasource.aggregation.AggregationResult;
import com.analysis.graph.datasource.aggregation.AggregationView;

/**
 * Created by cwc on 2017/4/27 0027.
 */
public interface DataAggregator {

    void aggregate(String columnName, AggregationView aggregationView) throws Exception;

    void aggregate(AggregationView aggregationView) throws Exception;

    AggregationResult getAggregateResult();

}
