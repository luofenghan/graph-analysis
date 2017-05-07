package com.analysis.graph.datasource.aggregation;

import com.analysis.graph.datasource.DataProvider;
import org.springframework.util.CollectionUtils;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by cwc on 2017/4/28 0028.
 */
public abstract class AbstractAggregator implements Aggregator {
    protected DataProvider dataProvider;
    protected static final BiFunction<Metric, String[], String> METRIC_PARSER = (config, types) -> {
        String aggExp;
        if (config.getColumn().contains(" ")) {
            aggExp = config.getColumn();
            for (String column : types) {
                aggExp = aggExp.replaceAll(" " + column + " ", " __view__." + column + " ");
            }
        } else {
            aggExp = "__view__." + config.getColumn();
        }
        return config.getFunction().getMetricFunc(aggExp);
    };

    protected static final Function<Field, String> FILTER_PARSER = (config) -> {
        Filter filter = config.getFilter();
        if (Objects.isNull(filter) || CollectionUtils.isEmpty(filter.getValues())) {
            return null;
        }
        Filter.Type type = filter.getType();
        switch (type) {
            case EQUAL:
            case NOT_EQUAL:
                return type.getFilterExp(config.getName(), IntStream.range(0, filter.getValues().size()).boxed().map(i -> filter.getValues().get(i)).collect(Collectors.joining(",")));
            case GREATER_THAN:
            case LESS_THAN:
            case NOT_LESS_THAN:
            case NOT_MORE_THAN:
                return type.getFilterExp(config.getName(), filter.getValues().get(0));
            case RANGE_A_$B:
            case RANGE_$A_B:
            case RANGE_A_B:
            case RANGE_$A_$B:
                if (filter.getValues().size() >= 2) {
                    type.getFilterExp(config.getName(), filter.getValues().get(0), config.getName(), filter.getValues().get(1));
                } else {
                    return null;
                }
            default:
                return null;
        }
    };

    public AbstractAggregator(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

}
