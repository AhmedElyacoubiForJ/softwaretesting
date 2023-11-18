package edu.yacoubi.softwaretesting.stripe;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.net.RequestOptions;
import edu.yacoubi.softwaretesting.payment.CardPaymentCharge;
import edu.yacoubi.softwaretesting.payment.CardPaymentCharger;
import edu.yacoubi.softwaretesting.payment.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

// implementation according to stripe api reference with some adjustment
// So currently we've implemented the payment unit.
// Right here, we fully tested. and we know our business logic works in isolation
// from the StripeService implementation.
// Let's go ahead and create a service that will interact with Stripe
// we can write a backend system that will use stripe to take payments.
// we have to define the actual api key And then we create a charge And
// then we pass some options
@Service
@ConditionalOnProperty(
        value = "stripe.enabled",
        havingValue = "true"
)
@RequiredArgsConstructor
public class StripeService implements CardPaymentCharger {

    public final StripeApi stripeApi;

    private final static RequestOptions requestOptions = RequestOptions
            .builder()
            .setApiKey("sk_test_4eC39HqLyjWDarjtT1zdp7dc")
            .build();

    @Override
    public CardPaymentCharge chargeCard(
            String cardSource,
            BigDecimal amount,
            Currency currency,
            String description) {

        Map<String, Object> params = new HashMap<>();
        params.put("amount", amount);
        params.put("currency", currency);
        params.put("source", cardSource);
        params.put("description", description);

        try {
            // we are actually trying to connect to the real Stripe API
            // So we try to make network call. We should never do this in unit.
            // Testing Unit testing, we shouldn't really be calling out to the real Services.
            // Because the point of unit testing is that things should be as fast as possible.
            // Unit testing must be self-isolated.
            // We know that Charge.create work, so we don't have to test it
            // because we are just using their API.
            // Only when we deploy to an environment And then we can test it with test cards or real cards,
            // but not for unit testing.

            // Another problem that we have is that this is a static method.
            // There is no way for us to mock this static method.
            // so we two options one, we could use an external framework
            // that allows to mock static methods, or we can solve the problem s. StripeApi
            // removed this call Charge.create(params, options);
            // And replaced with a delegate StripeApi class,
            // problem solved and so, we can test our service, and we can mock the StripeApi class.
            Charge charge = stripeApi.create(params, requestOptions);
            return new CardPaymentCharge(charge.getPaid());
        } catch (StripeException e) {
            throw new IllegalStateException("cannot make stripe charge", e);
        }
    }
}
