package de.muenchen.oss.foerdermittel.backend.projekt;

import de.muenchen.oss.foerdermittel.backend.common.InsertAndUpdateRepository;
import de.muenchen.oss.foerdermittel.backend.projekt.dao.AuswertungProjektDAO;
import de.muenchen.oss.foerdermittel.backend.projekt.dao.BasicProjektDAO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjektRepository extends
        PagingAndSortingRepository<Projekt, String>,
        ListCrudRepository<Projekt, String>,
        JpaSpecificationExecutor<Projekt>,
        InsertAndUpdateRepository<Projekt> {

    @Query("SELECT p.jahr, p.pname, p.pstrasse, p.krisofp FROM Projekt p")
    List<AuswertungProjektDAO> findAllAuswertungProjekte();

    @Query("SELECT p.projnr, p.pname, p.pstrasse FROM Projekt p")
    List<BasicProjektDAO> findAllAsBasic();

}
