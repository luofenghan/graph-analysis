package com.analysis.graph.datasource.aggregation;

import com.analysis.graph.datasource.DataProvider;
import com.analysis.graph.datasource.aggregation.AggregationView.ValueView;
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
    protected static final BiFunction<ValueView, Map<String, Integer>, String> AGGREGATION_PARSER = (config, types) -> {
        String aggExp;
        if (config.getColumn().contains(" ")) {
            aggExp = config.getColumn();
            for (String column : types.keySet()) {
                aggExp = aggExp.replaceAll(" " + column + " ", " __view__." + column + " ");
            }
        } else {
            aggExp = "__view__." + config.getColumn();
        }
        return config.getMethod().getExpression(aggExp);
    };

    protected static final Function<AggregationView.DimensionView, String> CONDITION_PARSER = (config) -> {
        if (CollectionUtils.isEmpty(config.getValues())) {
            return null;
        }
        AggregationFilter.Type filterType = config.getFilter();
        switch (filterType) {
            case EQUAL:
            case NOT_EQUAL:
                return filterType.expression(config.getName(), IntStream.range(0, config.getValues().size()).boxed().map(i -> config.getValues().get(i)).collect(Collectors.joining(",")));
            case GREATER_THAN:
            case LESS_THAN:
            case NOT_LESS_THAN:
            case NOT_MORE_THAN:
                return filterType.expression(config.getName(), config.getValues().get(0));
            case RANGE_A_$B:
            case RANGE_$A_B:
            case RANGE_A_B:
            case RANGE_$A_$B:
                if (config.getValues().size() >= 2) {
                    filterType.expression(config.getName(), config.getValues().get(0), config.getName(), config.getValues().get(1));
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