package com.alphamar.eurekaserver.fraud;

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

    @GetMapping("/check/customer")
    public ResponseEntity<Boolean> isFraudster(@RequestParam("id") Long customerId) {
        logger.info("Checking if customer with id {} is a fraudster", customerId);
        return ResponseEntity.ok(fraudCheckService.isFraudster(customerId));
    }

    @PutMapping("/{id}/customer")
    public ResponseEntity<Void> fraudCustomer(@PathVariable Long id) {
        fraudulentService.defineFraudHistory(FraudHistory.of(id, true));
        return ResponseEntity.ok().build();
    }
}
