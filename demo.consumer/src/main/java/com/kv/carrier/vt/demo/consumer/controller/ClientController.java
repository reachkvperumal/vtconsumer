package com.kv.carrier.vt.demo.consumer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kv.carrier.vt.demo.consumer.dto.ClientAccount;


import com.kv.carrier.vt.demo.consumer.service.BufferSvc;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Log4j2
public class ClientController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BufferSvc bufferSvc;

    @PostMapping("/readJson")
    public List<ClientAccount[]> readJson(@RequestBody ClientAccount[][] data) throws JsonProcessingException {
        log.info("Payload: {}", data);
        return Arrays.stream(data).collect(Collectors.toList());
    }

    @GetMapping("/getJson")
    public String getJson(){
        return bufferSvc.apply();
    }
}
