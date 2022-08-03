package com.example.batchprocessing.taskletJob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class OuterTasklet implements Tasklet{
    private static final Logger log = LoggerFactory.getLogger(OuterTasklet.class);
    
    @Override    
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
    	//business 업무 시작        
    	for(int inx = 0 ; inx < 20 ; inx++) {
    		log.info("[step1] : " + inx);
    		}         
    	//business 업무 끝        
    	return RepeatStatus.FINISHED;    
    }
}
