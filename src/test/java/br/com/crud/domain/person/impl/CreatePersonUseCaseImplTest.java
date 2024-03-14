package br.com.crud.domain.person.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
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
import br.com.crud.domain.person.dtos.CreatePersonDTO;
import br.com.crud.domain.person.dtos.UpdatePersonDTO;
import br.com.crud.domain.person.dtos.mapper.PersonMapper;
import br.com.crud.domain.person.entity.Person;
import br.com.crud.domain.person.repository.PersonRepository;
import br.com.crud.domain.person.usecases.impl.CreatePersonUseCaseImpl;
import br.com.crud.infra.exceptions.InvalidRequestException;

@ExtendWith(MockitoExtension.class)
class CreatePersonUseCaseImplTest {
  
  @Mock
  PersonRepository personRepository;

  @Mock
  AddressRepository addressRepository;

  @InjectMocks
  CreatePersonUseCaseImpl createPersonUseCaseImpl;

  PersonMapper personMapper;

  CreateAddressDTO createAddressDTO;
  List<CreateAddressDTO> addresses;
  List<CreateAddressDTO> addressesDTO;
  CreatePersonDTO createPersonDTO;
  UpdatePersonDTO updatePersonDTO;
 

  @BeforeEach
  void setup(){
    addressesDTO = new ArrayList<>();
    createAddressDTO = new CreateAddressDTO("Test Street", 123, "Test Neighborhood", State.BAHIA, "12345");
    addressesDTO.add(createAddressDTO);
    createPersonDTO = new CreatePersonDTO("Test name", LocalDate.now(), "02326713036", addressesDTO);
  }

  @DisplayName("Given Person DTO when Save Person should Return Person and addresses")
  @Test
  void testGivenPersonDTO_WhenSavePerson_ShouldReturnPersonAndAddresses() {
    Person person = new Person("Test name", LocalDate.now(), "02326713036", new ArrayList<>());
  
    given(personMapper.toPerson(any(CreatePersonDTO.class))).willReturn(person);

    given(personRepository.findPersonByCpf(anyString())).willReturn(Optional.empty());
    given(personRepository.save(any(Person.class))).willAnswer(invocation -> invocation.getArgument(0));
    given(addressRepository.save(any(Address.class))).willAnswer(invocation -> invocation.getArgument(0));

    Person savedPerson = createPersonUseCaseImpl.execute(createPersonDTO);

    assertNotNull(savedPerson);
    assertEquals("Test name", savedPerson.getName());
    assertFalse(savedPerson.getAddresses().isEmpty());
  }
  
  @DisplayName("Given Person DTO with Existing CPF when Save Person should Throw  InvalidRequestException")
  @Test
  void testGivenPersonDTOWithExistingCPF_WhenSavePerson_ShouldThrowInvalidRequestException() {
    given(personRepository.findPersonByCpf(anyString())).willReturn(Optional.of(new Person()));

    assertThrows( InvalidRequestException.class, () -> {
      createPersonUseCaseImpl.execute(createPersonDTO);
    });

    verify(personRepository, never()).save(any(Person.class));
  }
}

