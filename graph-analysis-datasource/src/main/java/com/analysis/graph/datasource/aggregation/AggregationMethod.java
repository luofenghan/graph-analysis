package com.analysis.graph.datasource.aggregation;

import java.util.Comparator;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by cwc on 2017/4/22 0022.
 */
public enum AggregationMethod {
    SUM,
    AVG,
    MAX,
    MIN,
    DISTINCT,
    COUNT;


    private static double toDouble(Object o) {
        if (o instanceof Double) {
            return (double) o;
        }
        return Double.parseDouble((String) o);
    }

    public <T> Collector getCollector(ToDoubleFunction<? super T> function) {
        switch (this) {
            case SUM:
                return Collectors.summingDouble(function);
            case AVG:
                return Collectors.averagingDouble(function);
            case MAX:
                return Collectors.maxBy(Comparator.comparingDouble(function));
            case MIN:
                return Collectors.minBy(Comparator.comparingDouble(function));
            case DISTINCT:
                return Collectors.toSet();
            case COUNT:
                return Collectors.counting();
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
