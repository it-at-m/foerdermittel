package de.muenchen.oss.foerdermittel.backend.bauleitung;

import static de.muenchen.oss.foerdermittel.backend.common.ExceptionMessageConstants.MSG_NOT_FOUND;

import de.muenchen.oss.foerdermittel.backend.common.NotFoundException;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

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
        return getBauleitungOrThrowException(bauleitungId);
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
        final Bauleitung foundBauleitung = getBauleitungOrThrowException(bauleitungId);
        foundBauleitung.setBezeichnung(bauleitung.getBezeichnung());
        log.debug("Update Bauleitung {}", foundBauleitung);
        return bauleitungRepository.update(foundBauleitung);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteBauleitung(final String bauleitungId) {
        log.debug("Delete Bauleitung with ID {}", bauleitungId);
        if (!bauleitungRepository.existsById(bauleitungId)) {
            throw new NotFoundException(String.format(MSG_NOT_FOUND, bauleitungId));
        }
        bauleitungRepository.deleteById(bauleitungId);
    }

    private Bauleitung getBauleitungOrThrowException(final String bauleitungId) {
        return bauleitungRepository
                .findById(bauleitungId)
                .orElseThrow(() -> new NotFoundException(String.format(MSG_NOT_FOUND, bauleitungId)));
    }
}
