package org.springframework.samples.nt4h.card.product;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ProductInGameRepository extends PagingAndSortingRepository<ProductInGame, Integer> {
    Optional<ProductInGame> findById(Integer id);

    List<ProductInGame> findAll();

    List<ProductInGame> findAllByStateProduct(StateProduct stateProduct, Pageable pageable);

}
