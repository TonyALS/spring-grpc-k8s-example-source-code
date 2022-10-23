package br.com.tony.grpc.server.controller;

import br.com.tony.grpc.server.*;
import br.com.tony.grpc.server.domain.Person;
import br.com.tony.grpc.server.repository.IPersonRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Function;

@GrpcService
public class PersonController extends PersonServiceGrpc.PersonServiceImplBase {

    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    private final IPersonRepository personRepository;

    private final Function<Person, PersonResponse> toPersonResponse = person -> PersonResponse.newBuilder()
            .setId(person.getId())
            .setName(person.getName())
            .setEmail(person.getEmail())
            .build();

    public PersonController(IPersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void create(PersonRequest request, StreamObserver<PersonResponse> responseObserver) {
        logger.info("GrpcServer - create person request received");
        Person person = new Person(request.getName(), request.getEmail());
        Person personCreated = personRepository.save(person);
        PersonResponse personResponse = toPersonResponse.apply(personCreated);
        logger.info("Person created. Id :{}", personCreated.getId());
        responseObserver.onNext(personResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void getAll(Empty request, StreamObserver<People> responseObserver) {
        List<Person> people = personRepository.findAll();
        logger.info("GrpcServer - get people request received");

        List<PersonResponse> personResponseList = people.stream()
                .map(toPersonResponse)
                .toList();

        responseObserver.onNext(People.newBuilder().addAllPeople(personResponseList).build());
        responseObserver.onCompleted();
    }
}
