package br.com.crud.domain.person.usecases.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.crud.domain.person.entity.Person;
import br.com.crud.domain.person.repository.PersonRepository;
import br.com.crud.domain.person.usecases.DeletePersonUseCase;
import br.com.crud.infra.exceptions.ResourceNotFoundException;

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
    return personRepository.findPersonByCpf(cpf).orElseThrow(() -> new ResourceNotFoundException("CPF not found!"));
  }
}
