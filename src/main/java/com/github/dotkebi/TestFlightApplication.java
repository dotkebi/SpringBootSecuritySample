package com.github.dotkebi;

import com.github.dotkebi.config.SchedulerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({SchedulerConfig.class})
public class TestFlightApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestFlightApplication.class, args);
    }
}
