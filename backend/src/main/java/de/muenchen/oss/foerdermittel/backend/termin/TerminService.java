package de.muenchen.oss.foerdermittel.backend.termin;

import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import de.muenchen.oss.foerdermittel.backend.projekt.ProjektService;
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
public class TerminService {

    private final TerminRepository terminRepository;
    private final ProjektService projektService;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Page<Termin> getTermin(final Pageable pageable) {
        log.info("Get Termin with Pageable: {}", pageable);
        return terminRepository.findAll(pageable);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    @Transactional(readOnly = true)
    public TerminFormContext getTerminFormContext() {
        log.info("Get Termin form context");

        return new TerminFormContext(
                terminRepository.findAllTermine(),
                projektService.getProjektFormContextDTOs());
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Termin createTermin(final Termin termin, final String projnr) {
        final Projekt projekt = projektService.getProjekt(projnr);
        termin.setProjekt(projekt);

        log.debug("Create Termin: {}", termin);
        return terminRepository.insert(termin);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Termin updateTermin(final Termin termin, final Long terminID) {
        final Termin foundTermin = ServiceUtils.getEntityOrThrowNotFoundException(terminID, terminRepository, Termin.class);

        foundTermin.setTermin(termin.getTermin());
        foundTermin.setZustaendig(termin.getZustaendig());
        foundTermin.setTelefon(termin.getTelefon());
        foundTermin.setNotizen(termin.getNotizen());
        foundTermin.setUeberwachung(termin.getUeberwachung());

        log.debug("Update Termin: {}", foundTermin);

        return terminRepository.update(foundTermin);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteTermin(final Long terminID) {
        log.debug("Delete Termin: {}", terminID);
        final Termin termin = ServiceUtils.getEntityOrThrowNotFoundException(terminID, terminRepository, Termin.class);
        terminRepository.delete(termin);
    }
}
