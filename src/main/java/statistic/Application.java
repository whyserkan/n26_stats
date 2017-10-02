package statistic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import statistic.model.TimeArray;

@SpringBootApplication
public class Application {

	@Bean
	public TimeArray TimeArray(){
		return new TimeArray();
	}
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}