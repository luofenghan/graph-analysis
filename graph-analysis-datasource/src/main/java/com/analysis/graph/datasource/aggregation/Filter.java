package com.analysis.graph.datasource.aggregation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cwc on 2017/5/7 0007.
 */
public class Filter {
    private List<String> values;
    private Type type;

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setType(String type) {
        this.type = Type.valueOfSymbol(type);
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
        private String expFormat;

        Type(String symbol, String expFormat) {
            this.symbol = symbol;
            this.expFormat = expFormat;
        }

        private static final Map<String, Type> MAP = new HashMap<>(values().length);

        static {
            for (Type filter : values()) {
                MAP.put(filter.symbol, filter);
            }
        }

        public String getSymbol() {
            return symbol;
        }

        public static Type valueOfSymbol(String type) {
            return MAP.get(type.toLowerCase());
        }

        public String getFilterExp(String first, String second) {
            return String.format(expFormat, first, second);
        }

        public String getFilterExp(String first, String second, String thirty, String fourth) {
            return String.format(expFormat, first, second, thirty, fourth);
        }

        @Override
        public String toString() {
            return symbol;
        }
    }
}
