package br.com.crud.domain.person.services.usecases.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.crud.domain._exceptions.CpfAlreadyExistsException;
import br.com.crud.domain.address.entity.Address;
import br.com.crud.domain.address.repository.AddressRepository;
import br.com.crud.domain.person.entity.Person;
import br.com.crud.domain.person.repository.PersonRepository;
import br.com.crud.domain.person.services.usecases.CreatePersonUseCase;

@Service
public class CreatePersonUseCaseImpl implements CreatePersonUseCase {

  @Autowired
  private PersonRepository personRepository;

  @Autowired 
  AddressRepository addressRepository;

  @Override
  public Person execute(Person person) {

    verifyIfCpfExists(person.getCpf());

    Person savedPerson = savePerson(person);

    saveAddresses(person.getAddresses(), savedPerson);

    return savedPerson;
  }

  private void verifyIfCpfExists(String cpf) {
    personRepository.findPersonByCpf(cpf).ifPresent((person)->{
      throw new CpfAlreadyExistsException("Already exists a person registered with this CPF");
    });
  }

  private Person savePerson(Person person) {
    return personRepository.save(person);
  }

  private void saveAddresses(List<Address> addresses, Person savedPerson) {
    if (addresses != null) {
      addresses = addresses.stream().map(address ->{
        address.setPerson(savedPerson);
        return addressRepository.save(address);
      }).collect(Collectors.toList());
    }
  }
}

