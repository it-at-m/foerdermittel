package de.muenchen.oss.foerdermittel.backend.termin;

import de.muenchen.oss.foerdermittel.backend.common.InsertAndUpdateRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TerminRepository extends PagingAndSortingRepository<Termin, Long>, ListCrudRepository<Termin, Long>,
        InsertAndUpdateRepository<Termin> {

    @Query("SELECT pt.id FROM Termin pt")
    List<Long> findAllTermine();

}
