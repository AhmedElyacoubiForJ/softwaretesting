package edu.yacoubi.softwaretesting.stripe;

import edu.yacoubi.softwaretesting.payment.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class StripeServiceTest {

    private StripeService underTest;

    @Mock
    private StripeApi stripeApi;

    @BeforeEach
    void setUp() {
        underTest = new StripeService(stripeApi);
    }

    @Test
    void itShouldChargeCard() {
        // Given
        String cardSource = "0x0x0x";
        BigDecimal amount = new BigDecimal("10.00");
        Currency currency = Currency.USD;
        String description = "Zakat";

        // When
        underTest.chargeCard(
                cardSource,
                amount,
                currency,
                description
        );
        // Then
    }
}