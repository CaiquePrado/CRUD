package br.com.crud.domain.person.usecases;

import br.com.crud.domain.person.dtos.CreatePersonDTO;
import br.com.crud.domain.person.entity.Person;

public interface CreatePersonUseCase {

  Person execute(CreatePersonDTO personDTO);

}
