package com.quickpick.backend;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuickpickApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		String clientId = dotenv.get("GOOGLE_0AUTH2_CLIENT_ID");
		String clientSecret = dotenv.get("GOOGLE_OAUTH2_CLIENT_SECRET");

		System.out.println("Client Id: " + clientId);
		System.out.println("Client Secret: " + clientSecret);

		SpringApplication.run(QuickpickApplication.class, args);
	}

}
