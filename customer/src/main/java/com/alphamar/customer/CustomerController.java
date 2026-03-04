package com.alphamar.eurekaserver.customer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("customers")
public class CustomerController {
    private final CustomerService customerService;
    private final RestClient.Builder restClientBuilder;

    public CustomerController(CustomerService customerService, RestClient.Builder restClientBuilder) {
        this.customerService = customerService;
        this.restClientBuilder = restClientBuilder;
    }

    @GetMapping
    public ResponseEntity<String> getCustomer() {
        return ResponseEntity.ok("Hello from Customer Service");
    }

    @PostMapping
    public ResponseEntity<Long> createCustomer(@RequestBody CustomerRequest customer) {
        //TODO: check if email is not fraudulent by calling fraud service
        var id = customerService.save(Customer.of(customer.name(), customer.email()));
        var restClient = this.restClientBuilder.baseUrl("http://fraud-service").build();

        boolean isFraudster = Boolean.TRUE.equals(restClient.get()
                .uri("/frauds/check/customer?id={id}", id)
                .retrieve()
                .body(Boolean.class));

        if (isFraudster) {
            return ResponseEntity.status(403).build();
        }

        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id);
        return ResponseEntity.created(location.toUri()).build();
    }

}
