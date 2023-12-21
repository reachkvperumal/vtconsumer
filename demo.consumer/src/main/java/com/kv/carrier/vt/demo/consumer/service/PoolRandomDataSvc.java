package com.kv.carrier.vt.demo.consumer.service;

import com.kv.carrier.vt.demo.consumer.config.Endpoints;
import com.kv.carrier.vt.demo.consumer.dto.SummaryResponse;
import com.kv.carrier.vt.demo.consumer.exception.VTConsumerSubTaskException;
import com.kv.carrier.vt.demo.consumer.exception.VTConsumerThreadException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestClient;

import java.nio.charset.Charset;
import java.util.concurrent.StructuredTaskScope;
import java.util.function.Supplier;
@Log4j2
@Service
public class PoolRandomDataSvc implements InitializingBean {

    private final static String API_KEY = "X-RapidAPI-Key";
    private final RestClient client;

    private final RestClient yahoo;

    private String key;

    private final Resource resource;

    public PoolRandomDataSvc(@Value("${demo.url}") String url, @Autowired RestClient.Builder builder,
                             @Value("${demo.summary}") String ticker, @Autowired RestClient.Builder yahoo, @Value("${demo.keyFileName}") Resource resource) {
        this.client = builder.baseUrl(url).build();
        this.yahoo = yahoo.baseUrl(ticker).build();
        this.resource = resource;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.key = StreamUtils.copyToString(resource.getInputStream(), Charset.defaultCharset());
        log.info("KEY: {}",this.key);
    }

    public SummaryResponse apply(String ticker) {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            Supplier<String> random1 = scope.fork(() -> client.get().uri(Endpoints.LOOKUP.get(Endpoints.RANDOM_ONE)).retrieve().toEntity(String.class).getBody());
            Supplier<String> random2 = scope.fork(() -> client.get().uri(Endpoints.LOOKUP.get(Endpoints.RANDOM_TWO)).retrieve().toEntity(String.class).getBody());
            Supplier<String> random3 = scope.fork(() -> client.get().uri(Endpoints.LOOKUP.get(Endpoints.RANDOM_THREE)).retrieve().toEntity(String.class).getBody());
            Supplier<String> random4 = scope.fork(() -> client.get().uri(Endpoints.LOOKUP.get(Endpoints.RANDOM_FOUR)).retrieve().toEntity(String.class).getBody());
            Supplier<String> random5 = scope.fork(() -> client.get().uri(Endpoints.LOOKUP.get(Endpoints.RANDOM_FIVE)).retrieve().toEntity(String.class).getBody());
            Supplier<String> summary = scope.fork(() -> yahoo.get().uri(String.format(Endpoints.LOOKUP.get(Endpoints.GET_SUMMARY), ticker))
                    .header(API_KEY, key)
                    .retrieve().toEntity(String.class).getBody());

            try {
                scope.join();
            } catch (InterruptedException e) {
                throw new VTConsumerThreadException(e);
            }
            scope.throwIfFailed(VTConsumerSubTaskException::new);
            return new SummaryResponse(random1.get(), random2.get(), random3.get(), random4.get(), random5.get(), summary.get());
        }
    }

}
