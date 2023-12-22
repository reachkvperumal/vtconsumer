package com.kv.carrier.vt.demo.consumer.service;

import com.kv.carrier.vt.demo.consumer.config.Endpoints;
import com.kv.carrier.vt.demo.consumer.dto.MockDataResponse;
import com.kv.carrier.vt.demo.consumer.exception.VTConsumerSubTaskException;
import com.kv.carrier.vt.demo.consumer.exception.VTConsumerThreadException;
import io.micrometer.core.annotation.Timed;
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
import java.util.concurrent.StructuredTaskScope.Subtask;

@Log4j2
@Service
public class PoolRandomDataSvc implements InitializingBean {

    private final static String API_KEY = "X-RapidAPI-Key";
    private final RestClient client;

    private final RestClient yahoo;

    private String key;

    private final Resource resource;

    private static final ScopedValue<String> HEADER_KEY = ScopedValue.newInstance();

    public PoolRandomDataSvc(@Value("${demo.url}") String url,
                             @Autowired RestClient.Builder builder,
                             @Value("${demo.summary}")
                             String ticker, @Autowired RestClient.Builder yahoo,
                             @Value("${demo.keyFileName}") Resource resource) {
        this.client = builder.baseUrl(url).build();
        this.yahoo = yahoo.baseUrl(ticker).build();
        this.resource = resource;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.key = StreamUtils.copyToString(resource.getInputStream(), Charset.defaultCharset());
        log.info("KEY: {}", this.key);

    }

    @Timed(value = "ticker.timer.svc", description = "Time calculated for execution of service call alone.")
    public MockDataResponse apply(String ticker) {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            Subtask<String> random1 = scope.fork(() -> getMockData(Endpoints.RANDOM_ONE));
            Subtask<String> random2 = scope.fork(() -> getMockData(Endpoints.RANDOM_TWO));
            Subtask<String> random3 = scope.fork(() -> getMockData(Endpoints.RANDOM_THREE));
            Subtask<String> random4 = scope.fork(() -> getMockData(Endpoints.RANDOM_FOUR));
            Subtask<String> random5 = scope.fork(() -> getMockData(Endpoints.RANDOM_FIVE));
            Subtask<String> random6 = scope.fork(
                    () -> ScopedValue.callWhere(HEADER_KEY, key, () -> getTicker(ticker)));
            try {
                scope.join();
            } catch (InterruptedException e) {
                throw new VTConsumerThreadException(e);
            }
            scope.throwIfFailed(VTConsumerSubTaskException::new);
            return new MockDataResponse(random1.get(), random2.get(), random3.get(), random4.get(), random5.get(), random6.get());
        }
    }

    private String getMockData(Endpoints task) {
        return client.get().uri(Endpoints.LOOKUP.get(task)).retrieve().toEntity(String.class).getBody();
    }

    private String getTicker(String ticker) {
        log.info("HEADER KEY: {}", HEADER_KEY.get());
        return yahoo.get().uri(String.format(Endpoints.LOOKUP.get(Endpoints.GET_SUMMARY), ticker)).header(API_KEY, HEADER_KEY.get()).retrieve().toEntity(String.class).getBody();
    }
}
