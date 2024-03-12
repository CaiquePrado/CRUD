package br.com.crud.domain.person.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.never;
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

import br.com.crud.domain._exceptions.CpfNotFoundException;
import br.com.crud.domain.address.entity.Address;
import br.com.crud.domain.address.enums.State;
import br.com.crud.domain.address.repository.AddressRepository;
import br.com.crud.domain.person.entity.Person;
import br.com.crud.domain.person.repository.PersonRepository;
import br.com.crud.domain.person.usecases.impl.DeletePersonUseCaseImpl;

@ExtendWith(MockitoExtension.class)
class DeletePersonUseCaseImplTest {
  
  @Mock
  PersonRepository personRepository;

  @Mock
  AddressRepository addressRepository;

  @InjectMocks
  DeletePersonUseCaseImpl deletePersonUseCaseImpl;
  
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

  @DisplayName("Given Person cpf when Delete Person Should do Nothing")
  @Test
  void testGivenPersonCpf_WhenDeletePerson_ShouldDoNothing(){

    given(personRepository.findPersonByCpf(anyString())).willReturn(Optional.of(person));
    willDoNothing().given(personRepository).delete(person);

    deletePersonUseCaseImpl.execute(person.getCpf());

    verify(personRepository, times(1)).delete(person);
  }

  @DisplayName("Given invalid Person cpf when Delete Person Should throw CpfNotFoundException")
  @Test
  void testGivenInvalidPersonCpf_WhenDeletePerson_ShouldThrowException(){

    String invalidCpf = "invalidCpf";
    given(personRepository.findPersonByCpf(invalidCpf)).willReturn(Optional.empty());

    assertThrows(CpfNotFoundException.class, () -> {
      deletePersonUseCaseImpl.execute(invalidCpf);
    });

    verify(personRepository, never()).delete(any(Person.class));
  }
}
