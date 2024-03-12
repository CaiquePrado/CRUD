package br.com.crud.domain.person.usecases.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.crud.domain.address.repository.AddressRepository;
import br.com.crud.domain.person.entity.Person;
import br.com.crud.domain.person.repository.PersonRepository;
import br.com.crud.domain.person.usecases.ListAllPeopleUseCase;

@Service
public class ListAllPeopleUseCaseImpl implements ListAllPeopleUseCase {

  @Autowired
  private PersonRepository personRepository;

  @Autowired 
  private AddressRepository addressRepository;

  @Override
  public List<Person> execute() {
    List<Person> people = personRepository.findAll();
    people.stream().forEach(person -> person.setAddresses(addressRepository.findByPerson(person)));
    return people;
  }
}
