package edu.yacoubi.softwaretesting.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerRegistrationService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerRegistrationService(CustomerRepository repository) {
        this.customerRepository = repository;
    }

    public void registerNewCustomer(CustomerRegistrationRequest request) {
        String phoneNumber = request.getCustomer().getPhoneNumber();

        Optional<Customer> optionalCustomer = customerRepository
                .selectCustomerByPhoneNumber(phoneNumber);

        if (optionalCustomer.isPresent()) {
            if (optionalCustomer.get().getName().equals(request.getCustomer().getName())) {
                return;
            }
            throw new IllegalStateException(
                    String.format("phone number [%s] is taken", phoneNumber)
            );
        }

        customerRepository.save(request.getCustomer());
    }

    public Iterable<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
