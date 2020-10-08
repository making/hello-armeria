package com.example.helloarmeria;

import com.example.helloarmeria.rpc.HelloRequest;
import com.example.helloarmeria.rpc.HelloResponse;
import com.example.helloarmeria.rpc.HelloServiceGrpc.HelloServiceImplBase;
import io.grpc.stub.StreamObserver;

import org.springframework.stereotype.Component;

//@Component
public class VanillaHelloService extends HelloServiceImplBase {
	@Override
	public void sayHello(HelloRequest r, StreamObserver<HelloResponse> responseObserver) {
		responseObserver.onNext(HelloResponse.newBuilder()
				.setReply("Hello " + r.getGreeting())
				.build());
		responseObserver.onCompleted();
	}

	@Override
	public void lotsOfReplies(HelloRequest r, StreamObserver<HelloResponse> responseObserver) {
		for (int i = 0; i < 3; i++) {
			responseObserver.onNext(HelloResponse.newBuilder()
					.setReply(String.format("[%05d] Hello %s", i, r.getGreeting()))
					.build());
		}
		responseObserver.onCompleted();
	}
}
