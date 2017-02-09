package com.github.dotkebi.job;

import com.github.dotkebi.service.TestService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author by dotkebi@gmail.com on 2017-02-09.
 */
public class TestJob implements Job {

    @Autowired
    private TestService testService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        testService.hello();
    }

}
