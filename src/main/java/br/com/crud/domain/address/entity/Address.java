package br.com.crud.domain.address.entity;

import br.com.crud.domain.address.enums.State;
import br.com.crud.domain.person.entity.Person;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_address")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
  
  @Column(nullable = false)
  private String street;

  @Column(nullable = false)
  private int number;

  @Column(nullable = false)
  private String neighborhood;
  
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private State state;

  @Column(nullable = false)
  private String zipCode;

  @ManyToOne
  @JoinColumn(name = "person_id")
  private Person person;
}
