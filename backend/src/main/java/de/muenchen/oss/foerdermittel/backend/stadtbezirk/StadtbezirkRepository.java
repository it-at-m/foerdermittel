package de.muenchen.oss.foerdermittel.backend.stadtbezirk;

import de.muenchen.oss.foerdermittel.backend.common.InsertAndUpdateRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StadtbezirkRepository extends PagingAndSortingRepository<Stadtbezirk, BigDecimal>, CrudRepository<Stadtbezirk, BigDecimal>,
        InsertAndUpdateRepository<Stadtbezirk> {

    @Query("SELECT s.stadtbezirk FROM Stadtbezirk s")
    List<BigDecimal> findAllStadtbezirke();

}
