package de.muenchen.oss.foerdermittel.backend.projekt;

import de.muenchen.oss.foerdermittel.backend.hauptabschnitt.Hauptabschnitt;
import de.muenchen.oss.foerdermittel.backend.krankenhaus.Krankenhaus;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.KurzbezeichnungFormContext;
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
public class ProjektService {

    private final ProjektRepository projektRepository;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Page<Projekt> getProjekte(
            ProjektFilter filter,
            Pageable pageable) {

        return projektRepository.findAll(
                ProjektSpecifications.filter(filter),
                pageable
        );
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    @Transactional(readOnly = true)
    public ProjektFormContext getProjektFormContext() {
        log.info("Get Projekt form context");
        return new ProjektFormContext(projektRepository.findAllProjekte());
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Projekt createProjekt(final Projekt projekt) {
        log.debug("Create Projekt {}", projekt);
        return projektRepository.insert(projekt);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Projekt updateProjekt(final Projekt projekt, final String ha) {
        final Projekt foundProjekt = ServiceUtils.getEntityOrThrowNotFoundException(ha, projektRepository);
        foundProjekt.setPname(projekt.getPname());
        log.debug("Update Projekt {}", foundProjekt);
        return projektRepository.update(foundProjekt);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteProjekt(final String ha) {
        log.debug("Delete Projekt with ID {}", ha);
        ServiceUtils.getEntityOrThrowNotFoundException(ha, projektRepository);
        projektRepository.deleteById(ha);
    }
}