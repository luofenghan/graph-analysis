package com.analysis.graph.datasource.aggregation;

import java.util.Comparator;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by cwc on 2017/5/4 0004.
 */
public class Measure {
    private String column;
    private Function function;

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public void setFunction(String function) {
        this.function = Function.valueOf(function.toUpperCase());
    }

    public enum Function {
        SUM("SUM(%s)"),
        AVG("AVG(%s)"),
        MAX("MAX(%s)"),
        MIN("MIN(%s)"),
        DISTINCT("COUNT(DISTINCT %s)"),
        COUNT("COUNT(%s)");
        private String format;

        Function(String format) {
            this.format = format;
        }

        public String getAggregateFunc(String param) {
            return String.format(format, param);
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
}
