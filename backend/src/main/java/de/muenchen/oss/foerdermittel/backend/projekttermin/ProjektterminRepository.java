package de.muenchen.oss.foerdermittel.backend.projekttermin;

import de.muenchen.oss.foerdermittel.backend.common.InsertAndUpdateRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjektterminRepository extends PagingAndSortingRepository<Projekttermin, Long>, ListCrudRepository<Projekttermin, Long>,
        InsertAndUpdateRepository<Projekttermin> {

    @Query("SELECT pt.id FROM Projekttermin pt")
    List<Long> findAllProjekttermine();

}
