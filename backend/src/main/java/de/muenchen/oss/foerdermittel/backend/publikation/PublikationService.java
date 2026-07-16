package de.muenchen.oss.foerdermittel.backend.publikation;

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
public class PublikationService {

    private final PublikationRepository publikationRepository;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Page<Publikation> getPublikationen(final Pageable pageable) {
        log.info("Get Publikationen with Pageable {}", pageable);
        return publikationRepository.findAll(pageable);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    @Transactional(readOnly = true)
    public PublikationFormContext getPublikationFormContext() {
        log.info("Get Publikation form context");
        return new PublikationFormContext(publikationRepository.findAllKurzformen());
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Publikation createPublikation(final Publikation publikation) {
        log.debug("Create Publikation {}", publikation);
        return publikationRepository.insert(publikation);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Publikation updatePublikation(final Publikation publikation, final String kurzform) {
        final Publikation foundPublikation = ServiceUtils.getEntityOrThrowNotFoundException(kurzform, publikationRepository);
        foundPublikation.setBezeichnung(publikation.getBezeichnung());
        log.debug("Update Publikation {}", foundPublikation);
        return publikationRepository.update(foundPublikation);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deletePublikation(final String kurzform) {
        log.debug("Delete Publikation with ID {}", kurzform);
        ServiceUtils.getEntityOrThrowNotFoundException(kurzform, publikationRepository);
        publikationRepository.deleteById(kurzform);
    }
}
