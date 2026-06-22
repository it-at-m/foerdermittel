package de.muenchen.oss.foerdermittel.backend.bauprogramm;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface BauprogrammRepository extends PagingAndSortingRepository<Bauprogramm, Integer>, CrudRepository<Bauprogramm, Integer> {

    @Query("SELECT b.bauprogramm FROM Bauprogramm b")
    List<BigDecimal> findAllBauprogramme();

}
