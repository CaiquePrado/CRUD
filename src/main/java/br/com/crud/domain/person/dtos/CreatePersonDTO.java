package br.com.crud.domain.person.dtos;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

import br.com.crud.domain.address.dtos.CreateAddressDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreatePersonDTO {

  @NotBlank
  @Size(min = 3, message = "Name must have at least 3 characters")
  private String name;

  @NotNull
  private LocalDate birthDate;

  @NotBlank
  @CPF(message = "CPF must contain 11 numeric characters")
  private String cpf;

  @Valid
  private List<CreateAddressDTO> addresses;

}
