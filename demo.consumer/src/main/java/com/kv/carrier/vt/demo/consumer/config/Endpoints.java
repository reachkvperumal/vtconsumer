package com.kv.carrier.vt.demo.consumer.config;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Endpoints {

    RANDOM_ONE("/todos/1"),
    RANDOM_TWO("/posts"),
    RANDOM_THREE("/posts/1"),
    RANDOM_FOUR("/posts/1/comments"),
    RANDOM_FIVE("/comments?postId=1"),
    GET_SUMMARY("/stock/v2/get-summary?symbol=%s&region=US");

    private final String url;
    Endpoints(String url){
        this.url = url;
    }
    public final static Map<Endpoints,String> LOOKUP = Arrays.stream(Endpoints.values()).collect(Collectors.toMap(Function.identity(), v -> v.url));
}
