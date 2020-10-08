package com.example.helloarmeria;

import com.fasterxml.jackson.databind.JsonNode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class HelloController {
	private final WebClient webClient;

	public HelloController(WebClient.Builder builder) {
		this.webClient = builder
				.build();
	}

	@GetMapping(path = "/")
	public Flux<String> hello() {
		return Flux.range(0, 10)
				.map(n -> String.format("[%05d] Hello!", n))
				.take(3)
				.log("hello");
	}

	@GetMapping(path = "get")
	public Mono<JsonNode> get() {
		return this.webClient.get()
				.uri("https://httpbin.org/get")
				.retrieve()
				.bodyToMono(JsonNode.class);
	}
}
