package com.analysis.graph.datasource;

import com.analysis.graph.datasource.aggregation.AggregationResult;
import com.analysis.graph.datasource.aggregation.AggregationView;

import java.sql.SQLException;

/**
 * Created by cwc on 2017/4/27 0027.
 */
public interface DataAggregator {

    void aggregate(String columnName, AggregationView aggregationView) throws SQLException;

    void aggregate(AggregationView aggregationView) throws SQLException;

    AggregationResult getAggregateResult();

    String getAggregationSql(AggregationView av) throws SQLException;

}
