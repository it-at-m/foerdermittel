package de.muenchen.oss.foerdermittel.backend.stadtbezirk;

import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.util.ServiceUtils;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class StadtbezirkService {

    private final StadtbezirkRepository stadtbezirkRepository;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Page<Stadtbezirk> getAllStadtbezirke(final Pageable pageable) {
        log.info("Get all Stadtbezirke with Pageable {}", pageable);
        return stadtbezirkRepository.findAll(pageable);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    @Transactional(readOnly = true)
    public StadtbezirkFormContext getStadtbezirkFormContext() {
        log.info("Get Stadtbezirk form context");
        return new StadtbezirkFormContext(stadtbezirkRepository.findAllStadtbezirke());
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Stadtbezirk createStadtbezirk(final Stadtbezirk stadtbezirk) {
        log.debug("Create Stadtbezirk {}", stadtbezirk);
        return stadtbezirkRepository.insert(stadtbezirk);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Stadtbezirk updateStadtbezirk(final Stadtbezirk stadtbezirk, final BigDecimal stadtbezirkId) {
        final Stadtbezirk foundStadtbezirk = ServiceUtils.getEntityOrThrowNotFoundException(
                stadtbezirkId, stadtbezirkRepository);

        foundStadtbezirk.setBezeichnung(stadtbezirk.getBezeichnung());
        log.debug("Update Stadtbezirk {}", foundStadtbezirk);
        return stadtbezirkRepository.update(foundStadtbezirk);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteStadtbezirk(final BigDecimal stadtbezirkId) {
        log.debug("Delete Stadtbezirk with ID {}", stadtbezirkId);
        ServiceUtils.getEntityOrThrowNotFoundException(stadtbezirkId, stadtbezirkRepository);
        stadtbezirkRepository.deleteById(stadtbezirkId);
    }
}
