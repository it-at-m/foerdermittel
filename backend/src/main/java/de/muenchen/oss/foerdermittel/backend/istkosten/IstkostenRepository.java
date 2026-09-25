package de.muenchen.oss.foerdermittel.backend.istkosten;

import de.muenchen.oss.foerdermittel.backend.common.InsertAndUpdateRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IstkostenRepository extends ListCrudRepository<Istkosten, IstkostenPrimaryKey>, PagingAndSortingRepository<Istkosten, IstkostenPrimaryKey>,
        InsertAndUpdateRepository<Istkosten> {

    @Query("SELECT i.id FROM Istkosten i")
    List<IstkostenPrimaryKey> findAllWithProjekt();

}
