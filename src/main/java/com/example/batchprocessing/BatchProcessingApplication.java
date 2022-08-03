/*package com.example.batchprocessing;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class BatchProcessingApplication {

    public static void main(String[] args) throws Exception {
        List<String> params = new ArrayList<>();
        for(String key : args){
            params.add(key);
        }
        
        params.add("requestDate="+System.currentTimeMillis());
        
        System.exit(SpringApplication.exit(SpringApplication.run(BatchProcessingApplication.class, params.toArray(new String[0]))));
    }
}*/
