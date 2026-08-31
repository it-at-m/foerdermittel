package de.muenchen.oss.foerdermittel.backend.archiv;

import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import de.muenchen.oss.foerdermittel.backend.projekt.ProjektRepository;
import de.muenchen.oss.foerdermittel.backend.projekt.ProjektService;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.util.ServiceUtils;
import jakarta.persistence.EntityNotFoundException;
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
public class ArchivService {

    private final ArchivRepository archivRepository;
    private final ProjektService projektService;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Page<Archiv> getArchiveintraege(final Pageable pageable) {
        log.info("Get Archiveintrag with Pageable {}", pageable);
        return archivRepository.findAll(pageable);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    @Transactional(readOnly = true)
    public ArchivFormContext getArchivFormContext() {
        log.info("Get Archiv form context");

        return new ArchivFormContext(
                archivRepository.findAllWithProjekt(),
                projektService.getProjektFormContextDTOs());
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Archiv createArchiv(final Archiv archiv, final String projnr) {
        final Projekt projekt = projektService.getProjekt(projnr);
        archiv.setProjekt(projekt);

        log.debug("Create Archiveintrag {}", archiv);
        return archivRepository.insert(archiv);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Archiv updateArchiv(final Archiv archiv, final Long archivId) {
        final Archiv foundArchiveintrag =
                ServiceUtils.getEntityOrThrowNotFoundException(archivId, archivRepository);

        foundArchiveintrag.setSpeicherDatum(archiv.getSpeicherDatum());
        foundArchiveintrag.setSpeicherAkt(archiv.getSpeicherAkt());
        foundArchiveintrag.setSpeicherRechnungen(archiv.getSpeicherRechnungen());
        foundArchiveintrag.setMikroDatPlan(archiv.getMikroDatPlan());
        foundArchiveintrag.setMikroDat(archiv.getMikroDat());
        foundArchiveintrag.setNotizen(archiv.getNotizen());

        log.debug("Update Archiveintrag {}", foundArchiveintrag);
        return archivRepository.update(foundArchiveintrag);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteArchiv(final Long archivID) {
        log.debug("Delete Archiv with ID {}", archivID);
        final Archiv archiv = ServiceUtils.getEntityOrThrowNotFoundException(archivID, archivRepository);
        archivRepository.delete(archiv);
    }

}
