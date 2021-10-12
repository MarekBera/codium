package cz.bera.codium.service;

import cz.bera.codium.controller.model.Person;

import javax.validation.constraints.NotNull;

public interface RedisService {

  void storePerson(
      @NotNull Long id,
      @NotNull Person person
  );
}
