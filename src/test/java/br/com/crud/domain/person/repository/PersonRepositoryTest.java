package br.com.crud.domain.person.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.crud.domain.address.entity.Address;
import br.com.crud.domain.address.enums.State;
import br.com.crud.domain.address.repository.AddressRepository;
import br.com.crud.domain.person.entity.Person;

@DataJpaTest
public class PersonRepositoryTest {
  
  @Autowired
  PersonRepository personRepository;

  @Autowired
  AddressRepository addressRepository;

  @DisplayName("Given Person OBject When Save Should Return Saved Person")
  @Test
  void testGivenPersonOBject_WhenSave_ShouldReturnSavedPerson() {

    List<Address> addresses = new ArrayList<>();
    Person person = new Person("Test name", LocalDate.now(), "12345678910", addresses);
    Address address = new Address("Test Street", 123, "Test Neighborhood", State.BAHIA, "12345", person);
    person.getAddresses().add(address);

    Person savedPerson = personRepository.save(person);

    assertNotNull(savedPerson);
    assertNotNull(savedPerson.getId());
    assertEquals("Test name", savedPerson.getName());
    assertEquals("12345678910", savedPerson.getCpf());

    assertNotNull(address.getId());
    assertEquals("Test Street", address.getStreet());
    assertEquals(123, address.getNumber());
    assertEquals("Test Neighborhood", address.getNeighborhood());
    assertEquals(State.BAHIA, address.getState());
    assertEquals("12345", address.getZipCode());
  }

  @DisplayName("Given People List When FindAll Should Return People List with Addresses")
  @Test
  void testGivenPeopleList_WhenFindAll_ShouldReturnPeopleListWithAddresses() {

    List<Address> addressesPerson = new ArrayList<>();
    Person person = new Person("Test name", LocalDate.now(), "12345678910", addressesPerson);
    Address address = new Address("Test Street", 123, "Test Neighborhood", State.BAHIA, "12345", person);
    person.getAddresses().add(address);
    personRepository.save(person);

    List<Address> addressesPersonTwo = new ArrayList<>();
    Person personTwo = new Person("Test name", LocalDate.now(), "11111111111", addressesPersonTwo);
    Address addressTwo = new Address("Test Street", 123, "Test Neighborhood", State.BAHIA, "12345", person);
    personTwo.getAddresses().add(addressTwo);
    personRepository.save(personTwo);

    List<Person> peopleList = personRepository.findAll();

    assertNotNull(peopleList);
    assertEquals(2, peopleList.size());

    peopleList.stream().forEach(personInList -> {
      assertNotNull(personInList.getAddresses());
      assertFalse(personInList.getAddresses().isEmpty());
    });
  }
}
