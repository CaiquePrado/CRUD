package br.com.crud.domain.person.services.usecases.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.crud.domain._exceptions.CpfNotFoundException;
import br.com.crud.domain.person.entity.Person;
import br.com.crud.domain.person.repository.PersonRepository;
import br.com.crud.domain.person.services.usecases.DeletePersonUseCase;

@Service
public class DeletePersonUseCaseImpl implements DeletePersonUseCase {

  @Autowired
  private PersonRepository personRepository;

  @Override
  public void execute(String cpf) {

    Person person = verifyIfCpfExists(cpf);

    personRepository.delete(person);
  }

  private Person verifyIfCpfExists(String cpf) {
    return personRepository.findPersonByCpf(cpf).orElseThrow(() -> new CpfNotFoundException("CPF not found!"));
  }
}
