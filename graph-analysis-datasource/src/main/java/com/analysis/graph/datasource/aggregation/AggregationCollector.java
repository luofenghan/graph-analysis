package com.analysis.graph.datasource.aggregation;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by cwc on 2017/4/22 0022.
 */
public class AggregationCollector<T> implements Collector<T[], Object[], Double[]> {
    private List<ColumnIndex> valueList;
    private List<Collector> collectors;

    public static <T> AggregationCollector<T> getCollector(List<ColumnIndex> valueList) {
        return new AggregationCollector<>(valueList);
    }

    private AggregationCollector(List<ColumnIndex> valueList) {
        this.valueList = valueList;
        this.collectors = valueList.stream()
                .map(value -> value.getFunction().getCollector(this::toDouble))
                .collect(Collectors.toList());
    }

    private double toDouble(Object o) {
        if (o instanceof Double) {
            return (double) o;
        }
        return Double.parseDouble((String) o);
    }

    /**
     * @return 返回每个收集器的初始值
     */
    @Override
    public Supplier<Object[]> supplier() {
        return () -> collectors.stream()
                .map(collector -> collector.supplier().get())
                .collect(Collectors.toSet()).toArray();
    }

    @Override
    public BiConsumer<Object[], T[]> accumulator() {
        return (array, e) -> IntStream.range(0, array.length)
                .forEach(i ->
                        collectors.get(i)
                                .accumulator()
                                .accept(array[i], e[valueList.get(i).getIndex()])
                );
    }

    @Override
    public BinaryOperator<Object[]> combiner() {
        return (a, b) -> {
            IntStream.range(0, a.length).forEach(i -> a[i] = collectors.get(i).combiner().apply(a[i], b[i]));
            return a;
        };
    }

    @Override
    public Function<Object[], Double[]> finisher() {
        return (array) -> {
            Double[] result = new Double[array.length];
            IntStream.range(0, array.length)
                    .forEach(i -> {
                        Object o = collectors.get(i).finisher().apply(array[i]);
                        if (o instanceof Double) {
                            result[i] = (Double) o;
                        } else if (o instanceof Long) {
                            result[i] = ((Long) o).doubleValue();
                        } else if (o instanceof Integer) {
                            result[i] = ((Integer) o).doubleValue();
                        } else if (o instanceof Optional) {
                            result[i] = toDouble(((Optional) o).get());
                        }
                    });
            return result;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.emptySet();
    }
}
