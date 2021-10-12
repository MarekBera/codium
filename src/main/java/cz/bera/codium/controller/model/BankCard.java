package cz.bera.codium.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankCard {

  @NotBlank(message = "{error.bankcard.cardNumber.missing}")
  @Min(value = 0100000000000000L, message = "{error.bankcard.cardNumber.size}")
  @Max(value = 9999999999999999L, message = "{error.bankcard.cardNumber.size}")
  private Long cardNumber;

}
