package com.example.batchprocessing.config.tasklet.MethodInvokingTaskLetJob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MethodInvokingTaskLetBatchConfiguration {
    private static final Logger log = LoggerFactory.getLogger(MethodInvokingTaskLetBatchConfiguration.class);
    
    @Autowired   
    public JobBuilderFactory jobBuilderFactory;
    @Autowired    
    public StepBuilderFactory stepBuilderFactory;
    
    
    @Bean
    public Job methodInvokingTaskLetJob(Step methodInvokingTaskLetStep){

        Job customJob = jobBuilderFactory.get("methodInvokingTaskLetJob")
        		.incrementer(new RunIdIncrementer())
                .flow(methodInvokingTaskLetStep)
                .end()
                .build();

        return customJob;
    }

    @Bean
    public Step methodInvokingTaskLetStep(){
        return stepBuilderFactory.get("methodInvokingTaskLetStep")
                .tasklet(myTasklet()).build();
    }
    

    @Bean
    public CustomService service() {
        return new CustomService ();
    }

    @Bean
    public MethodInvokingTaskletAdapter myTasklet() {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();

        adapter.setTargetObject(service());
        adapter.setTargetMethod("businessLogic");

        return adapter;
    }
    
}
