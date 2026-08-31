package de.muenchen.oss.foerdermittel.backend.kurzbezeichnung;

import de.muenchen.oss.foerdermittel.backend.common.InsertAndUpdateRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KurzbezeichnungRepository extends PagingAndSortingRepository<Kurzbezeichnung, String>, ListCrudRepository<Kurzbezeichnung, String>,
        InsertAndUpdateRepository<Kurzbezeichnung> {

    @Query("SELECT k.kurzbez FROM Kurzbezeichnung k")
    List<String> findAllKurzbezeichnungen();

}
