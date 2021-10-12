package cz.bera.codium.repository.entity.nosql;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("Person")
public class PersonNO implements Serializable {

  private Long id;

  private String firstname;

  private String lastname;

  private List<BankCardNO> bankCards;
}
