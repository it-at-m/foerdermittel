package de.muenchen.oss.foerdermittel.backend.bauprogramme;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BauprogrammeRepository extends PagingAndSortingRepository<Bauprogramme, Integer>, CrudRepository<Bauprogramme, Integer> {
    // Hier können zusätzliche Abfragen definiert werden, falls nötig
}
