package de.muenchen.oss.foerdermittel.backend.istkosten;

import de.muenchen.oss.foerdermittel.backend.common.InsertAndUpdateRepository;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektFormContextDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.ListCrudRepository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface IstkostenRepository extends ListCrudRepository<Istkosten, IstkostenPrimaryKey>, PagingAndSortingRepository<Istkosten, IstkostenPrimaryKey>,
        InsertAndUpdateRepository<Istkosten> {

    @Query("SELECT i.id FROM Istkosten i")
    List<IstkostenPrimaryKey> findAllWithProjekt();

}
