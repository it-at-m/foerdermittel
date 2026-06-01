package de.muenchen.oss.foerdermittel.backend.bauleitung;

import de.muenchen.oss.foerdermittel.backend.bauleitung.Bauleitung;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BauleitungRepository extends PagingAndSortingRepository<Bauleitung, String>, CrudRepository<Bauleitung, String> {
    // Hier können zusätzliche Abfragen definiert werden, falls nötig
}
