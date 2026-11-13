package de.muenchen.oss.foerdermittel.backend.antrag;

import de.muenchen.oss.foerdermittel.backend.bewilligung.Bewilligung;
import de.muenchen.oss.foerdermittel.backend.bewilligung.BewilligungRepository;
import de.muenchen.oss.foerdermittel.backend.common.ConflictException;
import de.muenchen.oss.foerdermittel.backend.common.DeleteNotAllowedException;
import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import de.muenchen.oss.foerdermittel.backend.projekt.ProjektService;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.util.ServiceUtils;
import java.util.Set;
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
public class AntragService {

    /// Antragstypen that may exist only once per Projekt.
    private static final Set<Antragstyp> UNIQUE_ANTRAGSTYPEN = Set.of(Antragstyp.E, Antragstyp.U);

    private final AntragRepository antragRepository;
    private final ProjektService projektService;
    private final BewilligungRepository bewilligungRepository;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Page<Antrag> getAntraege(final Pageable pageable) {
        log.info("Get Antraege with Pageable: {}", pageable);
        return antragRepository.findAll(pageable);
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public AntragFormContext getAntragFormContext() {
        log.info("Get Antrag form context");
        return new AntragFormContext(projektService.getProjektFormContextDTOs());
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    public Antrag createAntrag(final Antrag antrag, final String projnr) {
        final Projekt projekt = projektService.getProjekt(projnr);
        antrag.setProjekt(projekt);
        checkAntragstypUnique(projnr, antrag.getAntragstyp(), null);

        log.debug("Create Antrag: {}", antrag);
        return antragRepository.insert(antrag);
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    public Antrag updateAntrag(final Antrag antrag, final Long antragID) {
        final Antrag foundAntrag = ServiceUtils.getEntityOrThrowNotFoundException(antragID, antragRepository, Antrag.class);
        checkAntragstypUnique(foundAntrag.getProjekt().getProjnr(), antrag.getAntragstyp(), antragID);

        foundAntrag.setAntragstyp(antrag.getAntragstyp());
        foundAntrag.setAntragsdatum(antrag.getAntragsdatum());
        foundAntrag.setGeskosten(antrag.getGeskosten());
        foundAntrag.setZwfkosten(antrag.getZwfkosten());
        foundAntrag.setVorzbeg(antrag.getVorzbeg());
        foundAntrag.setVbdatum(antrag.getVbdatum());
        foundAntrag.setUnbeddat(antrag.getUnbeddat());
        foundAntrag.setUnbedja(antrag.getUnbedja());
        foundAntrag.setUnbedbis(antrag.getUnbedbis());
        foundAntrag.setAsuZ(antrag.getAsuZ());
        foundAntrag.setAsuD(antrag.getAsuD());
        foundAntrag.setAsuK(antrag.getAsuK());
        foundAntrag.setBvorSuZ(antrag.getBvorSuZ());
        foundAntrag.setBvorSuD(antrag.getBvorSuD());
        foundAntrag.setBvorSuK(antrag.getBvorSuK());
        foundAntrag.setNotizen(antrag.getNotizen());

        log.debug("Update Antrag: {}", foundAntrag);
        return antragRepository.update(foundAntrag);
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    public void deleteAntrag(final Long antragID) {
        log.debug("Delete Antrag: {}", antragID);
        final Antrag antrag = ServiceUtils.getEntityOrThrowNotFoundException(antragID, antragRepository, Antrag.class);

        if (bewilligungRepository.existsByAntragId(antragID)) {
            throw new DeleteNotAllowedException(Antrag.class, Bewilligung.class);
        }

        antragRepository.delete(antrag);
    }

    private void checkAntragstypUnique(final String projnr, final Antragstyp antragstyp, final Long antragID) {
        if (UNIQUE_ANTRAGSTYPEN.contains(antragstyp) && antragRepository.existsOtherAntrag(projnr, antragstyp, antragID)) {
            throw new ConflictException(String.format("Projekt %s already has an Antrag of type %s", projnr, antragstyp));
        }
    }
}
