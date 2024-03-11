package br.com.crud.domain.person.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

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

import br.com.crud.domain.address.entity.Address;
import br.com.crud.domain.address.enums.State;
import br.com.crud.domain.address.repository.AddressRepository;
import br.com.crud.domain.person.entity.Person;
import br.com.crud.domain.person.repository.PersonRepository;
import br.com.crud.domain.person.services.usecases.impl.CreatePersonUseCaseImpl;

@ExtendWith(MockitoExtension.class)
class CreatePersonUseCaseImplTest {
  
  @Mock
  PersonRepository personRepository;

  @Mock
  AddressRepository addressRepository;

  @InjectMocks
  CreatePersonUseCaseImpl createPersonUseCaseImpl;

  Person person;
  Address address;
  List<Address> addresses;

  @BeforeEach
  void setup(){
    addresses = new ArrayList<>();
    person = new Person("Test name", LocalDate.now(), "12345678910", addresses);
    address = new Address("Test Street", 123, "Test Neighborhood", State.BAHIA, "12345", person);
    person.getAddresses().add(address);
  }

  @DisplayName("Given Person Object when Save Person then Return Person Object")
  @Test
  void testGivenPersonObject_WhenSavePerson_ShouldReturnPersonObject() {

    given(personRepository.findPersonByCpf(anyString())).willReturn(Optional.empty());
    given(personRepository.save(person)).willReturn(person);
    given(addressRepository.save(any(Address.class))).willAnswer(invocation -> invocation.getArgument(0));

    Person savedPerson = createPersonUseCaseImpl.execute(person);

    assertNotNull(savedPerson);
    assertEquals("Test name", savedPerson.getName());
    assertFalse(savedPerson.getAddresses().isEmpty());
  }
}
