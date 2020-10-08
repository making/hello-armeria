package com.example.helloarmeria;

import com.example.helloarmeria.rpc.HelloRequest;
import com.example.helloarmeria.rpc.HelloResponse;
import com.example.helloarmeria.rpc.ReactorHelloServiceGrpc.ReactorHelloServiceStub;
import com.linecorp.armeria.client.Clients;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class HelloServiceTest {
	@LocalServerPort
	int port;

	@Test
	void sayHello() {
		final ReactorHelloServiceStub helloService = Clients.newClient("gproto+http://localhost:" + port, ReactorHelloServiceStub.class);
		final Mono<HelloResponse> response = helloService.sayHello(HelloRequest.newBuilder().setGreeting("World").build());
		StepVerifier.create(response)
				.expectNext(HelloResponse.newBuilder().setReply("Hello World").build())
				.verifyComplete();
	}

	@Test
	void lotsOfReplies() {
		final ReactorHelloServiceStub helloService = Clients.newClient("gproto+http://localhost:" + port, ReactorHelloServiceStub.class);
		final Flux<HelloResponse> response = helloService.lotsOfReplies(HelloRequest.newBuilder().setGreeting("World").build());
		StepVerifier.create(response)
				.expectNext(HelloResponse.newBuilder().setReply("[00000] Hello World").build())
				.expectNext(HelloResponse.newBuilder().setReply("[00001] Hello World").build())
				.expectNext(HelloResponse.newBuilder().setReply("[00002] Hello World").build())
				.verifyComplete();
	}
}