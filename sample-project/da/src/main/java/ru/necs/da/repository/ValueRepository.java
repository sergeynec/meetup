package ru.necs.da.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import ru.necs.da.model.ValueEntity;

public interface ValueRepository extends CrudRepository<ValueEntity, Long> {

    Optional<ValueEntity> findByHash(String hash);
}
