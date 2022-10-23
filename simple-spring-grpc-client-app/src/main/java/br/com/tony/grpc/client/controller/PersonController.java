package br.com.tony.grpc.client.controller;

import br.com.tony.grpc.server.*;
import br.com.tony.grpc.client.domain.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
public class PersonController {

    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);
    private final PersonServiceGrpc.PersonServiceBlockingStub personServiceBlockingStub;

    private final Function<PersonResponse, Person> toPerson = personResponse ->
            new Person(personResponse.getId(), personResponse.getName(), personResponse.getEmail());

    public PersonController(PersonServiceGrpc.PersonServiceBlockingStub personServiceBlockingStub) {
        this.personServiceBlockingStub = personServiceBlockingStub;
    }

    @PostMapping
    public ResponseEntity<Person> create(@RequestBody Person person) {
        logger.info("Server Address: :{}", personServiceBlockingStub.getChannel());
        PersonResponse personResponse = personServiceBlockingStub.create(PersonRequest.newBuilder()
                .setName(person.getName())
                .setEmail(person.getEmail())
                .build());

        Person personCreated = toPerson.apply(personResponse);

        return ResponseEntity.status(HttpStatus.CREATED).body(personCreated);
    }

    @GetMapping
    public ResponseEntity<List<Person>> getAll() {
        logger.info("Server Address: :{}", personServiceBlockingStub.getChannel());
        People people = personServiceBlockingStub.getAll(Empty.newBuilder().build());

        List<Person> personList = people.getPeopleList().stream().map(toPerson).toList();

        return ResponseEntity.status(HttpStatus.OK).body(personList);
    }
}
