package com.example.microservices.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class CircuitBreakerController {
	private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

	@GetMapping("/sample-api")
//	@Retry(name = "sample-api", fallbackMethod = "hardcodedResponse")
//	@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
	@RateLimiter(name = "default") // 10s=> 10000 calls to the sample api
	@Bulkhead(name = "sample-api")
	public String sampleApi() {
		logger.info("Sample api call");
//		ResponseEntity<String> response = new RestTemplate().getForEntity("http://192.168.1.8:8080/dummy-uri",
//				String.class);
//		return response.getBody();
		return "sample-api";
	}

	public String hardcodedResponse(Exception ex) {
		return "fallback response";
	}
}