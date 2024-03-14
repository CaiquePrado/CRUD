package br.com.crud.domain.person.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
import br.com.crud.domain.person.usecases.impl.FindPersonByIdUseCaseImpl;
import br.com.crud.infra.exceptions.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
public class FindPersonByIdUseCaseImplTest {
  
  @Mock
  PersonRepository personRepository;

  @Mock
  AddressRepository addressRepository;

  @InjectMocks
  FindPersonByIdUseCaseImpl findPersonByIdUseCaseImpl;

  Person person;
  Address address;
  List<Address> addresses;

  @BeforeEach
  void setup(){
    List<Address> addresses = new ArrayList<>();
    person = new Person("Test name", LocalDate.now(), "12345678910", addresses);
    Address addressperson = new Address("Test Street", 123, "Test Neighborhood", State.BAHIA, "12345", person);
    person.getAddresses().add(addressperson);
  }

  @DisplayName("Given PersonId when findById Should Return Person Object")
  @Test
  void testGivenPersonId_WhenFindById_ShouldReturnPersonObject() {

    UUID id = UUID.randomUUID();
    person.setId(id);
    given(personRepository.existsById(id)).willReturn(true);
    given(personRepository.findById(id)).willReturn(Optional.of(person));
    
    Optional<Person> savedPerson = findPersonByIdUseCaseImpl.execute(id);
    
    assertNotNull(savedPerson);
    assertEquals(person.getName(), savedPerson.get().getName());
  }
  
  @DisplayName("Given Wrong PersonId when findById Should Return ResourceNotFoundException")
  @Test
  void testGivenWrongPersonId_WhenFindById_ShouldReturnResourceNotFoundException() {
        
    UUID wrongId = UUID.randomUUID();
    lenient().when(personRepository.findById(wrongId)).thenReturn(Optional.empty());

    assertThrows(ResourceNotFoundException.class, () -> {
      findPersonByIdUseCaseImpl.execute(wrongId);
    });
  }  
}
