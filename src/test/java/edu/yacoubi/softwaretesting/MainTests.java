package edu.yacoubi.softwaretesting;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class MainTests {

	private Calculator underTest = new Calculator();

	@Test
	void contextLoads() {
	}

	@Test
	void itShouldAddTwoNumbers() {
		// Given
		int a = 20;
		int b = 30;

		// When
		int result = underTest.add(a, b);

		// Then
		int expected = 50;
		assertThat(result).isEqualTo(expected);
	}

	class Calculator {
		int add(int a, int b) {
			return a + b;
		}
	}
}
