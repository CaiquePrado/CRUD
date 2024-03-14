package br.com.crud.domain.person.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.crud.domain.address.dtos.CreateAddressDTO;
import br.com.crud.domain.address.entity.Address;
import br.com.crud.domain.address.enums.State;
import br.com.crud.domain.address.repository.AddressRepository;
import br.com.crud.domain.person.dtos.UpdatePersonDTO;
import br.com.crud.domain.person.dtos.mapper.PersonMapper;
import br.com.crud.domain.person.entity.Person;
import br.com.crud.domain.person.repository.PersonRepository;
import br.com.crud.domain.person.usecases.impl.UpdatePersonUseCaseImpl;

@ExtendWith(MockitoExtension.class)
public class UpdatePersonUseCaseImplTest {
  
  @Mock
  PersonRepository personRepository;

  @Mock
  AddressRepository addressRepository;

  @InjectMocks
  UpdatePersonUseCaseImpl updatePersonUseCaseImpl;

  @InjectMocks
  PersonMapper personMapper;

  UpdatePersonDTO updatePersonDTO;
  CreateAddressDTO createAddressDTO;
  List<CreateAddressDTO> addresses;

  @BeforeEach
  void setup(){
    addresses = new ArrayList<>();
    createAddressDTO = new CreateAddressDTO("Test Street", 123, "Test Neighborhood", State.BAHIA, "12345");
    addresses.add(createAddressDTO);
    updatePersonDTO = new UpdatePersonDTO("Test name", LocalDate.now(), "12345678910", addresses);
  }

  @DisplayName("Given Person DTO when Update Person and Addresses Should Return Updated Person and Addresses")
  @Test
  void testGivenPersonDTO_WhenUpdatePersonAndAddresses_ShouldReturnUpdatedPersonAndAddresses() {

    updatePersonDTO.setName("Kaique");
    updatePersonDTO.setBirthDate(LocalDate.now());
    updatePersonDTO.setCpf("78910123456");

    Person person = personMapper.toPerson(updatePersonDTO);

    given(personRepository.findPersonByCpf(updatePersonDTO.getCpf())).willReturn(Optional.of(person));
    given(addressRepository.save(any(Address.class))).willAnswer(invocation -> invocation.getArgument(0));
    given(personRepository.save(any(Person.class))).willAnswer(invocation -> invocation.getArgument(0));

    Person updatedPerson = updatePersonUseCaseImpl.execute(updatePersonDTO.getCpf(), updatePersonDTO);

    assertNotNull(updatedPerson);
    assertEquals(updatePersonDTO.getName(), updatedPerson.getName());
    assertEquals(updatePersonDTO.getBirthDate(), updatedPerson.getBirthDate());
    assertEquals(updatePersonDTO.getCpf(), updatedPerson.getCpf());
    assertEquals(updatePersonDTO.getAddresses().get(0).getStreet(), updatedPerson.getAddresses().get(0).getStreet());

    verify(personRepository, times(1)).findPersonByCpf(updatePersonDTO.getCpf());
    verify(addressRepository, times(1)).save(any(Address.class));
    verify(personRepository, times(1)).save(any(Person.class));
  }
}


