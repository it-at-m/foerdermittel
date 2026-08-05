package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste;

import de.muenchen.oss.foerdermittel.backend.common.InsertAndUpdateRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListennameRepository extends PagingAndSortingRepository<Listenname, String>, CrudRepository<Listenname, String>,
        InsertAndUpdateRepository<Listenname> {

    @Query("SELECT l.kurzbez FROM Listenname l")
    List<String> findAllKurzBezn();
}
