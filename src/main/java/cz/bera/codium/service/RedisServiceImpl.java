package cz.bera.codium.service;

import cz.bera.codium.controller.model.BankCard;
import cz.bera.codium.controller.model.Person;
import cz.bera.codium.repository.RedisRepository;
import cz.bera.codium.repository.entity.nosql.BankCardNO;
import cz.bera.codium.repository.entity.nosql.PersonNO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RedisServiceImpl extends AbstractService implements RedisService {

  @Autowired
  private RedisRepository redisRepository;

  @Override
  public void storePerson(
      @NotNull Long id,
      @NotNull Person person
  ) {
    final PersonNO personNO = map(person);
    personNO.setId(id);
    PersonNO savedPerson = redisRepository.save(personNO);

    log.debug("Stored person: {}", savedPerson);
  }

  private PersonNO map(@NotNull final Person person) {
    return PersonNO
        .builder()
        .firstname(person.getFirstname())
        .lastname(person.getLastname())
        .bankCards(
            person
                .getBankCards()
                .stream()
                .map(this::map)
                .collect(Collectors.toUnmodifiableList())
        )
        .build()
    ;
  }

  private BankCardNO map(@NotNull final BankCard bankCard) {
    return BankCardNO
        .builder()
        .cardNumber(bankCard.getCardNumber())
        .build()
    ;
  }

}
