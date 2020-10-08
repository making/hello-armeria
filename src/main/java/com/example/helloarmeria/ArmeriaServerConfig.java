package com.example.helloarmeria;

import java.util.List;

import com.linecorp.armeria.common.grpc.GrpcSerializationFormats;
import com.linecorp.armeria.server.grpc.GrpcService;
import com.linecorp.armeria.spring.ArmeriaServerConfigurator;
import io.grpc.BindableService;
import io.grpc.protobuf.services.ProtoReflectionService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ArmeriaServerConfig {
	@Bean
	public ArmeriaServerConfigurator armeriaServerConfigurator(List<BindableService> grpcServices) {
		return serverBuilder -> serverBuilder
				.service(GrpcService.builder()
						.addServices(grpcServices)
						.addService(ProtoReflectionService.newInstance())
						.supportedSerializationFormats(GrpcSerializationFormats.values())
						.enableUnframedRequests(true)
						.build());
	}
}
