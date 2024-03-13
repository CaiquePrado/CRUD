package br.com.crud.domain.address.usecases;

import java.util.UUID;

import br.com.crud.domain.address.entity.Address;

public interface CreateAddressUseCase {
  
  Address execute(Address address, UUID personId);

}
