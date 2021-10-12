package cz.bera.codium.service;

import cz.bera.codium.controller.exception.NotFoundException;
import cz.bera.codium.controller.model.BankCard;
import cz.bera.codium.controller.model.Person;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface BankService {

  /**
   * Stores {@link Person} into database and cache.
   * @param person {@link Person} with its {@link BankCard}s
   *
   * @return ID of person
   */
  @NotNull
  Long createPerson(@NotNull Person person);

  /**
   * Gets {@link Person} with {@link BankCard} from database by its ID.
   * In case ID does not exist returns throws exception.
   * @param id ID used to find Person
   * @throws NotFoundException
   * @return {@link Person}
   */
  @NotNull
  Person getPersonById(@NotNull Long id);

  /**
   * Returns all existing {@link Person} from database.
   *
   * @return List of all {@link Person}
   */
  @NotNull
  List<Person> findAll();
}
