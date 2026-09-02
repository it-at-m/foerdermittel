package de.muenchen.oss.foerdermittel.backend.benutzerhinweis;

import de.muenchen.oss.foerdermittel.backend.common.InsertAndUpdateRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BenutzerhinweisRepository extends ListCrudRepository<Benutzerhinweis, String>,
        InsertAndUpdateRepository<Benutzerhinweis> {
}
