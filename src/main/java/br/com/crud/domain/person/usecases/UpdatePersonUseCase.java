package br.com.crud.domain.person.usecases;

import br.com.crud.domain.person.entity.Person;

public interface UpdatePersonUseCase {
  
  Person execute( String cpf,Person person);

}
