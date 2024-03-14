package br.com.crud.domain.person.dtos.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.crud.domain.address.entity.Address;
import br.com.crud.domain.person.dtos.CreatePersonDTO;
import br.com.crud.domain.person.dtos.UpdatePersonDTO;
import br.com.crud.domain.person.entity.Person;

@Service
public class PersonMapper {

  public Person toPerson(CreatePersonDTO createPersonDTO) {
    return new Person(
      createPersonDTO.getName(),
      createPersonDTO.getBirthDate(),
      createPersonDTO.getCpf(),
      createPersonDTO.getAddresses().stream()
      .map(dto -> new Address(dto.getStreet(), dto.getNumber(), dto.getNeighborhood(), dto.getState(), dto.getZipCode(), null)).collect(Collectors.toList())
    );
  }

  public Person toPerson(UpdatePersonDTO updatePersonDTO) {
    return new Person(
      updatePersonDTO.getName(),
      updatePersonDTO.getBirthDate(),
      updatePersonDTO.getCpf(),
      updatePersonDTO.getAddresses().stream()
      .map(dto -> new Address(dto.getStreet(), dto.getNumber(), dto.getNeighborhood(), dto.getState(), dto.getZipCode(), null)).collect(Collectors.toList())
    );
  }
}
