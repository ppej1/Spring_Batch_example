package com.example.batchprocessing.taskletJob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.batchprocessing.JobCompletionNotificationListener;

@Configuration
public class SampleTasklet {
    private static final Logger log = LoggerFactory.getLogger(SampleTasklet.class);
    
    @Autowired   
    public JobBuilderFactory jobBuilderFactory;
    @Autowired    
    public StepBuilderFactory stepBuilderFactory;
    
    @Bean
    public Job sampleTaskletJob(JobCompletionNotificationListener jobListener, Step sampleTaskletStep) {
    	return jobBuilderFactory.get("sampleTaskletJob")
    			.incrementer(new RunIdIncrementer())
    			.flow(sampleTaskletStep)
    			.end()
    			.build();
    }
    
    @Bean
    public Step sampleTaskletStep() {
    	return stepBuilderFactory.get("sampleTaskletStep")
    			.tasklet(new OuterTasklet())
    			.build();
    }
}
