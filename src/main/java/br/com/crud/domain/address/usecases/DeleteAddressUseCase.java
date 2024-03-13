package br.com.crud.domain.address.usecases;

import java.util.UUID;

public interface DeleteAddressUseCase {
  
  void execute(UUID id);

}
