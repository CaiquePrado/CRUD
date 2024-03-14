package br.com.crud.domain.person.usecases.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
  public Page<Person> execute(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<Person> people = personRepository.findAll(pageable);
    people.stream().forEach(person -> person.setAddresses(addressRepository.findByPerson(person)));
    return people;
  }
}
