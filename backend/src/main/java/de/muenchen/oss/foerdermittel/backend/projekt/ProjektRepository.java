package de.muenchen.oss.foerdermittel.backend.projekt;

import de.muenchen.oss.foerdermittel.backend.common.InsertAndUpdateRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjektRepository extends PagingAndSortingRepository<Projekt,String>, CrudRepository<Projekt,String>,
        InsertAndUpdateRepository<Projekt> {

    @Query("SELECT p.projnr FROM Projekt p")
    List<String> findAllProjekte();


}
