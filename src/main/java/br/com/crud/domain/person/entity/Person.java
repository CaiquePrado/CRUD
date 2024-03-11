package br.com.crud.domain.person.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.com.crud.domain.address.entity.Address;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_person")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Person {

  public Person(String name, LocalDate birthDate, String cpf, List<Address> addresses) {
    this.name = name;
    this.birthDate = birthDate;
    this.cpf = cpf;
    this.addresses = addresses;
  }
  
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  
  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private LocalDate birthDate;

  @Column(unique = true, nullable = false, columnDefinition = "VARCHAR(11)")
  private String cpf;

  @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
  private List<Address> addresses = new ArrayList<>();
}
