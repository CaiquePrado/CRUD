package br.com.crud.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.crud.domain._exceptions.CpfMismatchException;
import br.com.crud.domain.person.entity.Person;
import br.com.crud.domain.person.usecases.impl.CreatePersonUseCaseImpl;
import br.com.crud.domain.person.usecases.impl.DeletePersonUseCaseImpl;
import br.com.crud.domain.person.usecases.impl.ListAllPeopleUseCaseImpl;
import br.com.crud.domain.person.usecases.impl.UpdatePersonUseCaseImpl;
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

  @Autowired
  private UpdatePersonUseCaseImpl updatePersonUseCaseImpl;

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

  @GetMapping
  public ResponseEntity<List<Person>> getAll(){
    try {
      List<Person> result = listAllPeopleUseCaseImpl.execute();
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PutMapping("/{cpf}")
  public ResponseEntity<Object> update(@PathVariable String cpf, @Valid @RequestBody Person person){
    try {
      if (!person.getCpf().equals(cpf)) {
        throw new CpfMismatchException("The CPF in the URL does not match the CPF of the person to be updated.");
      }
      var result = updatePersonUseCaseImpl.execute(person);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
