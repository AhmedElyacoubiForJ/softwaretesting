package edu.yacoubi.softwaretesting.stripe;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.net.RequestOptions;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
// reason to implement this delegate service
// is we cannot test a static create method or to mock it
// And we will not add other frameworks for these issue
// to prevent adding other dependencies (like PowerMock).
// So StripeApi delegate give the possibility to mock
// our stripe service implementation
public class StripeApi {
    public Charge create(Map<String, Object> params, RequestOptions options)
            throws StripeException {
        return Charge.create(params, options);
    }
}
