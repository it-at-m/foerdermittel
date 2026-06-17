package de.muenchen.oss.foerdermittel.backend.siedlungsgebiet;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiedlungsgebietRepository extends PagingAndSortingRepository<Siedlungsgebiet, Integer>, CrudRepository<Siedlungsgebiet, Integer> {
}
