package com.example.batchprocessing.config.tasklet.MethodInvokingTaskLetJob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CustomService {
    private static final Logger log = LoggerFactory.getLogger(MethodInvokingTaskLetBatchConfiguration.class);

	    public void businessLogic() {
	        //비즈니스 로직
	        for(int idx = 0; idx < 10; idx ++){
	            log.info("[idx] = " + idx);
	        }
	    }
}
