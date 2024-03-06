package br.com.crud.domain.address.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
  private String state;

  @Column(nullable = false)
  private String zipCode;
}
