package de.muenchen.oss.foerdermittel.backend.bauprogramm;

import static de.muenchen.oss.foerdermittel.backend.common.ExceptionMessageConstants.MSG_ALREADY_EXISTS;
import static de.muenchen.oss.foerdermittel.backend.common.ExceptionMessageConstants.MSG_NOT_FOUND;

import de.muenchen.oss.foerdermittel.backend.common.AlreadyExistsException;
import de.muenchen.oss.foerdermittel.backend.common.NotFoundException;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BauprogrammService {

    private final BauprogrammRepository bauprogrammRepository;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    public Bauprogramm getBauprogramm(final Integer bauprogrammId) {
        log.info("Get Bauprogramm with ID {}", bauprogrammId);
        return getBauprogrammOrThrowException(bauprogrammId);
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    public Page<Bauprogramm> getAllBauprogramme(final Pageable pageable) {
        log.info("Get all Bauprogramme with Pageable {}", pageable);
        return bauprogrammRepository.findAll(pageable);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public BauprogrammFormContext getBauprogrammFormContext() {
        log.info("Get Bauprogramm form context");
        return new BauprogrammFormContext(bauprogrammRepository.findAllBauprogramme());
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Bauprogramm createBauprogramm(final Bauprogramm bauprogramm) {
        log.debug("Create Bauprogramm {}", bauprogramm);
        final int bauprogrammId = bauprogramm.getBauprogramm().intValue();
        if (bauprogrammRepository.existsById(bauprogrammId)) {
            throw new AlreadyExistsException(String.format(MSG_ALREADY_EXISTS, bauprogrammId));
        }
        return bauprogrammRepository.save(bauprogramm);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Bauprogramm updateBauprogramm(final Bauprogramm bauprogramm, final Integer bauprogrammId) {
        final Bauprogramm foundBauprogramm = getBauprogrammOrThrowException(bauprogrammId);
        foundBauprogramm.setBezeichnung(bauprogramm.getBezeichnung());
        log.debug("Update Bauprogramm {}", foundBauprogramm);
        return bauprogrammRepository.save(foundBauprogramm);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteBauprogramm(final Integer bauprogrammId) {
        log.debug("Delete Bauprogramm with ID {}", bauprogrammId);
        if (!bauprogrammRepository.existsById(bauprogrammId)) {
            throw new NotFoundException(String.format(MSG_NOT_FOUND, bauprogrammId));
        }
        bauprogrammRepository.deleteById(bauprogrammId);
    }

    private Bauprogramm getBauprogrammOrThrowException(final Integer bauprogrammId) {
        return bauprogrammRepository
                .findById(bauprogrammId)
                .orElseThrow(() -> new NotFoundException(String.format(MSG_NOT_FOUND, bauprogrammId)));
    }
}
