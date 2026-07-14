package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste;

import de.muenchen.oss.foerdermittel.backend.common.InsertAndUpdateRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StadtbezirkslisteRepository extends PagingAndSortingRepository<Stadtbezirksliste, String>, CrudRepository<Stadtbezirksliste, String>,
        InsertAndUpdateRepository<Stadtbezirksliste> {

    @Query("SELECT s.lnaKurzbez FROM Stadtbezirksliste s")
    List<String> findAllStadtbezirkslisten();

}
