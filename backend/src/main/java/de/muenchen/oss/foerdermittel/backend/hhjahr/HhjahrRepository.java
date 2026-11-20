
package de.muenchen.oss.foerdermittel.backend.hhjahr;

import de.muenchen.oss.foerdermittel.backend.common.InsertAndUpdateRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HhjahrRepository extends
        PagingAndSortingRepository<Hhjahr, BigDecimal>,
        ListCrudRepository<Hhjahr, BigDecimal>,
        InsertAndUpdateRepository<Hhjahr> {

    @Query("SELECT hhj.hhjahr FROM Hhjahr hhj ORDER BY hhj.hhjahr")
    List<BigDecimal> findAllHhjahr();

    @Query("SELECT hhj FROM Hhjahr hhj")
    Page<Hhjahr> findAllHhjahre(Pageable pageable);
}
