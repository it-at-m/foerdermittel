package de.muenchen.oss.foerdermittel.backend.archiv;

import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import de.muenchen.oss.foerdermittel.backend.projekt.ProjektRepository;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektMapper;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektResponseDTO;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.util.ServiceUtils;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.StreamSupport;
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
    private final ProjektRepository projektRepository;
    private final ProjektMapper projektMapper;

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

        List<Long> archivIds = archivRepository.findAllWithProjekt();

        List<ProjektResponseDTO> projekte = StreamSupport
                .stream(projektRepository.findAll().spliterator(), false)
                .map(projektMapper::toDTO)
                .toList();

        log.info("Anzahl Projekte: {}", projekte.size());

        return new ArchivFormContext(archivIds, projekte);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Archiv updateArchiv(final Archiv archiv, final Long archivID) {
        final Archiv foundArchiveintrag = ServiceUtils.getEntityOrThrowNotFoundException(archivID, archivRepository);

        foundArchiveintrag.setSpeicherDatum(archiv.getSpeicherDatum());
        foundArchiveintrag.setSpeicherAkt(archiv.getSpeicherAkt());
        foundArchiveintrag.setSpeicherRechnungen(archiv.getSpeicherRechnungen());
        foundArchiveintrag.setMikroDatPlan(archiv.getMikroDatPlan());
        foundArchiveintrag.setMikroDat(archiv.getMikroDat());
        foundArchiveintrag.setNotizen(archiv.getNotizen());

        if (archiv.getProjekt() != null && archiv.getProjekt().getProjnr() != null) {
            String projnr = archiv.getProjekt().getProjnr();

            Projekt projekt = projektRepository.findById(projnr)
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Projekt mit Projektnummer " + projnr + " wurde nicht gefunden"));

            foundArchiveintrag.setProjekt(projekt);
        }

        log.debug("Update Archiveintrag {}", foundArchiveintrag);

        return archivRepository.update(foundArchiveintrag);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Archiv createArchiv(final Archiv archiv) {
        log.debug("Create Archiveintrag {}", archiv);

        String projnr = archiv.getProjekt().getProjnr();

        Projekt projekt = projektRepository.findById(projnr)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Projekt mit Projektnummer " + projnr + " wurde nicht gefunden"));

        archiv.setProjekt(projekt);

        return archivRepository.insert(archiv);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteArchiv(final Long archivID) {
        log.debug("Delete Archiv with ID {}", archivID);
        Archiv archiv = ServiceUtils.getEntityOrThrowNotFoundException(archivID, archivRepository);
        archivRepository.delete(archiv);
    }

}
