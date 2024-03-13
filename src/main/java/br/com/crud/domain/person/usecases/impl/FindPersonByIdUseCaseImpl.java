package br.com.crud.domain.person.usecases.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.crud.domain._exceptions.IdNotFoundException;
import br.com.crud.domain.address.repository.AddressRepository;
import br.com.crud.domain.person.entity.Person;
import br.com.crud.domain.person.repository.PersonRepository;
import br.com.crud.domain.person.usecases.FindPersonByIdUseCase;

@Service
public class FindPersonByIdUseCaseImpl implements FindPersonByIdUseCase {

  @Autowired
  private PersonRepository personRepository;

  @Autowired 
  private AddressRepository addressRepository;

  @Override 
  public Optional<Person> execute(UUID id) {
    verifyIfIdExists(id);
    return personRepository.findById(id).map(person -> {
      person.setAddresses(addressRepository.findByPerson(person));
      return person;
    });
  }

  private void verifyIfIdExists(UUID id) {
    if (!personRepository.existsById(id)) {
      throw new IdNotFoundException("No person found with this ID");
    }
  }
}
