package de.muenchen.oss.foerdermittel.backend.antrag;

import de.muenchen.oss.foerdermittel.backend.common.InsertAndUpdateRepository;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AntragRepository extends PagingAndSortingRepository<Antrag, Long>, ListCrudRepository<Antrag, Long>,
        InsertAndUpdateRepository<Antrag> {

    /// `id` is null on create, then no Antrag is excluded.
    @Query("SELECT COUNT(a) > 0 FROM Antrag a WHERE a.projekt.projnr = :projnr AND a.antragstyp = :antragstyp AND (:id IS NULL OR a.id <> :id)")
    boolean existsOtherAntrag(@Param("projnr") String projnr, @Param("antragstyp") Antragstyp antragstyp, @Param("id") Long id);

    @Query("SELECT a.projekt.projnr AS projnr, a.antragstyp AS antragstyp, a.id AS id FROM Antrag a WHERE a.antragstyp IN :antragstypen")
    List<UniqueAntrag> findUniqueAntraege(@Param("antragstypen") Collection<Antragstyp> antragstypen);

}
