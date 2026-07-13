package de.muenchen.oss.foerdermittel.backend.stichwortbereich;

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
public class StichwortbereichService {

    private final StichwortbereichRepository stichwortbereichRepository;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Stichwortbereich getStichwortbereich(final String bereich) {
        log.info("Get Stichwortbereich with ID {}", bereich);
        return ServiceUtils.getEntityOrThrowNotFoundException(bereich, stichwortbereichRepository);
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Page<Stichwortbereich> getAllStichwortbereiche(final Pageable pageable) {
        log.info("Get all Stichwortbereiche with Pageable {}", pageable);
        return stichwortbereichRepository.findAll(pageable);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    @Transactional(readOnly = true)
    public StichwortbereichFormContext getStichwortbereichFormContext() {
        log.info("Get Stichwortbereich form context");
        return new StichwortbereichFormContext(stichwortbereichRepository.findAllStichwortbereiche());
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Stichwortbereich createStichwortbereich(final Stichwortbereich Stichwortbereich) {
        log.debug("Create Stichwortbereich {}", Stichwortbereich);
        return stichwortbereichRepository.insert(Stichwortbereich);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Stichwortbereich updateStichwortbereich(final Stichwortbereich stichwortbereich, final String bereich) {
        final Stichwortbereich foundStichwortbereich = ServiceUtils.getEntityOrThrowNotFoundException(bereich, stichwortbereichRepository);
        foundStichwortbereich.setBezeichnung(stichwortbereich.getBezeichnung());
        log.debug("Update Stichwortbereich {}", foundStichwortbereich);
        return stichwortbereichRepository.update(foundStichwortbereich);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteStichwortbereich(final String bereich) {
        log.debug("Delete Stichwortbereich with ID {}", bereich);
        ServiceUtils.getEntityOrThrowNotFoundException(bereich, stichwortbereichRepository);
        stichwortbereichRepository.deleteById(bereich);
    }
}
