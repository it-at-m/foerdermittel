package de.muenchen.oss.foerdermittel.backend.archiv;

import de.muenchen.oss.foerdermittel.backend.common.InsertAndUpdateRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ArchivRepository extends PagingAndSortingRepository<Archiv, Long>, CrudRepository<Archiv, Long>,
        InsertAndUpdateRepository<Archiv> {

    @Query("SELECT a.id FROM Archiv a JOIN a.projekt p")
    List<Long> findAllWithProjekt();
}
