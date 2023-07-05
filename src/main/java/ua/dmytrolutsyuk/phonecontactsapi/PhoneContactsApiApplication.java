package ua.dmytrolutsyuk.phonecontactsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:.env")
public class PhoneContactsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhoneContactsApiApplication.class, args);
	}

}
