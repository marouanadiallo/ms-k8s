package com.alphamar.customer;

import com.alphamar.clients.fraud.FraudClient;
import com.alphamar.clients.notification.NotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("customers")
public class CustomerController {

    private final CustomerService customerService;
    private final FraudClient fraudClient;
    private final NotificationPublisher notificationPublisher;
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    public CustomerController(CustomerService customerService,
                              FraudClient fraudClient,
                              NotificationPublisher notificationPublisher
                              ) {
        this.customerService = customerService;
        this.fraudClient = fraudClient;
        this.notificationPublisher = notificationPublisher;
    }

    @GetMapping
    public ResponseEntity<String> getCustomer() {
        return ResponseEntity.ok("Hello from Customer Service");
    }

    @PostMapping
    public ResponseEntity<Long> createCustomer(@RequestBody CustomerRequest customer) {

        boolean isFraudster = Boolean.TRUE.equals(fraudClient.isFraudster(customer.email()).getBody());
        if (isFraudster) {
            return ResponseEntity.status(403).build();
        }

        logger.info("Creating customer with email {}", customer.email());
        var id = customerService.save(Customer.of(customer.name(), customer.email()));

        var location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id);

        this.notificationPublisher.publish(
                new NotificationRequest(
                        id,
                        customer.email(),
                        "Welcome to Alphamar, " + customer.name() + "!"
                )
        );
        return ResponseEntity.created(location.toUri()).body(id);
    }

}
