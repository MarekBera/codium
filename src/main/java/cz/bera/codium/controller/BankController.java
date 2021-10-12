package cz.bera.codium.controller;

import cz.bera.codium.controller.model.Person;
import cz.bera.codium.controller.model.PersonCardType;
import cz.bera.codium.service.BankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/bank", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class BankController {

  @Autowired
  private BankService bankService;

  @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> savePerson(@Valid @RequestBody Person person) throws URISyntaxException {
    log.debug("POST /save: {}", person);

    Long personId = bankService.createPerson(person);

    return ResponseEntity.created(new URI("/api/bank/" + personId)).build();
  }

  @PostMapping(value = "/save/cardType", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
  public void savePersonCardType(@Valid @RequestBody PersonCardType person) throws URISyntaxException {
    log.debug("POST /save/cardType: {}", person);
  }

  @GetMapping(value = "/list")
  @ResponseStatus(HttpStatus.OK)
  public List<Person> getAll() {
    log.debug("GET /list");

    return bankService.findAll();
  }

  @GetMapping(value = "/find/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Person findPerson(@PathVariable Long id) {
    log.debug("GET /find/{}", id);

    return bankService.getPersonById(id);
  }
}
