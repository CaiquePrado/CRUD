package br.com.crud.domain.person.impl;

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

import br.com.crud.domain.address.dtos.UpdateAddressDTO;
import br.com.crud.domain.address.entity.Address;
import br.com.crud.domain.address.enums.State;
import br.com.crud.domain.address.repository.AddressRepository;
import br.com.crud.domain.person.dtos.UpdatePersonDTO;
import br.com.crud.domain.person.dtos.mapper.PersonMapper;
import br.com.crud.domain.person.entity.Person;
import br.com.crud.domain.person.repository.PersonRepository;
import br.com.crud.domain.person.usecases.impl.UpdatePersonUseCaseImpl;

@ExtendWith(MockitoExtension.class)
class UpdatePersonUseCaseImplTest {

  @Mock
  PersonRepository personRepository;

  @Mock
  AddressRepository addressRepository;

  @Mock
  PersonMapper personMapper;

  @InjectMocks
  UpdatePersonUseCaseImpl updatePersonUseCaseImpl;

  UpdatePersonDTO updatePersonDTO;
  UpdateAddressDTO updateAddressDTO;

  @BeforeEach
  void setup(){
    List<UpdateAddressDTO> addressesDTO = new ArrayList<>();
    updateAddressDTO = new UpdateAddressDTO("Updated Street", 123, "Updated Neighborhood", State.BAHIA, "Updated ZipCode");
    addressesDTO.add(updateAddressDTO);
    updatePersonDTO = new UpdatePersonDTO("Updated name", LocalDate.now(), "10512971501", addressesDTO);
  }
  
  @DisplayName("Given Person Object when Update Person and Addresses Should Return Updated Person and Addresses")
  @Test
  void testGivenPersonObject_WhenUpdatePersonAndAddresses_ShouldReturnUpdatedPersonAndAddresses() {
    Person existingPerson = new Person("Test name", LocalDate.now(), "12345678910", new ArrayList<>());
    Person updatedPerson = new Person(updatePersonDTO.getName(), updatePersonDTO.getBirthDate(), updatePersonDTO.getCpf(), new ArrayList<>());

    given(personRepository.findPersonByCpf(anyString())).willReturn(Optional.of(existingPerson));
    given(personMapper.toPerson(updatePersonDTO)).willReturn(updatedPerson);
    given(personRepository.save(any(Person.class))).willAnswer(invocation -> invocation.getArgument(0));

    updatePersonDTO.getAddresses().forEach(addressDTO -> {
      Address address = new Address(addressDTO.getStreet(), addressDTO.getNumber(), addressDTO.getNeighborhood(), addressDTO.getState(), addressDTO.getZipCode(), existingPerson);
      given(addressRepository.save(any(Address.class))).willReturn(address);
      updatedPerson.getAddresses().add(address);
    });

    Person resultPerson = updatePersonUseCaseImpl.execute(existingPerson.getCpf(), updatePersonDTO);

    assertNotNull(resultPerson);
    assertEquals(updatePersonDTO.getName(), resultPerson.getName());
    assertEquals(updatePersonDTO.getCpf(), resultPerson.getCpf());
    assertFalse(resultPerson.getAddresses().isEmpty());

    updatePersonDTO.getAddresses().forEach(addressDTO -> {
    Address resultAddress = resultPerson.getAddresses().stream().filter(address -> address.getStreet().equals(addressDTO.getStreet())).findFirst().orElse(null);

    assertNotNull(resultAddress);
    assertEquals(addressDTO.getStreet(), resultAddress.getStreet());
    assertEquals(addressDTO.getNumber(), resultAddress.getNumber());
    assertEquals(addressDTO.getNeighborhood(), resultAddress.getNeighborhood());
    assertEquals(addressDTO.getState(), resultAddress.getState());
    assertEquals(addressDTO.getZipCode(), resultAddress.getZipCode());
  });
}
}


