package de.muenchen.oss.foerdermittel.backend.siedlungsgebiet;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiedlungsgebietRepository extends PagingAndSortingRepository<Siedlungsgebiet, Integer>, CrudRepository<Siedlungsgebiet, Integer> {

    @Query("SELECT b.siedlungsgebiet FROM Siedlungsgebiet b")
    List<BigDecimal> findAllSiedlungsgebiete();

}
