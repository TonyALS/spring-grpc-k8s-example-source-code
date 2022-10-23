package br.com.tony.grpc.client.repository;

import br.com.tony.grpc.client.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersonRepository extends JpaRepository<Person, Long> {}
