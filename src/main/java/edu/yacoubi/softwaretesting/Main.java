package edu.yacoubi.softwaretesting;

import lombok.val;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class Main implements CommandLineRunner {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
//		String[] beanDefinitionNames = context.getBeanDefinitionNames();
//
//		Arrays.stream(beanDefinitionNames)
//				.sorted()
//				.forEach(System.out::println);

	}

	@Override
	public void run(String... args) throws Exception {
		//...
	}
}
