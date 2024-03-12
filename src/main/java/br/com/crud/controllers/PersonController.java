package br.com.crud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.crud.domain.person.entity.Person;
import br.com.crud.domain.person.services.usecases.impl.CreatePersonUseCaseImpl;
import br.com.crud.domain.person.services.usecases.impl.DeletePersonUseCaseImpl;
import br.com.crud.domain.person.services.usecases.impl.ListAllPeopleUseCaseImpl;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/person")
public class PersonController {
  
  @Autowired
  private CreatePersonUseCaseImpl createPersonUseCaseImpl;

  @Autowired
  private DeletePersonUseCaseImpl deletePersonUseCaseImpl;

  @Autowired
  private ListAllPeopleUseCaseImpl listAllPeopleUseCaseImpl;

  @PostMapping
  public ResponseEntity<Object> create(@Valid @RequestBody Person person){
    try {
      var result = createPersonUseCaseImpl.execute(person);
      return ResponseEntity.status(HttpStatus.CREATED).body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/{cpf}")
  public ResponseEntity<Object> delete(@PathVariable String cpf){
    try {
      deletePersonUseCaseImpl.execute(cpf);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
