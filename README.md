# spring-grpc-k8s-example-source-code

Esse repositório contém o código fonte da aplicação que está no container do repositório [Kubernetes Spring gRPC](https://github.com/TonyALS/k8s-simple-spring-grpc-app)

No [application.properties](simple-spring-grpc-client-app/src/main/resources/application.properties) do simple-spring-grpc-client-app a linha
`
grpc.client.simple-spring-grpc-server-app.address=dns:///spring-grpc-server-lb:80
`
indica o dns do service load balancer com as aplicações grpc-server.

[Veja mais na documentação oficial do Spring gRPC](https://yidongnan.github.io/grpc-spring-boot-starter/en/kubernetes.html)
