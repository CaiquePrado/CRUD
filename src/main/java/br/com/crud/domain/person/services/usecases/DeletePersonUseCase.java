package br.com.crud.domain.person.services.usecases;

import br.com.crud.domain.person.entity.Person;

public interface DeletePersonUseCase {
  
  Person execute(String cpf);

}
