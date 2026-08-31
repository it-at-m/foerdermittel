package de.muenchen.oss.foerdermittel.backend.unterabschnitt;

import de.muenchen.oss.foerdermittel.backend.hauptabschnitt.Hauptabschnitt;
import de.muenchen.oss.foerdermittel.backend.hauptabschnitt.HauptabschnittService;
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
public class UnterabschnittService {

    private final HauptabschnittService hauptabschnittService;
    private final UnterabschnittRepository unterabschnittRepository;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Page<Unterabschnitt> getUnterabschnitte(final Pageable pageable) {
        log.info("Get Unterabschnitte with Pageable {}", pageable);
        return unterabschnittRepository.findAll(pageable);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    @Transactional(readOnly = true)
    public UnterabschnittFormContext getUnterabschnittFormContext() {
        log.info("Get Unterabschnitt form context");

        return new UnterabschnittFormContext(
                unterabschnittRepository.findAllUas(),
                hauptabschnittService.getHauptabschnittFormContextDTOs());
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Unterabschnitt createUnterabschnitt(final Unterabschnitt unterabschnitt, final String ha) {
        final Hauptabschnitt hauptabschnitt = hauptabschnittService.getHauptabschnitt(ha);
        unterabschnitt.setHasHa(hauptabschnitt);
        log.debug("Create Unterabschnitt {}", unterabschnitt);
        return unterabschnittRepository.insert(unterabschnitt);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Unterabschnitt updateUnterabschnitt(final Unterabschnitt unterabschnitt, final String ua) {
        final Unterabschnitt foundUnterabschnitt = ServiceUtils.getEntityOrThrowNotFoundException(ua, unterabschnittRepository);

        foundUnterabschnitt.setBezeichnung(unterabschnitt.getBezeichnung());

        log.debug("Update Unterabschnitt {}", foundUnterabschnitt);
        return unterabschnittRepository.update(foundUnterabschnitt);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteUnterabschnitt(final String ua) {
        log.debug("Delete Unterabschnitt with ID {}", ua);
        ServiceUtils.getEntityOrThrowNotFoundException(ua, unterabschnittRepository);
        unterabschnittRepository.deleteById(ua);
    }
}
