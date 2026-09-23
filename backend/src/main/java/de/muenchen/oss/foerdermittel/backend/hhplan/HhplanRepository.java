package de.muenchen.oss.foerdermittel.backend.hhplan;

import de.muenchen.oss.foerdermittel.backend.common.InsertAndUpdateRepository;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.Foerderbereich;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface HhplanRepository extends PagingAndSortingRepository<Hhplan, BigDecimal>, ListCrudRepository<Hhplan, BigDecimal>,
        InsertAndUpdateRepository<Hhplan> {

    @Query("SELECT h.hhjJahr FROM Hhplan h ORDER BY h.hhjJahr")
    List<BigDecimal> findAllHhplan();

}
