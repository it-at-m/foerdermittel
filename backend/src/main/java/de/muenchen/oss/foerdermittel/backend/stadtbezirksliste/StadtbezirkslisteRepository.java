package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste;

import de.muenchen.oss.foerdermittel.backend.common.InsertAndUpdateRepository;
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


    void deleteByListenName_KurzbezAndStadtbezirk_Stadtbezirk(
            String kurzbez,
            BigDecimal stadtbezirk);

}