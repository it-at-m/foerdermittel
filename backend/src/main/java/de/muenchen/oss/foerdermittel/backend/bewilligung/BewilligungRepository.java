package de.muenchen.oss.foerdermittel.backend.bewilligung;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BewilligungRepository extends ListCrudRepository<Bewilligung, Long> {

    boolean existsByAntragId(Long antragId);

}
