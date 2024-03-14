package br.com.crud.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.crud.domain.person.dtos.CreatePersonDTO;
import br.com.crud.domain.person.dtos.UpdatePersonDTO;
import br.com.crud.domain.person.entity.Person;
import br.com.crud.domain.person.usecases.impl.CreatePersonUseCaseImpl;
import br.com.crud.domain.person.usecases.impl.DeletePersonUseCaseImpl;
import br.com.crud.domain.person.usecases.impl.FindPersonByIdUseCaseImpl;
import br.com.crud.domain.person.usecases.impl.ListAllPeopleUseCaseImpl;
import br.com.crud.domain.person.usecases.impl.UpdatePersonUseCaseImpl;
import io.swagger.v3.oas.annotations.Operation;
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

  @Autowired
  private FindPersonByIdUseCaseImpl findPersonByIdUseCaseImpl;

  @PostMapping
  @Operation(summary = "Create person")
  public ResponseEntity<Object> create(@Valid @RequestBody CreatePersonDTO createPersonDTO){
    try {
      var result = createPersonUseCaseImpl.execute(createPersonDTO);
      return ResponseEntity.status(HttpStatus.CREATED).body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/{cpf}")
  @Operation(summary = "Delete person by cpf")
  public ResponseEntity<Object> delete(@PathVariable String cpf){
    try {
      deletePersonUseCaseImpl.execute(cpf);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping
  @Operation(summary = "list all people")
  public ResponseEntity<Page<Person>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
    try {
      Page<Person> result = listAllPeopleUseCaseImpl.execute(page, size);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PutMapping("/{cpf}")
  @Operation(summary = "Update person by cpf")
  public ResponseEntity<Object> update(@PathVariable String cpf, @Valid @RequestBody UpdatePersonDTO updatePersonDTO){
    try {
      var result = updatePersonUseCaseImpl.execute(cpf, updatePersonDTO);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/{id}")
  @Operation(summary = "Find person by id")
  public ResponseEntity<Object> getById(@PathVariable UUID id){
    try {
      Optional<Person> result = findPersonByIdUseCaseImpl.execute(id);
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }
}
