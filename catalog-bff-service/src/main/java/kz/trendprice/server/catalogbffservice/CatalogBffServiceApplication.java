package kz.trendprice.server.catalogbffservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
		"kz.trendprice.server.catalogbffservice",
		"kz.trendprice.securitystarter"
})
public class CatalogBffServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogBffServiceApplication.class, args);
	}

}
