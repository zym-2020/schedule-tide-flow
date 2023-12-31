package nnu.edu.schedule;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan("nnu.edu.schedule.dao.*")
@EnableScheduling
public class ScheduleTideFlowApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleTideFlowApplication.class, args);
    }

}
