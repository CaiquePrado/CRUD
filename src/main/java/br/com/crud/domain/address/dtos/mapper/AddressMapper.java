package br.com.crud.domain.address.dtos.mapper;

import org.springframework.stereotype.Service;

import br.com.crud.domain.address.dtos.CreateAddressDTO;
import br.com.crud.domain.address.dtos.UpdateAddressDTO;
import br.com.crud.domain.address.entity.Address;

@Service
public class AddressMapper {

  public Address toAddress(CreateAddressDTO createAddressDTO) {
    return new Address(
      createAddressDTO.getStreet(),
      createAddressDTO.getNumber(),
      createAddressDTO.getNeighborhood(),
      createAddressDTO.getState(),
      createAddressDTO.getZipCode(), null
    );
  }

  public Address toAddress(UpdateAddressDTO updateAddressDTO) {
    return new Address(
      updateAddressDTO.getStreet(),
      updateAddressDTO.getNumber(),
      updateAddressDTO.getNeighborhood(),
      updateAddressDTO.getState(),
      updateAddressDTO.getZipCode(), null
    );
  }
}

