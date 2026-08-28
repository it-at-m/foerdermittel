package de.muenchen.oss.foerdermittel.backend.projekt;

import de.muenchen.oss.foerdermittel.backend.common.InsertAndUpdateRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjektRepository extends
        JpaRepository<Projekt, String>,
        JpaSpecificationExecutor<Projekt>,
        InsertAndUpdateRepository<Projekt> {

    @Query("SELECT p.projnr FROM Projekt p")
    List<String> findAllProjekte();
}