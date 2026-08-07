package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StadtbezirkslisteRepository
        extends CrudRepository<Stadtbezirksliste, StadtbezirkslistePrimaryKey> {

    boolean existsByListenName_Kurzbez(String kurzbez);

}
