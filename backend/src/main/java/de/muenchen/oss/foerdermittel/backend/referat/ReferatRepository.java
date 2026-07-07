package de.muenchen.oss.foerdermittel.backend.referat;

import de.muenchen.oss.foerdermittel.backend.common.InsertAndUpdateRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferatRepository extends PagingAndSortingRepository<Referat, BigDecimal>, CrudRepository<Referat, BigDecimal>,
        InsertAndUpdateRepository<Referat> {

    @Query("SELECT r.refnr FROM Referat r ORDER BY r.refnr")
    List<BigDecimal> findAllRefNr();

}
