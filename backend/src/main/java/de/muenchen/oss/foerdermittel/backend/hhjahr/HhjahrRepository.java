
package de.muenchen.oss.foerdermittel.backend.hhjahr;

import de.muenchen.oss.foerdermittel.backend.common.InsertAndUpdateRepository;
import java.math.BigDecimal;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HhjahrRepository extends
        PagingAndSortingRepository<Hhjahr, BigDecimal>,
        ListCrudRepository<Hhjahr, BigDecimal>,
        InsertAndUpdateRepository<Hhjahr> {
}
