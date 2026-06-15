package de.muenchen.oss.foerdermittel.backend.bauprogramm;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BauprogrammRepository extends PagingAndSortingRepository<Bauprogramm, Integer>, CrudRepository<Bauprogramm, Integer> {
}
