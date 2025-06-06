package org.abigotado.otp_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OtpServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OtpServiceApplication.class, args);
	}

}
