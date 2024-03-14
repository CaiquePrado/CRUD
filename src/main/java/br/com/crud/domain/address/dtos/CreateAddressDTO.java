package br.com.crud.domain.address.dtos;

import br.com.crud.domain.address.enums.State;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateAddressDTO {

  @NotBlank
  private String street;

  @NotNull
  private int number;

  @NotBlank
  private String neighborhood;

  @NotNull
  @Enumerated(EnumType.STRING)
  private State state;

  @Pattern(regexp = "^[0-9]{8}$", message = "zip code format is invalid")
  @NotBlank
  private String zipCode;

}