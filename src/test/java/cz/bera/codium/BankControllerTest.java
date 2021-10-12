package cz.bera.codium;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.bera.codium.controller.model.BankCard;
import cz.bera.codium.controller.model.Person;
import cz.bera.codium.repository.BankCardRepository;
import cz.bera.codium.repository.PersonRepository;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ActiveProfiles("test")
public class BankControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private PersonRepository personRepository;

  @Autowired
  private BankCardRepository bankCardRepository;

  @Test
  @Order(1)
  public void savePerson_status201() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders
                .post("/api/bank/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(createTestPerson()))
        )
        .andExpect(
            MockMvcResultMatchers
                .status()
                .isCreated()
        )
        .andExpect(
            MockMvcResultMatchers
                .header()
                .exists("Location")
        )
    ;
  }

  private Person createTestPerson() {
    return Person
        .builder()
        .firstname("Jon")
        .lastname("Snow")
        .bankCards(createBankCards())
        .build()
    ;
  }

  private List<BankCard> createBankCards() {
    final List<BankCard> bankCards = new ArrayList<>();
    bankCards.add(
        BankCard
            .builder()
            .cardNumber(1234123412341234L)
            .build()
    );
    bankCards.add(
        BankCard
            .builder()
            .cardNumber(1234123443214321L)
            .build()
    );

    return bankCards;
  }

  static byte[] toJson(Object object) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    return mapper.writeValueAsBytes(object);
  }
}
