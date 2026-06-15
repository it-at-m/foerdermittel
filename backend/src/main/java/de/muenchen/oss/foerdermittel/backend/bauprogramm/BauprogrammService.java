package de.muenchen.oss.foerdermittel.backend.bauprogramm;

import static de.muenchen.oss.foerdermittel.backend.common.ExceptionMessageConstants.MSG_NOT_FOUND;

import de.muenchen.oss.foerdermittel.backend.common.NotFoundException;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public Page<Bauprogramm> getAllBauprogramme(final int pageNumber, final int pageSize) {
        log.info("Get all Bauprogramme with Page {} and PageSize {}", pageNumber, pageSize);
        final Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
        return bauprogrammRepository.findAll(pageRequest);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Bauprogramm createBauprogramm(final Bauprogramm bauprogramm) {
        log.debug("Create Bauprogramm {}", bauprogramm);
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
        bauprogrammRepository.deleteById(bauprogrammId);
    }

    private Bauprogramm getBauprogrammOrThrowException(final Integer bauprogrammId) {
        return bauprogrammRepository
                .findById(bauprogrammId)
                .orElseThrow(() -> new NotFoundException(String.format(MSG_NOT_FOUND, bauprogrammId)));
    }
}
