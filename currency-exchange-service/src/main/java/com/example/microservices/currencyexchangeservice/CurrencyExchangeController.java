package com.example.microservices.currencyexchangeservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {

	@Autowired
	private CurrencyExchangeRepository currencyExchangeRepository;

	@Autowired
	private Environment env;

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
		CurrencyExchange currencyExchange = currencyExchangeRepository.findByFromAndTo(from, to);
		if (currencyExchange == null) {
			throw new RuntimeException("unable to find data for" + from + " to " + to);
		}
		String port = env.getProperty("local.server.port");
		currencyExchange.setEnviroment(port);

		return currencyExchange;
	}
}
