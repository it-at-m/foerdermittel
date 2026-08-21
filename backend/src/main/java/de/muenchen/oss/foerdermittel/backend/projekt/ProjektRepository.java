package de.muenchen.oss.foerdermittel.backend.projekt;

import de.muenchen.oss.foerdermittel.backend.common.InsertAndUpdateRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjektRepository extends PagingAndSortingRepository<Projekt, String>, CrudRepository<Projekt, String>,
        InsertAndUpdateRepository<Projekt> {

    @Query("SELECT p.projnr FROM Projekt p")
    List<String> findAllProjekte();

}
