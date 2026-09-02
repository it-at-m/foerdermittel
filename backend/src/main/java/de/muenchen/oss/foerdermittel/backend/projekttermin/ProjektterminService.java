package de.muenchen.oss.foerdermittel.backend.projekttermin;

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
public class ProjektterminService {

    private final ProjektterminRepository projektterminRepository;
    private final ProjektService projektService;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Page<Projekttermin> getProjekttermine(final Pageable pageable) {
        log.info("Get Projekttermine with Pageable: {}", pageable);
        return projektterminRepository.findAll(pageable);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    @Transactional(readOnly = true)
    public ProjektterminFormContext getProjektterminFormContext() {
        log.info("Get Projekttermin form context");

        return new ProjektterminFormContext(
                projektterminRepository.findAllProjekttermine(),
                projektService.getProjektFormContextDTOs());
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Projekttermin createProjekttermin(final Projekttermin projekttermin, final String projnr) {
        final Projekt projekt = projektService.getProjekt(projnr);
        projekttermin.setProjekt(projekt);

        log.debug("Create Projekttermin: {}", projekttermin);
        return projektterminRepository.insert(projekttermin);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Projekttermin updateProjekttermin(final Projekttermin projekttermin, final Long terminID) {
        final Projekttermin foundProjekttermin = ServiceUtils.getEntityOrThrowNotFoundException(terminID, projektterminRepository);

        foundProjekttermin.setTermin(projekttermin.getTermin());
        foundProjekttermin.setZustaendig(projekttermin.getZustaendig());
        foundProjekttermin.setTelefon(projekttermin.getTelefon());
        foundProjekttermin.setNotizen(projekttermin.getNotizen());
        foundProjekttermin.setUeberwachung(projekttermin.getUeberwachung());

        log.debug("Update Projekttermin: {}", foundProjekttermin);

        return projektterminRepository.update(foundProjekttermin);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteProjekttermin(final Long terminID) {
        log.debug("Delete Projekttermin: {}", terminID);
        final Projekttermin projekttermin = ServiceUtils.getEntityOrThrowNotFoundException(terminID, projektterminRepository);
        projektterminRepository.delete(projekttermin);
    }
}
