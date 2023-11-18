package edu.yacoubi.softwaretesting.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerRegistrationService {

    private final CustomerRepository customerRepository;

    public void registerNewCustomer(CustomerRegistrationRequest request) {
        String phoneNumber = request.getCustomer().getPhoneNumber();

        Optional<Customer> optionalCustomer = customerRepository
                .selectCustomerByPhoneNumber(phoneNumber);

        if (optionalCustomer.isPresent()) {
            if (optionalCustomer.get().getName()
                    .equals(request.getCustomer().getName())) {
                return;
            }
            throw new IllegalStateException(
                    String.format("phone number [%s] is taken", phoneNumber)
            );
        }

        // to ensure that the service have a control to set
        // the customer id
        if(request.getCustomer().getId() == null) {
            request.getCustomer().setId(UUID.randomUUID());
        }

        customerRepository.save(request.getCustomer());
    }

    public Iterable<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
