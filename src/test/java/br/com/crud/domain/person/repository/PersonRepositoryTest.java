package br.com.crud.domain.person.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.crud.domain.address.entity.Address;
import br.com.crud.domain.address.enums.State;
import br.com.crud.domain.address.repository.AddressRepository;
import br.com.crud.domain.person.entity.Person;
import br.com.crud.integrationTest.testcontainers.AbstractIntegrationTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PersonRepositoryTest extends AbstractIntegrationTest {
  
  @Autowired
  PersonRepository personRepository;

  @Autowired
  AddressRepository addressRepository;

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

  @DisplayName("Given Person OBject When Save Should Return Saved Person")
  @Test
  void testGivenPersonOBject_WhenSave_ShouldReturnSavedPerson() {

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

  @DisplayName("Given Person Object When FindById Should Return Person Object")
  @Test
  void testGivenPersonOBject_WhenFindById_ShouldReturnPersonObject() {

    personRepository.save(person);

    Person savedPerson = personRepository.findById(person.getId()).get();

    assertNotNull(savedPerson);
    assertNotNull(savedPerson.getId());
    assertEquals(person.getId(), savedPerson.getId());
  }

  @DisplayName("Given Person Object When FindByCpf Should Return Person Object")
  @Test
  void testGivenPersonOBject_WhenFindByCpf_ShouldReturnPersonObject() {

    personRepository.save(person);

    Person savedPerson = personRepository.findPersonByCpf(person.getCpf()).get();

    assertNotNull(savedPerson);
    assertNotNull(savedPerson.getCpf());
    assertEquals(person.getCpf(), savedPerson.getCpf());
  }

  @DisplayName("Given Person Object When Update Person and Addresses Should Return Person and Addresses")
  @Test
  void testGivenPersonOBject_WhenUpdatePersonAndAddresses_ShouldReturnPersonAndAddresses() {
  
    personRepository.save(person);

    Person savedPerson = personRepository.findById(person.getId()).get();
    savedPerson.setName("Roger");
    savedPerson.setCpf("22222222222");

    Address savedAddress = savedPerson.getAddresses().get(0);
    savedAddress.setStreet("Updated Street");
    savedAddress.setNumber(456);
    savedAddress.setNeighborhood("Updated Neighborhood");
    savedAddress.setState(State.SAO_PAULO);
    savedAddress.setZipCode("54321");

    Person updatededPerson = personRepository.save(savedPerson); 
    Address uptadetededAddress = addressRepository.save(savedAddress); 

    assertNotNull(updatededPerson);
    assertEquals("Roger", updatededPerson.getName());
    assertEquals("22222222222", updatededPerson.getCpf());

    assertNotNull(uptadetededAddress);
    assertEquals("Updated Street", uptadetededAddress.getStreet());
    assertEquals(456, uptadetededAddress.getNumber());
    assertEquals("Updated Neighborhood", uptadetededAddress.getNeighborhood());
    assertEquals(State.SAO_PAULO, uptadetededAddress.getState());
    assertEquals("54321", uptadetededAddress.getZipCode());
  }

  @DisplayName("Given Person Object When Delete Person and addresses Should Remove Person and addresses")
  @Test
  void testGivenPersonOBject_WhenDeletePerson_ShouldReturnRemovePersonAndAddresses() {
  
    personRepository.save(person);

    person.getAddresses().stream().forEach(addressInAddresses -> addressRepository.deleteById(addressInAddresses.getId()));
    personRepository.deleteById(person.getId());

    Optional<Person> personOptional = personRepository.findById(person.getId());
    assertTrue(personOptional.isEmpty());

    person.getAddresses().stream().forEach(addressInAddresses -> {
        Optional<Address> addressOptional = addressRepository.findById(addressInAddresses.getId());
        assertTrue(addressOptional.isEmpty());
    });
  }
}
