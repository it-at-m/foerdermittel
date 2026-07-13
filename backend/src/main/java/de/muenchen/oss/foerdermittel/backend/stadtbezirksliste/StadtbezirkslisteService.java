package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste;

import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.Stadtbezirksliste;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.StadtbezirkslisteFormContext;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.StadtbezirkslisteRepository;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.util.ServiceUtils;
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
public class StadtbezirkslisteService {

    private final StadtbezirkslisteRepository stadtbezirkslisteRepository;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Stadtbezirksliste getStadtbezirksliste(final String stadtbezirkslisteId) {
        log.info("Get Stadtbezirksliste with ID {}", stadtbezirkslisteId);
        return ServiceUtils.getEntityOrThrowNotFoundException(stadtbezirkslisteId, stadtbezirkslisteRepository);
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Page<Stadtbezirksliste> getAllStadtbezirkslisten(final Pageable pageable) {
        log.info("Get all Stadtbezirkslisten with Pageable {}", pageable);
        return stadtbezirkslisteRepository.findAll(pageable);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    @Transactional(readOnly = true)
    public StadtbezirkslisteFormContext getStadtbezirkslisteFormContext() {
        log.info("Get Stadtbezirksliste form context");
        return new StadtbezirkslisteFormContext(stadtbezirkslisteRepository.findAllStadtbezirkslisten());
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Stadtbezirksliste createStadtbezirksliste(final Stadtbezirksliste stadtbezirksliste) {
        log.debug("Create Stadtbezirksliste {}", stadtbezirksliste);
        return stadtbezirkslisteRepository.insert(stadtbezirksliste);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Stadtbezirksliste updateStadtbezirksliste(final Stadtbezirksliste stadtbezirksliste, final String stadtbezirkslisteId) {
        final Stadtbezirksliste foundStadtbezirksliste = ServiceUtils.getEntityOrThrowNotFoundException(stadtbezirkslisteId, stadtbezirkslisteRepository);
        foundStadtbezirksliste.setBezeichnung(stadtbezirksliste.getBezeichnung());
        log.debug("Update Stadtbezirksliste {}", foundStadtbezirksliste);
        return stadtbezirkslisteRepository.update(foundStadtbezirksliste);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteStadtbezirksliste(final String stadtbezirkslisteId) {
        log.debug("Delete Stadtbezirksliste with ID {}", stadtbezirkslisteId);
        ServiceUtils.getEntityOrThrowNotFoundException(stadtbezirkslisteId, stadtbezirkslisteRepository);
        stadtbezirkslisteRepository.deleteById(stadtbezirkslisteId);
    }
}
