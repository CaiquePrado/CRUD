package br.com.crud.domain.person.Controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.crud.domain.address.dtos.CreateAddressDTO;
import br.com.crud.domain.address.entity.Address;
import br.com.crud.domain.address.enums.State;
import br.com.crud.domain.person.dtos.CreatePersonDTO;
import br.com.crud.domain.person.entity.Person;
import br.com.crud.domain.person.usecases.impl.CreatePersonUseCaseImpl;
import br.com.crud.domain.person.usecases.impl.DeletePersonUseCaseImpl;
import br.com.crud.domain.person.usecases.impl.FindPersonByIdUseCaseImpl;
import br.com.crud.domain.person.usecases.impl.ListAllPeopleUseCaseImpl;
import br.com.crud.domain.person.usecases.impl.UpdatePersonUseCaseImpl;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PersonControllerTest {
  
  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  CreatePersonUseCaseImpl createPersonUseCaseImpl;

  @MockBean 
  ListAllPeopleUseCaseImpl listAllPeopleUseCaseImpl;

  @MockBean
  DeletePersonUseCaseImpl deletePersonUseCaseImpl;

  @MockBean
  FindPersonByIdUseCaseImpl findPersonByIdUseCaseImpl;

  @MockBean
  UpdatePersonUseCaseImpl updatePersonUseCaseImpl;

  Person person;
  Address address;
  List<Address> addresses;

  CreatePersonDTO createPersonDTO;
  CreateAddressDTO createAddressDTO;

  @BeforeEach
  void setup(){
    addresses = new ArrayList<>();
    person = new Person("Test name", LocalDate.now(), "12345678910", addresses);
    address = new Address("Test Street", 123, "Test Neighborhood", State.BAHIA, "12345", person);
    person.getAddresses().add(address);
  }
  
  @Test
  @DisplayName("Given List of Persons when findAll Persons Should Return People List")
  void testGivenListOfPersons_WhenFindAllPersons_ShouldReturnPersonsList() throws JsonProcessingException, Exception {
    
    List<Person> persons = new ArrayList<>();
    persons.add(person);

    int page = 0;
    int size = 10;
    Page<Person> personPage = new PageImpl<>(persons);

    given(listAllPeopleUseCaseImpl.execute(page, size)).willReturn(personPage);

    ResultActions response = mockMvc.perform(get("/api/v1/person")
        .param("page", String.valueOf(page))
        .param("size", String.valueOf(size)));

    response.
    andExpect(status().isOk())
    .andDo(print())
    .andExpect(jsonPath("$.content.size()", is(persons.size())));
  }

  @Test
  @DisplayName("Given personCpf when Delete Should Return NotContent")
  void testGivenPersonId_WhenDelete_ShouldReturnNotContent() throws JsonProcessingException, Exception {

    willDoNothing().given(deletePersonUseCaseImpl).execute(person.getCpf());
    
    ResultActions response = mockMvc.perform(delete("/api/v1/person/{cpf}", person.getCpf()));

    response
    .andExpect(status().isNoContent())
    .andDo(print());
  }

  @Test
  @DisplayName("Given Mismatched CPF when Update Should Throw InvalidRequestException")
  void testGivenMismatchedCPF_WhenUpdate_ShouldThrowInvalidRequestException() throws JsonProcessingException, Exception {

    Person updatedPerson = new Person("Caique name", LocalDate.now(), "12345678910", addresses);
    String mismatchedCpf = "22122233344";

    ResultActions response = mockMvc.perform(put("/api/v1/person/{cpf}", mismatchedCpf)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updatedPerson))
    );

    response
    .andExpect(status().isBadRequest())
    .andDo(print());
  }
}