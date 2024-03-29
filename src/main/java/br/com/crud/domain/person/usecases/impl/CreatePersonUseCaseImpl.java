package br.com.crud.domain.person.usecases.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.crud.domain.address.entity.Address;
import br.com.crud.domain.address.repository.AddressRepository;
import br.com.crud.domain.person.dtos.CreatePersonDTO;
import br.com.crud.domain.person.dtos.mapper.PersonMapper;
import br.com.crud.domain.person.entity.Person;
import br.com.crud.domain.person.repository.PersonRepository;
import br.com.crud.domain.person.usecases.CreatePersonUseCase;
import br.com.crud.infra.exceptions.InvalidRequestException;

@Service
public class CreatePersonUseCaseImpl implements CreatePersonUseCase {

  @Autowired
  private PersonRepository personRepository;

  @Autowired 
  private AddressRepository addressRepository;

  @Autowired
  private PersonMapper personMapper;

  @Override
  public Person execute(CreatePersonDTO createPersonDTO) {

    Person person = personMapper.toPerson(createPersonDTO);

    verifyIfCpfExists(person.getCpf());

    Person savedPerson = savePerson(person);

    saveAddresses(person.getAddresses(), savedPerson);

    return savedPerson;
  }

  private void verifyIfCpfExists(String cpf) {
    personRepository.findPersonByCpf(cpf).ifPresent((person)-> {
      throw new InvalidRequestException("Already exists a person registered with this CPF");
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
