package de.muenchen.oss.foerdermittel.backend.hauptabschnitt;

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
public class HauptabschnittService {

    private final HauptabschnittRepository hauptabschnittRepository;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Page<Hauptabschnitt> getHauptabschnitte(final Pageable pageable) {
        log.info("Get Hauptabschnitte with Pageable {}", pageable);
        return hauptabschnittRepository.findAll(pageable);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    @Transactional(readOnly = true)
    public HauptabschnittFormContext getHauptabschnittFormContext() {
        log.info("Get Hauptabschnitt form context");
        return new HauptabschnittFormContext(hauptabschnittRepository.findAllHas());
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Hauptabschnitt createHauptabschnitt(final Hauptabschnitt hauptabschnitt) {
        log.debug("Create Hauptabschnitt {}", hauptabschnitt);
        return hauptabschnittRepository.insert(hauptabschnitt);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Hauptabschnitt updateHauptabschnitt(final Hauptabschnitt hauptabschnitt, final String ha) {
        final Hauptabschnitt foundHauptabschnitt = ServiceUtils.getEntityOrThrowNotFoundException(ha, hauptabschnittRepository);
        foundHauptabschnitt.setBezeichnung(hauptabschnitt.getBezeichnung());
        log.debug("Update Hauptabschnitt {}", foundHauptabschnitt);
        return hauptabschnittRepository.update(foundHauptabschnitt);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteHauptabschnitt(final String ha) {
        log.debug("Delete Hauptabschnitt with ID {}", ha);
        ServiceUtils.getEntityOrThrowNotFoundException(ha, hauptabschnittRepository);
        hauptabschnittRepository.deleteById(ha);
    }
}
