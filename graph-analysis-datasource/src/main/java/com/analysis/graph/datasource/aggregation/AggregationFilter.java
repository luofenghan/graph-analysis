package com.analysis.graph.datasource.aggregation;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;


/**
 * Created by cwc on 2017/4/23 0023.
 */
public class AggregationFilter {
    private List<AggregationView.DimensionView> ruleList;
    private Map<String, Integer> columnIndex;

    public AggregationFilter(AggregationView aggregateConfig, Map<String, Integer> columnIndex) {
        ruleList = new ArrayList<>();
        if (aggregateConfig != null) {
            ruleList.addAll(aggregateConfig.getColumns());
            ruleList.addAll(aggregateConfig.getRows());
            ruleList.addAll(aggregateConfig.getFilters());
        }
        this.columnIndex = columnIndex;
    }

    @SuppressWarnings("unchecked")
    public boolean filter(String[] row) {
        return ruleList.stream().allMatch(rule -> {
            if (CollectionUtils.isEmpty(rule.getValues())) {
                return true;
            }
            String value = row[columnIndex.get(rule.getName())];
            if (StringUtils.isEmpty(value)) {
                return false;
            }
            String a = rule.getValues().get(0);
            String b = rule.getValues().size() >= 2 ? rule.getValues().get(1) : null;

            Comparator<String> stringComparator = Comparator.naturalOrder();
            switch (rule.getFilter()) {
                case EQUAL:
                    return rule.getValues().stream().anyMatch(e -> e.equals(value));
                case NOT_EQUAL:
                    return rule.getValues().stream().noneMatch(e -> e.equals(value));
                case GREATER_THAN:
                    return stringComparator.compare(value, a) > 0;
                case LESS_THAN:
                    return stringComparator.compare(value, a) < 0;
                case NOT_LESS_THAN:
                    return stringComparator.compare(value, a) >= 0;
                case NOT_MORE_THAN:
                    return stringComparator.compare(value, a) <= 0;
                case RANGE_A_$B:
                    return (rule.getValues().size() >= 2)
                            && (stringComparator.compare(value, a) > 0)
                            && (stringComparator.compare(value, b) <= 0);
                case RANGE_$A_B:
                    return (rule.getValues().size() >= 2)
                            && (stringComparator.compare(value, a) >= 0)
                            && (stringComparator.compare(value, b) < 0);
                case RANGE_A_B:
                    return (rule.getValues().size() >= 2)
                            && (stringComparator.compare(value, a) > 0)
                            && (stringComparator.compare(value, b) < 0);
                case RANGE_$A_$B:
                    return (rule.getValues().size() >= 2)
                            && (stringComparator.compare(value, a) >= 0)
                            && (stringComparator.compare(value, b) <= 0);
            }
            return true;
        });
    }

    private Comparable[] tryToNumber(String... args) {
        if (Arrays.stream(args).allMatch(NumberUtils::isNumber)) {
            return Arrays.stream(args)
                    .mapToDouble(Double::parseDouble)
                    .boxed()
                    .toArray(Double[]::new);
        } else {
            return args;
        }
    }

    public enum Type {
        EQUAL("="),
        NOT_EQUAL("≠"),
        GREATER_THAN(">"),
        LESS_THAN("<"),
        NOT_LESS_THAN("≥"),
        NOT_MORE_THAN("≤"),
        RANGE_A_B("(a,b)"),
        RANGE_A_$B("(a,b]"),
        RANGE_$A_B("[a,b)"),
        RANGE_$A_$B("[a,b]");
        private String operation;

        Type(String operation) {
            this.operation = operation;
        }

        private static final Map<String, Type> MAP = new HashMap<>();

        static {
            for (Type type : values()) {
                MAP.put(type.operation, type);
            }
        }

        public static Type from(String type) {
            return MAP.get(type);
        }
    }

}