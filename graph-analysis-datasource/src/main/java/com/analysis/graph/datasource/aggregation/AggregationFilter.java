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
    public boolean filter(final String[] row) {
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
        EQUAL("=", "%s IN ( %s )"),
        NOT_EQUAL("≠", "%s NOT IN ( '%s' )"),
        GREATER_THAN(">", "%s > '%s'"),
        LESS_THAN("<", "%s < '%s'"),
        NOT_LESS_THAN("≥", "%s >= '%s'"),
        NOT_MORE_THAN("≤", "%s <= '%s'"),
        RANGE_A_B("(a,b)", "( %s > '%s' AND %s < '%s'"),
        RANGE_A_$B("(a,b]", "( %s > '%s' AND %s <= '%s'"),
        RANGE_$A_B("[a,b)", "( %s >= '%s' AND %s < '%s'"),
        RANGE_$A_$B("[a,b]", "( %s >= '%s' AND %s <= '%s'");
        private String symbol;
        private String expressionFormat;

        Type(String symbol, String expressionFormat) {
            this.symbol = symbol;
            this.expressionFormat = expressionFormat;
        }

        private static final Map<String, Type> MAP = new HashMap<>(values().length);

        static {
            for (Type type : values()) {
                MAP.put(type.symbol, type);
            }
        }

        public static Type fromSymbol(String type) {
            return MAP.get(type);
        }

        public String expression(String first, String second) {
            return String.format(expressionFormat, first, second);
        }

        public String expression(String first, String second, String thirty, String fourth) {
            return String.format(expressionFormat, first, second, thirty, fourth);
        }
    }

}
