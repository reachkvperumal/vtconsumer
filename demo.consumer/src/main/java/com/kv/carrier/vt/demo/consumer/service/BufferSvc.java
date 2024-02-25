package com.kv.carrier.vt.demo.consumer.service;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@Log4j2
@Service
public class BufferSvc implements InitializingBean {

    private ByteBuffer byteBuffer;

    @Override
    public void afterPropertiesSet() throws Exception {
        File file = ResourceUtils.getFile("classpath:DFS.json");
        log.info("File Loaded :: {}", file.getName());
        byte[] byteArray = IOUtils.toByteArray(new FileInputStream(file));
        byteBuffer = ByteBuffer.wrap(byteArray);
    }

    public String apply(){
        return new String(byteBuffer.array(), StandardCharsets.UTF_8);
    }
}
