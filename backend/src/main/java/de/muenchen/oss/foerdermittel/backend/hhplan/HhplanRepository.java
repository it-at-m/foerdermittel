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
public interface HhplanRepository extends
        PagingAndSortingRepository<Hhplan, HhplanPrimaryKey>,
        ListCrudRepository<Hhplan, HhplanPrimaryKey>,
        InsertAndUpdateRepository<Hhplan> {

    boolean existsByHhjJahr_Hhjahr(BigDecimal hhjahr);

    @Query("SELECT h.id.hhjJahr FROM Hhplan h ORDER BY h.id.hhjJahr")
    List<BigDecimal> findAllHhplan();

    @Query("SELECT DISTINCT h.id.hhjJahr, h.id.proProjnr, h.fipo FROM Hhplan h ORDER BY h.fipo")
    List<BasicHhplanDAO> findReportHhplan();

    @Query(
        "SELECT DISTINCT h FROM Hhplan h JOIN FETCH h.proProjnr p LEFT JOIN FETCH p.fobFb LEFT JOIN FETCH p.bezStadtbezirk b LEFT JOIN FETCH b.stadtbezirkslisten s LEFT JOIN FETCH s.listenName WHERE h.id.hhjJahr IN :hhjahre"
    )
    List<Hhplan> findAllByHhjJahrIn(List<BigDecimal> hhjahre);
}
