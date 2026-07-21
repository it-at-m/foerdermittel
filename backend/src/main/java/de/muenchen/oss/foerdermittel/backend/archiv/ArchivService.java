package de.muenchen.oss.foerdermittel.backend.archiv;

import de.muenchen.oss.foerdermittel.backend.bauprogramm.Bauprogramm;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.BauprogrammFormContext;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.util.ServiceUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.arch.Processor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ArchivService {

    private final ArchivRepository archivRepository;

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
        return new ArchivFormContext(archivIds);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Archiv updateArchiv(final Archiv archiv, final Long archivID) {
        final Archiv foundArchiveintrag = ServiceUtils.getEntityOrThrowNotFoundException(archivID, archivRepository);
        foundArchiveintrag.setNotizen(archiv.getNotizen());
        log.debug("Update Bauprogramm {}", foundArchiveintrag);
        return archivRepository.update(foundArchiveintrag);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Archiv createArchiv(final Archiv archiv) {
        log.debug("Create Archiveintrag {}", archiv);
        return archivRepository.insert(archiv);
    }
    //ToDo Löschen wenn Projekt gelöscht


}
