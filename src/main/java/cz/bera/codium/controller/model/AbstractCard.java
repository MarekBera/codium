package cz.bera.codium.controller.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "cardType",
    visible = true
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = MasterCard.class, name = "MASTERCARD"),
    @JsonSubTypes.Type(value = VisaCard.class, name = "VISA")
})
public abstract class AbstractCard {

  @NotBlank(message = "{error.bankcard.cardNumber.missing}")
  @Min(value = 0100000000000000L, message = "{error.bankcard.cardNumber.size}")
  @Max(value = 9999999999999999L, message = "{error.bankcard.cardNumber.size}")
  private Long cardNumber;

  private EnumCardTypes cardType;
}
