package br.com.tony.grpc.client.config;


import br.com.tony.grpc.server.PersonServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.client.inject.GrpcClientBean;
import org.springframework.context.annotation.Configuration;

@Configuration
@GrpcClientBean(
        clazz = PersonServiceGrpc.PersonServiceBlockingStub.class,
        beanName = "blockingStub",
        client = @GrpcClient("simple-spring-grpc-server-app")
)
public class GrpcServerService {}
