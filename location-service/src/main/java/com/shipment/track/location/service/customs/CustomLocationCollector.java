package com.shipment.track.location.service.customs;

import com.shipment.track.location.service.utils.enums.OsmQueryParams;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class CustomLocationCollector
        implements Collector<OsmQueryParams, MultiValueMap<String,String>,MultiValueMap<String,String>> {

    public static CustomLocationCollector toOsmValueMap(){
        return new CustomLocationCollector();
    }
    /**
     * @return
     */
    @Override
    public Supplier<MultiValueMap<String, String>> supplier() {
        return LinkedMultiValueMap::new;
    }

    /**
     * @return
     */
    @Override
    public BiConsumer<MultiValueMap<String, String>, OsmQueryParams> accumulator() {
        return (multiValueMap,queryParams) -> multiValueMap.put(queryParams.getKey(),
                List.of(queryParams.getValue()));
    }

    /**
     * @return
     */
    @Override
    public BinaryOperator<MultiValueMap<String, String>> combiner() {
        return (map1,map2) -> {
            map1.addAll(map2);
            return map1;
        };
    }

    /**
     * @return
     */
    @Override
    public Function<MultiValueMap<String, String>, MultiValueMap<String, String>> finisher() {
        return item -> item;
    }

    /**
     * @return
     */
    @Override
    public Set<Characteristics> characteristics() {
        return Set.of();
    }
}
