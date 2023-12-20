package com.kv.carrier.vt.demo.consumer.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Log4j2
@Service
public class ProducerSvc {

    @Value("${consumer.hostName}")
    private String hostName;

    @Value("${consumer.contextPath}")
    private String contextPath;

    @Value("${consumer.path}")
    private String path;

    @Autowired
    private RestClient.Builder builder;

    public String apply(Integer time){
        String uri = String.format("%s%s%s", contextPath, path, time);
        //log.info("Path: {}",uri);
        ResponseEntity<String> entity = builder.baseUrl(hostName).build().get().uri(uri).retrieve().toEntity(String.class);

        String resp = String.format("Response: %s - Thread: %s - Http Code: %s", entity.getBody(), Thread.currentThread(), entity.getStatusCode());
        log.info("Thread Name: - Status Code: {} ", resp);
        return resp;
    }

}
