package com.analysis.graph.datasource.aggregation;

import com.analysis.graph.datasource.DataProvider;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by cwc on 2017/4/28 0028.
 */
public abstract class AbstractDataAggregator implements DataAggregator {
    protected DataProvider dataProvider;
    protected static final BiFunction<Aggregation, Map<String, Integer>, String> AGGREGATION_PARSER = (config, types) -> {
        String aggExp;
        if (config.getColumn().contains(" ")) {
            aggExp = config.getColumn();
            for (String column : types.keySet()) {
                aggExp = aggExp.replaceAll(" " + column + " ", " __view__." + column + " ");
            }
        } else {
            aggExp = "__view__." + config.getColumn();
        }
        return config.getFunction().getAggregateFunc(aggExp);
    };

    protected static final Function<Dimension, String> CONDITION_PARSER = (config) -> {
        if (CollectionUtils.isEmpty(config.getValues())) {
            return null;
        }
        Dimension.Filter filter = config.getFilter();
        switch (filter) {
            case EQUAL:
            case NOT_EQUAL:
                return filter.getFilterExp(config.getName(), IntStream.range(0, config.getValues().size()).boxed().map(i -> config.getValues().get(i)).collect(Collectors.joining(",")));
            case GREATER_THAN:
            case LESS_THAN:
            case NOT_LESS_THAN:
            case NOT_MORE_THAN:
                return filter.getFilterExp(config.getName(), config.getValues().get(0));
            case RANGE_A_$B:
            case RANGE_$A_B:
            case RANGE_A_B:
            case RANGE_$A_$B:
                if (config.getValues().size() >= 2) {
                    filter.getFilterExp(config.getName(), config.getValues().get(0), config.getName(), config.getValues().get(1));
                } else {
                    return null;
                }
            default:
                return null;
        }
    };

    public AbstractDataAggregator(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

}
