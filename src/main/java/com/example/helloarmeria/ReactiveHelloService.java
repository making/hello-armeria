package com.example.helloarmeria;

import com.example.helloarmeria.rpc.HelloRequest;
import com.example.helloarmeria.rpc.HelloResponse;
import com.example.helloarmeria.rpc.ReactorHelloServiceGrpc.HelloServiceImplBase;
import com.linecorp.armeria.server.ServiceRequestContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import org.springframework.stereotype.Component;

@Component
public class ReactiveHelloService extends HelloServiceImplBase {
	@Override
	public Mono<HelloResponse> sayHello(Mono<HelloRequest> request) {
		return request.map(r -> HelloResponse.newBuilder()
				.setReply("Hello " + r.getGreeting())
				.build())
				.log("sayHello");
	}

	@Override
	public Flux<HelloResponse> lotsOfReplies(Mono<HelloRequest> request) {
		return request.flatMapMany(r -> Flux.range(0, 3)
				.map(n -> HelloResponse.newBuilder()
						.setReply(String.format("[%05d] Hello %s", n, r.getGreeting()))
						.build()))
				.publishOn(Schedulers.fromExecutor(ServiceRequestContext.current().eventLoop()))
				.log("lotsOfReplies");
	}
}
