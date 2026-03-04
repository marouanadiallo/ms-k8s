package com.alphamar.clients.fraud;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "fraud-service",
        url = "${clients.fraud.url}"
)
public interface FraudClient {

    @GetMapping("frauds/check/{id}/customer")
    ResponseEntity<Boolean> isFraudster(@PathVariable("id") Long id);

    @GetMapping("frauds/check/customer")
    ResponseEntity<Boolean> isFraudster(@RequestParam("email") String email);
}
