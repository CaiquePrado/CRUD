package br.com.crud.domain.person.usecases;
import java.util.List;

import br.com.crud.domain.person.entity.Person;

public interface ListAllPeopleUseCase {
  
  List<Person> execute();

}
