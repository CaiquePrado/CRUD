package br.com.crud.domain.address.usecases.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.crud.domain.address.entity.Address;
import br.com.crud.domain.address.repository.AddressRepository;
import br.com.crud.domain.address.usecases.DeleteAddressUseCase;
import br.com.crud.infra.exceptions.ResourceNotFoundException;

@Service
public class DeleteAddressUseCaseImpl implements DeleteAddressUseCase {

  @Autowired
  private AddressRepository addressRepository;

  @Override
  public void execute(UUID id) {
    Address address = verifyIfIdExists(id);
    addressRepository.delete(address);
  }

  private Address verifyIfIdExists(UUID id) {
    return addressRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + id));
  }
}
