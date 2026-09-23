package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StadtbezirkslisteRepository
        extends ListCrudRepository<Stadtbezirksliste, StadtbezirkslistePrimaryKey> {

    boolean existsByListenNameKurzbez(String kurzbez);

}
