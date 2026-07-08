package de.muenchen.oss.foerdermittel.backend.benutzerhinweis;

import de.muenchen.oss.foerdermittel.backend.common.InsertAndUpdateRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BenutzerhinweisRepository extends CrudRepository<Benutzerhinweis, String>,
        InsertAndUpdateRepository<Benutzerhinweis> {
}
