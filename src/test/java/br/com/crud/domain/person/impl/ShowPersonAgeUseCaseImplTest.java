package br.com.crud.domain.person.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.crud.domain.address.entity.Address;
import br.com.crud.domain.address.enums.State;
import br.com.crud.domain.person.entity.Person;
import br.com.crud.domain.person.usecases.impl.ShowPersonAgeUseCaseImpl;

@ExtendWith(MockitoExtension.class)
public class ShowPersonAgeUseCaseImplTest {

  @InjectMocks
  private ShowPersonAgeUseCaseImpl showPersonAgeUseCaseImpl;

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
  
  @DisplayName("Given Person Object When ShowAge Should Return Age")
  @Test
  void testGivenPersonObject_WhenShowAge_ShouldReturnAge() {

    LocalDate now = LocalDate.now();
    Integer expectedAge = Period.between(person.getBirthDate(), now).getYears();

    var age = showPersonAgeUseCaseImpl.execute(person);

    assertEquals(expectedAge, age);
  }
}

