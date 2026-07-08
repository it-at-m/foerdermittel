package de.muenchen.oss.foerdermittel.backend.publikation;

import de.muenchen.oss.foerdermittel.backend.common.InsertAndUpdateRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublikationRepository extends PagingAndSortingRepository<Publikation, String>, CrudRepository<Publikation, String>,
        InsertAndUpdateRepository<Publikation> {

    @Query("SELECT p.kurzform FROM Publikation p")
    List<String> findAllKurzformen();

}

