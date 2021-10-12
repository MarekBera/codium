package cz.bera.codium.repository.entity.sql;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString
@Entity
@Table(name = "bank_card")
public class BankCardVO extends AbstractVO {

  @Column(name = "card_number", nullable = false, unique = true)
  private long cardNumber;

  @ToString.Exclude
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "person_id")
  private PersonVO person;
}
