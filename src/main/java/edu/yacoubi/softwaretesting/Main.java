package edu.yacoubi.softwaretesting;

import edu.yacoubi.softwaretesting.twilio.SmsRequest;
import edu.yacoubi.softwaretesting.twilio.SmsSender;
import edu.yacoubi.softwaretesting.twilio.SmsSenderDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class Main implements CommandLineRunner {

	@Autowired
	private SmsSenderDelegate smsSenderDelegate;

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		smsSenderDelegate.sendSms(
				new SmsRequest("+441234567890", "message")
		);
	}
}
