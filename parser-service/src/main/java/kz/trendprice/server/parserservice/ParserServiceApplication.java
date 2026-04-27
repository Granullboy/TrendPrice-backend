package kz.trendprice.server.parserservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ParserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParserServiceApplication.class, args);
	}

}
