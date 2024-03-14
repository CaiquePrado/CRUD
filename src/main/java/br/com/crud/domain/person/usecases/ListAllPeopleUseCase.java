package br.com.crud.domain.person.usecases;
import org.springframework.data.domain.Page;

import br.com.crud.domain.person.entity.Person;

public interface ListAllPeopleUseCase {
  
  Page<Person> execute(int page, int size);

}
