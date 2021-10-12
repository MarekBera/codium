package cz.bera.codium.repository;

import cz.bera.codium.repository.entity.sql.PersonVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<PersonVO, Long> {

  @Query(value = "call find_person_with_cards_by_id(:input);", nativeQuery = true)
  Optional<PersonVO> findPersonWithCardById(@Param("input") Long id);

  @Query(value = "call find_all_person_with_cards;", nativeQuery = true)
  List<PersonVO> findAllPersonWithCards();
}
