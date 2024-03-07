package br.com.crud.domain.person.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.crud.domain.person.entity.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person,UUID> {
  
}
