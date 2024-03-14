package br.com.crud.domain.address.usecases;

import java.util.UUID;

import br.com.crud.domain.address.dtos.UpdateAddressDTO;
import br.com.crud.domain.address.entity.Address;

public interface UpdateAddressUseCase {

  Address execute(UpdateAddressDTO updateAddressDTO, UUID id);
  
}
