package de.muenchen.oss.foerdermittel.backend.hhplan;

import de.muenchen.oss.foerdermittel.backend.common.InsertAndUpdateRepository;
import de.muenchen.oss.foerdermittel.backend.hhplan.dao.BasicHhplanDAO;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HhplanRepository extends PagingAndSortingRepository<Hhplan, HhplanPrimaryKey>, ListCrudRepository<Hhplan, HhplanPrimaryKey>,
        InsertAndUpdateRepository<Hhplan> {

    @Query("SELECT h.id.hhjJahr FROM Hhplan h ORDER BY h.id.hhjJahr")
    List<BigDecimal> findAllHhplan();

    @Query("SELECT Distinct h.id.hhjJahr, h.fipo FROM Hhplan h ORDER BY h.fipo")
    List<BasicHhplanDAO> findReportHhplan();

}
