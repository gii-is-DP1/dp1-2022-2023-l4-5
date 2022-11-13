package org.springframework.samples.nt4h.card.product;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductInGameRepository extends CrudRepository<ProductInGame, Integer> {
    Optional<ProductInGame> findById(Integer id);

    List<ProductInGame> findAll();

}
