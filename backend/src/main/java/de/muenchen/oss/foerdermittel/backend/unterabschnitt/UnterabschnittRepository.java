package de.muenchen.oss.foerdermittel.backend.unterabschnitt;

import de.muenchen.oss.foerdermittel.backend.common.InsertAndUpdateRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnterabschnittRepository extends PagingAndSortingRepository<Unterabschnitt, String>, ListCrudRepository<Unterabschnitt, String>,
        InsertAndUpdateRepository<Unterabschnitt> {

    @Query("SELECT u.ua FROM Unterabschnitt u")
    List<String> findAllUas();

}
