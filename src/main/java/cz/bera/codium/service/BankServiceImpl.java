package cz.bera.codium.service;

import cz.bera.codium.controller.exception.ConflictException;
import cz.bera.codium.controller.exception.NotFoundException;
import cz.bera.codium.controller.model.BankCard;
import cz.bera.codium.controller.model.Person;
import cz.bera.codium.repository.BankCardRepository;
import cz.bera.codium.repository.PersonRepository;
import cz.bera.codium.repository.entity.sql.BankCardVO;
import cz.bera.codium.repository.entity.sql.PersonVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BankServiceImpl extends AbstractService implements BankService {

  private final PersonRepository personRepository;
  private final BankCardRepository bankCardRepository;
  private final RedisService redisService;

  public BankServiceImpl(
      @NotNull final PersonRepository personRepository,
      @NotNull final BankCardRepository bankCardRepository,
      @NotNull final RedisService redisService
  ) {
    this.personRepository = personRepository;
    this.bankCardRepository = bankCardRepository;
    this.redisService = redisService;
  }

  @Transactional
  @Override
  public @NotNull Long createPerson(@NotNull final Person person) {
    final PersonVO personVO = map(person);

    personRepository.save(personVO);
    try {
      bankCardRepository.saveAll(personVO.getBankCards());
    } catch (DataIntegrityViolationException ex) {
      throw new ConflictException(ex.getLocalizedMessage());
    }
    redisService.storePerson(personVO.getId(), person);

    return personVO.getId();
  }

  @Transactional(readOnly = true)
  @Override
  public @NotNull Person getPersonById(@NotNull final Long id) {
    return personRepository.findPersonWithCardById(id)
        .map(this::map)
        .orElseThrow(() -> new NotFoundException(
            messageSource.getMessage(
                "error.person.id.not-found",
                new Object[]{id},
                LocaleContextHolder.getLocale())
            )
        )
    ;
  }

  @Transactional(readOnly = true)
  @Override
  public @NotNull List<Person> findAll() {
    return personRepository
        .findAllPersonWithCards()
        .stream()
        .map(this::map)
        .collect(Collectors.toUnmodifiableList())
    ;
  }

  private PersonVO map(@NotNull final Person person) {
    final PersonVO personVO = new PersonVO();
    personVO.setFirstname(person.getFirstname());
    personVO.setLastname(person.getLastname());
    personVO.setBankCards(
        person
            .getBankCards()
            .stream()
            .map(bankCardVO -> map(personVO, bankCardVO))
            .collect(Collectors.toUnmodifiableList())
    );

    return personVO;
  }

  private BankCardVO map(
      @NotNull final PersonVO personVO,
      @NotNull final BankCard bankCard
  ) {
    return BankCardVO
        .builder()
        .cardNumber(bankCard.getCardNumber())
        .person(personVO)
        .build()
    ;
  }

  private Person map(@NotNull final PersonVO personVO) {
    return Person
        .builder()
        .firstname(personVO.getFirstname())
        .lastname(personVO.getLastname())
        .bankCards(
            personVO
                .getBankCards()
                .stream()
                .map(this::map)
                .collect(Collectors.toUnmodifiableList())
        )
        .build()
    ;
  }

  private BankCard map(@NotNull final BankCardVO bankCardVO) {
    return BankCard
        .builder()
        .cardNumber(bankCardVO.getCardNumber())
        .build()
    ;
  }
}
