package de.muenchen.oss.foerdermittel.backend.foerderbereich;

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
public class FoerderbereichService {

    private final FoerderbereichRepository foerderbereichRepository;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Foerderbereich getFoerderbereich(final BigDecimal foerderbereichId) {
        log.info("Get Foerderbereich with ID {}", foerderbereichId);
        return ServiceUtils.getEntityOrThrowNotFoundException(foerderbereichId, foerderbereichRepository);
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Page<Foerderbereich> getAllFoerderbereiche(final Pageable pageable) {
        log.info("Get all Foerderbereiche with Pageable {}", pageable);
        return foerderbereichRepository.findAll(pageable);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    @Transactional(readOnly = true)
    public FoerderbereichFormContext getFoerderbereichFormContext() {
        log.info("Get Foerderbereich form context");
        return new FoerderbereichFormContext(foerderbereichRepository.findAllFb());
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Foerderbereich createFoerderbereich(final Foerderbereich foerderbereich) {
        log.debug("Create Foerderbereich {}", foerderbereich);
        return foerderbereichRepository.insert(foerderbereich);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Foerderbereich updateFoerderbereich(final Foerderbereich foerderbereich, final BigDecimal foerderbereichId) {
        final Foerderbereich foundFoerderbereich = ServiceUtils.getEntityOrThrowNotFoundException(foerderbereichId, foerderbereichRepository);
        foundFoerderbereich.setBezeichnung(foerderbereich.getBezeichnung());
        foundFoerderbereich.setFinanzausgleich(foerderbereich.getFinanzausgleich());
        foundFoerderbereich.setJahresstatistik(foerderbereich.getJahresstatistik());
        foundFoerderbereich.setKindergarten(foerderbereich.getKindergarten());
        foundFoerderbereich.setNichtRelevant(foerderbereich.getNichtRelevant());
        log.debug("Update Foerderbereich {}", foundFoerderbereich);
        return foerderbereichRepository.update(foundFoerderbereich);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteFoerderbereich(final BigDecimal foerderbereichId) {
        log.debug("Delete Foerderbereich with ID {}", foerderbereichId);
        ServiceUtils.getEntityOrThrowNotFoundException(foerderbereichId, foerderbereichRepository);
        foerderbereichRepository.deleteById(foerderbereichId);
    }

}
