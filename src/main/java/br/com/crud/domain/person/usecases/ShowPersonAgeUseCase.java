package br.com.crud.domain.person.usecases;

import br.com.crud.domain.person.entity.Person;

public interface ShowPersonAgeUseCase {
  
  Integer execute(Person person);

}
