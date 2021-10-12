package cz.bera.codium.repository;

import cz.bera.codium.repository.entity.sql.BankCardVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankCardRepository extends JpaRepository<BankCardVO, Long> {
}
