package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste;

import de.muenchen.oss.foerdermittel.backend.common.InsertAndUpdateRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface StadtbezirkslisteRepository extends
        PagingAndSortingRepository<Stadtbezirksliste, StadtbezirkslistePrimaryKey>,
        CrudRepository<Stadtbezirksliste, StadtbezirkslistePrimaryKey>,
        InsertAndUpdateRepository<Stadtbezirksliste> {


    List<Stadtbezirksliste> findByListenName_Kurzbez(String kurzbez);

    @Query("SELECT s.listenName FROM Stadtbezirksliste s")
    List<BigDecimal> findAllStadtbezirkslisten();


    void deleteByListenName_KurzbezAndStadtbezirk_Stadtbezirk(
            String kurzbez,
            BigDecimal stadtbezirk);

    void deleteByListenName_Kurzbez(String kurzbez);

}