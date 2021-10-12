package cz.bera.codium.repository.entity.nosql;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankCardNO implements Serializable {

  private Long cardNumber;
}
