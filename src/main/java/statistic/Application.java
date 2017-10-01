package statistic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import statistic.model.DataQueue;

@EnableScheduling
@SpringBootApplication
public class Application {

	@Bean
	public DataQueue DataQueue(){
		return new DataQueue();
	}
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}