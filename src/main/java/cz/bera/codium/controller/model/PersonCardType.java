package cz.bera.codium.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonCardType {

  @NotBlank(message = "{error.person.firstname.missing}")
  private String firstname;

  @NotBlank(message = "{error.person.lastname.missing}")
  private String lastname;

  private List<AbstractCard> bankCards;
}
