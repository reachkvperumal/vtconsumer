package com.kv.carrier.vt.demo.consumer.controller;

import com.kv.carrier.vt.demo.consumer.dto.RespDataDO;
import com.kv.carrier.vt.demo.consumer.exception.VTConsumerException;
import com.kv.carrier.vt.demo.consumer.service.PollDataSvc;
import com.kv.carrier.vt.demo.consumer.service.ProducerSvc;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private PollDataSvc randomDataSvc;

    @GetMapping("/sleep/{secs}")
    public String demo(@PathVariable("secs") Integer secs) {
        return producerSvc.apply(secs);
    }

    @GetMapping("/exp")
    public String exception(){
        throw new VTConsumerException("Exception Demo...");
    }

    @GetMapping("/random/{ticker}")
    @Observed(name="svc.calls", contextualName = "main controller method", lowCardinalityKeyValues = {"ticker","string"})
    public ResponseEntity<RespDataDO> random(@PathVariable("ticker") String ticker){
        return new ResponseEntity<>(randomDataSvc.apply(ticker), HttpStatus.OK);
    }
}
