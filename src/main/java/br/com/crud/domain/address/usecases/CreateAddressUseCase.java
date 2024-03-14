package br.com.crud.domain.address.usecases;

import java.util.UUID;

import br.com.crud.domain.address.dtos.CreateAddressDTO;
import br.com.crud.domain.address.entity.Address;

public interface CreateAddressUseCase {

  Address execute(CreateAddressDTO createAddressDTO, UUID id);
  
}

