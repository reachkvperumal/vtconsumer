package com.kv.carrier.vt.demo.consumer.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Log4j2
@Service
public class ProducerSvc implements InitializingBean {

    @Value("${consumer.hostName}")
    private String hostName;

    @Value("${consumer.contextPath}")
    private String contextPath;

    @Value("${consumer.path}")
    private String path;

    @Autowired
    private RestClient.Builder builder;

    private RestClient consumer;

    @Override
    public void afterPropertiesSet() throws Exception {
        String uri = String.format("%s%s%s",hostName,contextPath,path);
        log.info("Producer: {}",uri);
        this.consumer = builder.baseUrl(uri).build();
    }

    public String apply(Integer time){
        ResponseEntity<String> entity = consumer.get().uri(String.valueOf(time)).retrieve().toEntity(String.class);
        String resp = String.format("Response: %s - Thread: %s - Http Code: %s", entity.getBody(), Thread.currentThread(), entity.getStatusCode());
        log.info("{} ", resp);
        return resp;
    }

}
