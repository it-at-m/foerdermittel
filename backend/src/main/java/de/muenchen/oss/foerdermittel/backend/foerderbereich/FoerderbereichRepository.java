package de.muenchen.oss.foerdermittel.backend.foerderbereich;

import de.muenchen.oss.foerdermittel.backend.common.InsertAndUpdateRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoerderbereichRepository extends PagingAndSortingRepository<Foerderbereich, BigDecimal>, CrudRepository<Foerderbereich, BigDecimal>,
        InsertAndUpdateRepository<Foerderbereich> {

    @Query("SELECT f.fb FROM Foerderbereich f ORDER BY f.fb")
    List<BigDecimal> findAllFb();

}
