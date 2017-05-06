package com.analysis.graph.datasource.aggregation;

import java.sql.SQLException;

/**
 * Created by cwc on 2017/4/27 0027.
 */
public interface Aggregator {

    AggregationResult doAggregation(AggregationView aggregationView) throws SQLException;

    String getAggregationSql(AggregationView av) throws SQLException;

}
