package br.com.crud.domain.person.usecases.impl;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.crud.domain.address.repository.AddressRepository;
import br.com.crud.domain.person.entity.Person;
import br.com.crud.domain.person.repository.PersonRepository;
import br.com.crud.domain.person.usecases.UpdatePersonUseCase;
import br.com.crud.infra.exceptions.InvalidRequestException;
import br.com.crud.infra.exceptions.ResourceNotFoundException;

@Service
public class UpdatePersonUseCaseImpl implements UpdatePersonUseCase {

  @Autowired
  private PersonRepository personRepository;

  @Autowired 
  private AddressRepository addressRepository;

  @Override
  public Person execute(String cpf,Person person) {

  if (!person.getCpf().equals(cpf)) {
      throw new  InvalidRequestException("The CPF in the URL does not match the CPF of the person to be updated.");
  }

  Person existingPerson = verifyIfCpfExists(person.getCpf());

  existingPerson.setName(person.getName());
  existingPerson.setBirthDate(person.getBirthDate());
  existingPerson.setCpf(person.getCpf());

  existingPerson.setAddresses(person.getAddresses().stream().map(address -> {
    address.setPerson(existingPerson);
    return addressRepository.save(address);
  }).collect(Collectors.toList()));

    return personRepository.save(existingPerson);
  }

  private Person verifyIfCpfExists(String cpf) {
    return personRepository.findPersonByCpf(cpf).orElseThrow(() -> new ResourceNotFoundException("CPF not found!"));
  }
}
