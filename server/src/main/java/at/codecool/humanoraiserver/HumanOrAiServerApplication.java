package at.codecool.humanoraiserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HumanOrAiServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HumanOrAiServerApplication.class, args);
	}

}
