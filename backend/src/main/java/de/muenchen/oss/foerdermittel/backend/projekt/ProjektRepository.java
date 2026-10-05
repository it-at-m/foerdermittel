package de.muenchen.oss.foerdermittel.backend.projekt;

import de.muenchen.oss.foerdermittel.backend.common.InsertAndUpdateRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjektRepository extends PagingAndSortingRepository<Projekt, String>, ListCrudRepository<Projekt, String>,
        InsertAndUpdateRepository<Projekt> {

}
