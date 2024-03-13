package br.com.crud.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.crud.domain.address.entity.Address;
import br.com.crud.domain.address.usecases.impl.CreateAddressUseCaseImpl;
import br.com.crud.domain.address.usecases.impl.DeleteAddressUseCaseImpl;
import br.com.crud.domain.address.usecases.impl.UpdateAddressUseCaseImpl;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/address")
public class AddressController {
  
  @Autowired
  private CreateAddressUseCaseImpl createAddressUseCaseImpl;

  @Autowired
  private DeleteAddressUseCaseImpl deleteAddressUseCaseImpl;

  @Autowired
  private UpdateAddressUseCaseImpl updateAddressUseCaseImpl;
  
  @PostMapping("/{id}")
  public ResponseEntity<Object> create(@PathVariable UUID personId, @Valid @RequestBody Address address){
    try {
      var result = createAddressUseCaseImpl.execute(address, personId);
      return ResponseEntity.status(HttpStatus.CREATED).body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> delete(@PathVariable UUID id){
    try {
      deleteAddressUseCaseImpl.execute(id);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> update(@PathVariable UUID id, @Valid @RequestBody Address address){
    try {
      var result = updateAddressUseCaseImpl.execute(address, id);
      return ResponseEntity.status(HttpStatus.OK).body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
