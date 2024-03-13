package br.com.crud.domain.person.usecases.impl;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.stereotype.Service;

import br.com.crud.domain.person.entity.Person;
import br.com.crud.domain.person.usecases.ShowPersonAgeUseCase;

@Service
public class ShowPersonAgeUseCaseImpl implements ShowPersonAgeUseCase {

  @Override
  public Integer execute(Person person) {
    LocalDate now = LocalDate.now();
    Integer age = Period.between(person.getBirthDate(), now).getYears();
    return age;
  }
}
