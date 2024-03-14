package br.com.crud.domain.person.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import br.com.crud.domain._exceptions.CpfMismatchException;
import br.com.crud.domain.address.entity.Address;
import br.com.crud.domain.address.enums.State;
import br.com.crud.domain.address.repository.AddressRepository;
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

  @DisplayName("Given Person Object when Update Person and Addresses Should Return Updated Person and Addresses")
  @Test
  void testGivenPersonObject_WhenUpdatePersonAndAddresses_ShouldReturnUpdatedPersonAndAddresses() {

    person.setName("Kaique");
    person.setBirthDate(LocalDate.now());
    person.setCpf("78910123456");

    given(personRepository.findPersonByCpf(person.getCpf())).willReturn(Optional.of(person));
    given(addressRepository.save(any(Address.class))).willAnswer(invocation -> invocation.getArgument(0));
    given(personRepository.save(any(Person.class))).willAnswer(invocation -> invocation.getArgument(0));

    Person updatedPerson = updatePersonUseCaseImpl.execute(person.getCpf(),person);

    assertNotNull(updatedPerson);
    assertEquals(person.getName(), updatedPerson.getName());
    assertEquals(person.getBirthDate(), updatedPerson.getBirthDate());
    assertEquals(person.getCpf(), updatedPerson.getCpf());
    assertEquals(person.getAddresses().get(0).getStreet(), updatedPerson.getAddresses().get(0).getStreet());

    verify(personRepository, times(1)).findPersonByCpf(person.getCpf());
    verify(addressRepository, times(1)).save(any(Address.class));
    verify(personRepository, times(1)).save(any(Person.class));
  }

  @Test
  @DisplayName("Given Mismatched CPF when Update Person Should Throw CpfMismatchException")
  void testGivenMismatchedCpf_WhenUpdatePerson_ShouldThrowCpfMismatchException() {

    person.setName("Kaique");
    person.setBirthDate(LocalDate.now());
    person.setCpf("78910123456");

    String mismatchedCpf = "11111111111";

    assertThrows(CpfMismatchException.class, () -> {
      updatePersonUseCaseImpl.execute(mismatchedCpf, person);
    });
  }
}
