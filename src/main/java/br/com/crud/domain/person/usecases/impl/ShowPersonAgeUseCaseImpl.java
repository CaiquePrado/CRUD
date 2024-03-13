package br.com.crud.domain.person.usecases.impl;

import java.time.LocalDate;
import java.time.Period;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.crud.domain.person.entity.Person;
import br.com.crud.domain.person.usecases.ShowPersonAgeUseCase;

@Service
public class ShowPersonAgeUseCaseImpl implements ShowPersonAgeUseCase {

  private static final Logger log = LoggerFactory.getLogger(ShowPersonAgeUseCaseImpl.class);

  @Override
  public Integer execute(Person person) {
    LocalDate now = LocalDate.now();
    Integer age = Period.between(person.getBirthDate(), now).getYears();
    log.info("The age of the person is: {}", age);
    return age;
  }
}
