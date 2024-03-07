package br.com.crud.domain.address.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.crud.domain.address.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
  
}
