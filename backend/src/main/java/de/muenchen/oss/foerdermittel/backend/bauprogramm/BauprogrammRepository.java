package de.muenchen.oss.foerdermittel.backend.bauprogramm;

import de.muenchen.oss.foerdermittel.backend.common.InsertAndUpdateRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BauprogrammRepository extends PagingAndSortingRepository<Bauprogramm, BigDecimal>, CrudRepository<Bauprogramm, BigDecimal>,
        InsertAndUpdateRepository<Bauprogramm> {

    @Query("SELECT b.bauprogramm FROM Bauprogramm b")
    List<BigDecimal> findAllBauprogramme();

}
