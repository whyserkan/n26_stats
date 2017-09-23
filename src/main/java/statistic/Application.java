package statistic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import statistic.model.DataQueue;

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