package de.muenchen.oss.foerdermittel.backend.bauleitung;

import de.muenchen.oss.foerdermittel.backend.common.InsertAndUpdateRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BauleitungRepository extends PagingAndSortingRepository<Bauleitung, String>, CrudRepository<Bauleitung, String>,
        InsertAndUpdateRepository<Bauleitung> {

    @Query("SELECT b.bauleitung FROM Bauleitung b")
    List<String> findAllBauleitungen();

}
