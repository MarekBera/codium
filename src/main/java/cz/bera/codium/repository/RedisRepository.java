package cz.bera.codium.repository;

import cz.bera.codium.repository.entity.nosql.PersonNO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisRepository extends CrudRepository<PersonNO, Long> {
}
