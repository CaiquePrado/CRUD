package br.com.crud.domain.person.usecases;

import java.util.Optional;
import java.util.UUID;

import br.com.crud.domain.person.entity.Person;

public interface FindPersonByIdUseCase {
  Optional<Person> execute(UUID id);
}
