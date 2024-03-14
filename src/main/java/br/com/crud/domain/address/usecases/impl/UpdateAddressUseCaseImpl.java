package br.com.crud.domain.address.usecases.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.crud.domain.address.entity.Address;
import br.com.crud.domain.address.repository.AddressRepository;
import br.com.crud.domain.address.usecases.UpdateAddressUseCase;
import br.com.crud.infra.exceptions.ResourceNotFoundException;

@Service
public class UpdateAddressUseCaseImpl implements UpdateAddressUseCase {

  @Autowired
  private AddressRepository addressRepository;

  @Override
  public Address execute(Address address, UUID id) {

    Address existingAddress = verifyIfAddressExists(id);

    existingAddress.setStreet(address.getStreet());
    existingAddress.setNumber(address.getNumber());
    existingAddress.setNeighborhood(address.getNeighborhood());
    existingAddress.setState(address.getState());
    existingAddress.setZipCode(address.getZipCode());

    return addressRepository.save(existingAddress);
  }

  private Address verifyIfAddressExists(UUID id) {
    return addressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address not found!"));
  }
}
