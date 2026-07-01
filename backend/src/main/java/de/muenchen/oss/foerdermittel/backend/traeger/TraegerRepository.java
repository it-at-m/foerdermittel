package de.muenchen.oss.foerdermittel.backend.traeger;

import java.math.BigDecimal;
import java.util.List;
import de.muenchen.oss.foerdermittel.backend.common.InsertAndUpdateRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TraegerRepository extends PagingAndSortingRepository<Traeger, BigDecimal>, CrudRepository<Traeger, BigDecimal>,
        InsertAndUpdateRepository<Traeger> {

    @Query("SELECT t.kurzform FROM Traeger t")
    List<BigDecimal> findAllTraeger();

}
