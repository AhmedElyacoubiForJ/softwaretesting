package edu.yacoubi.softwaretesting;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import edu.yacoubi.softwaretesting.twilio.TwilioConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class Main implements CommandLineRunner {

	@Autowired
	private TwilioConfiguration twilioConfiguration;

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// A good way to mock out any interaction with Twilio
		// would be to create your own class (MockClient) that extends HttpClient.
		// Then you would create a new TwilioRestClient that uses your MockClient.
		// Now if you use the new TwilioRestClient you can have the MockClient
		// return whatever response that you would like!


		/*Twilio.init(
				twilioConfiguration.getAccountSid(),
				twilioConfiguration.getAuthToken()
		);
		String to = twilioConfiguration.getToPhoneNumber();
		String fromTwilio = twilioConfiguration.getFromTwilioPhoneNumber();
		String sms = "Testing twilio from Spring Boot application";

		PhoneNumber toPhoneNumber = new PhoneNumber(to);
		PhoneNumber fromTwilioPhoneNumber = new PhoneNumber(fromTwilio);

		Message message = Message.creator(
				toPhoneNumber,
				fromTwilioPhoneNumber,
				sms
		).create();*/


		System.out.println("TwilioConfiguration: " + twilioConfiguration);
	}
}
