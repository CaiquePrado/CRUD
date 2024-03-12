package br.com.crud.domain.person.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
import br.com.crud.domain.person.usecases.impl.ListAllPeopleUseCaseImpl;

@ExtendWith(MockitoExtension.class)
class ListAllPeopleUseCaseImplTest {
  
  @Mock
  PersonRepository personRepository;

  @Mock
  AddressRepository addressRepository;

  @InjectMocks
  ListAllPeopleUseCaseImpl listAllPeopleUseCaseImpl;

  Person person;
  Person personTwo;

  @BeforeEach
  void setup() {
    List<Address> addresses = new ArrayList<>();
    person = new Person("Test name", LocalDate.now(), "12345678910", addresses);
    Address addressperson = new Address("Test Street", 123, "Test Neighborhood", State.BAHIA, "12345", person);
    person.getAddresses().add(addressperson);

    List<Address> addressesTwo = new ArrayList<>();
    personTwo = new Person("Test name", LocalDate.now(), "11111111111", addressesTwo);
    Address addresspersonTwo = new Address("Test Street", 123, "Test Neighborhood", State.BAHIA, "12345", personTwo);
    personTwo.getAddresses().add(addresspersonTwo);
  }

  @DisplayName("Given Persons List when findAll Persons then Return Persons List")
  @Test
  void testGivenPersonsList_WhenFindAllPersons_thenReturnPersonsList() {

    given(personRepository.findAll()).willReturn(List.of(person, personTwo));
    given(addressRepository.findByPerson(person)).willReturn(person.getAddresses());
    given(addressRepository.findByPerson(personTwo)).willReturn(personTwo.getAddresses());

    List<Person> personsList = listAllPeopleUseCaseImpl.execute();

    assertNotNull(personsList);
    assertEquals(2, personsList.size());
    assertTrue(personsList.containsAll(List.of(person, personTwo)));
    verify(personRepository, times(1)).findAll();
    verify(addressRepository, times(2)).findByPerson(any(Person.class));
  }
}


