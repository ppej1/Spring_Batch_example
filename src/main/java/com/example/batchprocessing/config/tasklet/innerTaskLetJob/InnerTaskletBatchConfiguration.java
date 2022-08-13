package com.example.batchprocessing.config.tasklet.innerTaskLetJob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.batchprocessing.JobCompletionNotificationListener;

@Configuration
public class InnerTaskletBatchConfiguration {
    private static final Logger log = LoggerFactory.getLogger(InnerTaskletBatchConfiguration.class);
    
    @Autowired   
    public JobBuilderFactory jobBuilderFactory;
    @Autowired    
    public StepBuilderFactory stepBuilderFactory;
    
    @Bean
    public Job innerTaskletJob(JobCompletionNotificationListener jobListener, Step innerTaskletStep) {
    	return jobBuilderFactory.get("innerTaskletJob")
    			.incrementer(new RunIdIncrementer())
    			.flow(innerTaskletStep)
    			.end()
    			.build();
    }
    
    @Bean
    public Step innerTaskletStep() {
    	return stepBuilderFactory.get("innerTaskletStep")
    			.tasklet((contribution, chunkContext) ->{

                    //비즈니스 로직
                    for(int idx = 0; idx < 10; idx ++){
                        log.info("[idx] = " + idx);
                    }

                   return RepeatStatus.FINISHED;
                }).build();
    }
}
