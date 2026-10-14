package de.muenchen.oss.foerdermittel.backend.hauptabschnitt;

import de.muenchen.oss.foerdermittel.backend.common.InsertAndUpdateRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HauptabschnittRepository extends PagingAndSortingRepository<Hauptabschnitt, String>, ListCrudRepository<Hauptabschnitt, String>,
        InsertAndUpdateRepository<Hauptabschnitt> {

    @Query("SELECT h.ha FROM Hauptabschnitt h")
    List<String> findAllHas();

}
