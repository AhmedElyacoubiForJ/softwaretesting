package edu.yacoubi.softwaretesting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@SpringBootApplication
public class Main implements CommandLineRunner {

	public static final String ACCOUNT_SID =
			System.getenv("TWILIO_ACCOUNT_SID");
	public static final String AUTH_TOKEN =
			System.getenv("TWILIO_AUTH_TOKEN");

	public static final String FROM_TWILIO_PHONE_NUMBER =
			System.getenv("TWILIO_PHONE_NUMBER");
	public static final String TO_PHONE_NUMBER =
			System.getenv("MY_PHONE_NUMBER");

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		Message message = Message.creator(
						new PhoneNumber(TO_PHONE_NUMBER),
						new PhoneNumber(FROM_TWILIO_PHONE_NUMBER),
						"Testing twilio from Spring Boot application"
		).create();

		System.out.println("ACCOUNT_SID : " + ACCOUNT_SID);
		System.out.println("AUTH_TOKEN : " + AUTH_TOKEN);

		System.out.println("TWILIO_PHONE_NUMBER : " + FROM_TWILIO_PHONE_NUMBER);
		System.out.println("MY_PHONE_NUMBER : " + TO_PHONE_NUMBER);
	}
}
