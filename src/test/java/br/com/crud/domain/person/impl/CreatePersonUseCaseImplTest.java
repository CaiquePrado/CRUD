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

  @Mock
  PersonMapper personMapper;

  @InjectMocks
  CreatePersonUseCaseImpl createPersonUseCaseImpl;

  CreatePersonDTO createPersonDTO;
  CreateAddressDTO createAddressDTO;

  @BeforeEach
  void setup(){
    List<CreateAddressDTO> addressesDTO = new ArrayList<>();
    createAddressDTO = new CreateAddressDTO("Test Street", 123, "Test Neighborhood", State.BAHIA, "12345");
    addressesDTO.add(createAddressDTO);
    createPersonDTO = new CreatePersonDTO("Test name", LocalDate.now(), "10512971501", addressesDTO);
  }

  @DisplayName("Given Person Object when Save Person should Return Person and addresses")
  @Test
  void testGivenPersonObject_WhenSavePerson_ShouldReturnPersonAndAddresses() {
    
    Person person = new Person(createPersonDTO.getName(), createPersonDTO.getBirthDate(), createPersonDTO.getCpf(), new ArrayList<>());
    Address address = new Address(createAddressDTO.getStreet(), createAddressDTO.getNumber(), createAddressDTO.getNeighborhood(), createAddressDTO.getState(), createAddressDTO.getZipCode(), person);
    person.getAddresses().add(address);

    given(personMapper.toPerson(createPersonDTO)).willReturn(person);
    given(personRepository.findPersonByCpf(anyString())).willReturn(Optional.empty());
    given(personRepository.save(any(Person.class))).willReturn(person);
    given(addressRepository.save(any(Address.class))).willReturn(address);

    Person savedPerson = createPersonUseCaseImpl.execute(createPersonDTO);

    assertNotNull(savedPerson);
    assertEquals(createPersonDTO.getName(), savedPerson.getName());
    assertFalse(savedPerson.getAddresses().isEmpty());

    Address savedAddress = savedPerson.getAddresses().get(0);
    assertEquals(createAddressDTO.getStreet(), savedAddress.getStreet());
    assertEquals(createAddressDTO.getNumber(), savedAddress.getNumber());
    assertEquals(createAddressDTO.getNeighborhood(), savedAddress.getNeighborhood());
    assertEquals(createAddressDTO.getState(), savedAddress.getState());
    assertEquals(createAddressDTO.getZipCode(), savedAddress.getZipCode());
  }

  @DisplayName("Given Person Object with Existing CPF when Save Person should Throw InvalidRequestException")
  @Test
  void testGivenPersonObjectWithExistingCPF_WhenSavePerson_ShouldThrowInvalidRequestException() {
    Person person = new Person(createPersonDTO.getName(), createPersonDTO.getBirthDate(), createPersonDTO.getCpf(), new ArrayList<>());

    given(personMapper.toPerson(createPersonDTO)).willReturn(person);
    given(personRepository.findPersonByCpf(anyString())).willReturn(Optional.of(person));
    
    assertThrows(InvalidRequestException.class, () -> {
      createPersonUseCaseImpl.execute(createPersonDTO);
    });

    verify(personRepository, never()).save(any(Person.class));
  }
}