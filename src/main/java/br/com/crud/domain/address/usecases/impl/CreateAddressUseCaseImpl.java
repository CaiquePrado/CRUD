package br.com.crud.domain.address.usecases.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.crud.domain.address.dtos.CreateAddressDTO;
import br.com.crud.domain.address.dtos.mapper.AddressMapper;
import br.com.crud.domain.address.entity.Address;
import br.com.crud.domain.address.repository.AddressRepository;
import br.com.crud.domain.address.usecases.CreateAddressUseCase;
import br.com.crud.domain.person.entity.Person;
import br.com.crud.domain.person.repository.PersonRepository;
import br.com.crud.infra.exceptions.ResourceNotFoundException;

@Service
public class CreateAddressUseCaseImpl implements CreateAddressUseCase {

  @Autowired
  private AddressRepository addressRepository;

  @Autowired
  private PersonRepository personRepository;

  @Autowired
  private AddressMapper addressMapper;

  @Override
  public Address execute(CreateAddressDTO createAddressDTO, UUID id) {
    Address address = addressMapper.toAddress(createAddressDTO);
    Person person = verifyIfPersonExists(id);
    address.setPerson(person);
    return saveAddress(address);
  }

  private Person verifyIfPersonExists(UUID id){
    return personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id));
  }

  private Address saveAddress(Address address){
    return addressRepository.save(address);
  }
}

