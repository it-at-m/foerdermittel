package de.muenchen.oss.foerdermittel.backend.bauprogramm;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BauprogrammRepository extends PagingAndSortingRepository<Bauprogramm, Integer>, CrudRepository<Bauprogramm, Integer> {

    @Query("SELECT b.bauprogramm FROM Bauprogramm b")
    List<BigDecimal> findAllBauprogramme();

}
