package com.kv.carrier.vt.demo.consumer.controller;

import com.kv.carrier.vt.demo.consumer.service.ProducerSvc;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api/v1")
public class Konsument {

    @Autowired
    private ProducerSvc producerSvc;

    @GetMapping("/sleep/{secs}")
    public String demo(@PathVariable("secs") Integer secs) {

        return producerSvc.apply(secs);
    }
}
