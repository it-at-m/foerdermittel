package de.muenchen.oss.foerdermittel.backend.stichwortbereich;

import de.muenchen.oss.foerdermittel.backend.common.InsertAndUpdateRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StichwortbereichRepository extends PagingAndSortingRepository<Stichwortbereich, String>, CrudRepository<Stichwortbereich, String>,
        InsertAndUpdateRepository<Stichwortbereich> {

    @Query("SELECT s.bereich FROM Stichwortbereich s")
    List<String> findAllStichwortbereiche();

}
