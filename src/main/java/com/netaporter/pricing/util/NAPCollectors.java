package com.netaporter.pricing.util;


import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * Created by j.hoare on 4/22/14.
 */
public class NAPCollectors {

    public static <T, A extends List<T>> Collector<T, A, List<T>> toImmutableList(Supplier<A> collectionFactory) {
        return Collector.of(collectionFactory, List::add, (left, right) -> {
            left.addAll(right);
            return left;
        }, Collections::unmodifiableList);
    }


}