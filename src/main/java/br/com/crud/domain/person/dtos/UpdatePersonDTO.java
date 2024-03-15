package br.com.crud.domain.person.dtos;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

import br.com.crud.domain.address.dtos.UpdateAddressDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UpdatePersonDTO{

  @NotBlank
  @Size(min = 3, message = "Name must have at least 3 characters")
  String name;

  @NotNull
  LocalDate birthDate;

  @CPF(message = "CPF must contain 11 numeric characters")
  String cpf;

  @Valid
  private List<UpdateAddressDTO> addresses;

}