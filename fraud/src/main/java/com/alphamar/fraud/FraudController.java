package com.alphamar.fraud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("frauds")
public class FraudController {

    private final static Logger logger = LoggerFactory.getLogger(FraudController.class);
    private final FraudulentService fraudulentService;
    private final FraudCheckService fraudCheckService;

    public FraudController(FraudulentService fraudulentService, FraudCheckService fraudCheckService) {
        this.fraudulentService = fraudulentService;
        this.fraudCheckService = fraudCheckService;
    }

    @GetMapping("/check/{id}/customer")
    public ResponseEntity<Boolean> isFraudster(@PathVariable Long id) {
        logger.info("Checking if customer with id {} is a fraudster", id);
        return ResponseEntity.ok(fraudCheckService.isFraudster(id));
    }

    @GetMapping("/check/customer")
    public ResponseEntity<Boolean> isFraudster(@RequestParam("email") String email) {
        logger.info("Checking if customer with email {} is a fraudster", email);
        return ResponseEntity.ok(fraudCheckService.isFraudster(email));
    }

    @PutMapping("/customer")
    public ResponseEntity<Void> fraudCustomer(@RequestBody FraudRequest fraudRequest) {
        logger.info("Defining customer with id {} and email {} as a fraudster", fraudRequest.customerId(), fraudRequest.customerEmail());
        fraudulentService.defineFraudHistory(
                FraudHistory.of(fraudRequest.customerId(), fraudRequest.customerEmail(), true)
        );
        return ResponseEntity.ok().build();
    }
}
