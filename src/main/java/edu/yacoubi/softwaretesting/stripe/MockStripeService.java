package edu.yacoubi.softwaretesting.stripe;

import edu.yacoubi.softwaretesting.payment.CardPaymentCharge;
import edu.yacoubi.softwaretesting.payment.CardPaymentCharger;
import edu.yacoubi.softwaretesting.payment.Currency;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@ConditionalOnProperty(
        value = "stripe.enabled",
        havingValue = "false"
)
public class MockStripeService implements CardPaymentCharger {

    @Override
    public CardPaymentCharge chargeCard(String cardSource,
                                        BigDecimal amount,
                                        Currency currency,
                                        String description) {
        // happy path simulation
        return new CardPaymentCharge(true);
    }
}
