package br.com.crud.domain.address.repository;

import java.util.UUID;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.crud.domain.address.entity.Address;
import br.com.crud.domain.person.entity.Person;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
  
  List<Address> findByPerson(Person person);

}
