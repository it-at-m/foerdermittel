package de.muenchen.oss.foerdermittel.backend.bauleitung;

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
public class BauleitungService {

    private final BauleitungRepository bauleitungRepository;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Bauleitung getBauleitung(final String bauleitungId) {
        log.info("Get Bauleitung with ID {}", bauleitungId);
        return ServiceUtils.getEntityOrThrowNotFoundException(bauleitungId, bauleitungRepository);
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Page<Bauleitung> getAllBauleitungen(final Pageable pageable) {
        log.info("Get all Bauleitungen with Pageable {}", pageable);
        return bauleitungRepository.findAll(pageable);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    @Transactional(readOnly = true)
    public BauleitungFormContext getBauleitungFormContext() {
        log.info("Get Bauleitung form context");
        return new BauleitungFormContext(bauleitungRepository.findAllBauleitungen());
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Bauleitung createBauleitung(final Bauleitung bauleitung) {
        log.debug("Create Bauleitung {}", bauleitung);
        return bauleitungRepository.insert(bauleitung);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Bauleitung updateBauleitung(final Bauleitung bauleitung, final String bauleitungId) {
        final Bauleitung foundBauleitung = ServiceUtils.getEntityOrThrowNotFoundException(bauleitungId, bauleitungRepository);
        foundBauleitung.setBezeichnung(bauleitung.getBezeichnung());
        log.debug("Update Bauleitung {}", foundBauleitung);
        return bauleitungRepository.update(foundBauleitung);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteBauleitung(final String bauleitungId) {
        log.debug("Delete Bauleitung with ID {}", bauleitungId);
        ServiceUtils.getEntityOrThrowNotFoundException(bauleitungId, bauleitungRepository);
        bauleitungRepository.deleteById(bauleitungId);
    }
}
