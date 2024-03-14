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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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

  @DisplayName("Given Persons List when findAll Persons Should Return Persons List")
  @Test
  void testGivenPersonsList_WhenFindAllPersons_ShouldReturnPersonsList() {

    int page = 0;
    int size = 10;
    Pageable pageable = PageRequest.of(page, size);
    Page<Person> personPage = new PageImpl<>(List.of(person, personTwo));

    given(personRepository.findAll(pageable)).willReturn(personPage);
    given(addressRepository.findByPerson(person)).willReturn(person.getAddresses());
    given(addressRepository.findByPerson(personTwo)).willReturn(personTwo.getAddresses());

    Page<Person> personsPage = listAllPeopleUseCaseImpl.execute(page, size);

    assertNotNull(personsPage);
    assertEquals(2, personsPage.getContent().size());
    assertTrue(personsPage.getContent().containsAll(List.of(person, personTwo)));
    verify(personRepository, times(1)).findAll(pageable);
    verify(addressRepository, times(2)).findByPerson(any(Person.class));
  }
}


