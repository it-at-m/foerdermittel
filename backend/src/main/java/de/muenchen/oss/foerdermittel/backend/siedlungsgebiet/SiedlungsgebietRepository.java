package de.muenchen.oss.foerdermittel.backend.siedlungsgebiet;

import de.muenchen.oss.foerdermittel.backend.common.InsertAndUpdateRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiedlungsgebietRepository extends PagingAndSortingRepository<Siedlungsgebiet, BigDecimal>, CrudRepository<Siedlungsgebiet, BigDecimal>,
        InsertAndUpdateRepository<Siedlungsgebiet> {

    @Query("SELECT b.siedlungsgebiet FROM Siedlungsgebiet b")
    List<BigDecimal> findAllSiedlungsgebiete();

}
