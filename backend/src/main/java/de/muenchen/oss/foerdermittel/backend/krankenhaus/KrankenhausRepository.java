package de.muenchen.oss.foerdermittel.backend.krankenhaus;

import de.muenchen.oss.foerdermittel.backend.common.InsertAndUpdateRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KrankenhausRepository extends PagingAndSortingRepository<Krankenhaus, String>, CrudRepository<Krankenhaus, String>,
        InsertAndUpdateRepository<Krankenhaus> {

    @Query("SELECT k.krhname FROM Krankenhaus k")
    List<String> findAllKrhNamen();

}
