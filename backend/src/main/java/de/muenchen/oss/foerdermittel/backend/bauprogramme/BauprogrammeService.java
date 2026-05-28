package de.muenchen.oss.foerdermittel.backend.bauprogramme;

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
public class BauprogrammeService {

    private final BauprogrammeRepository bauprogrammeRepository;

    @PreAuthorize(Authorities.BAUPROGRAMM_GET)
    public Bauprogramme getBauprogramm(final Integer bauprogrammId) {
        log.info("Get Bauprogramm with ID {}", bauprogrammId);
        return getBauprogrammOrThrowException(bauprogrammId);
    }

    @PreAuthorize(Authorities.BAUPROGRAMM_GET_ALL)
    public Page<Bauprogramme> getAllBauprogramme(final int pageNumber, final int pageSize) {
        log.info("Get all Bauprogramme with Page {} and PageSize {}", pageNumber, pageSize);
        final Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
        return bauprogrammeRepository.findAll(pageRequest);
    }

    @PreAuthorize(Authorities.BAUPROGRAMM_CREATE)
    public Bauprogramme createBauprogramm(final Bauprogramme bauprogramm) {
        log.debug("Create Bauprogramm {}", bauprogramm);
        return bauprogrammeRepository.save(bauprogramm);
    }

    @PreAuthorize(Authorities.BAUPROGRAMM_UPDATE)
    public Bauprogramme updateBauprogramm(final Bauprogramme bauprogramm, final Integer bauprogrammId) {
        final Bauprogramme foundBauprogramm = getBauprogrammOrThrowException(bauprogrammId);
        foundBauprogramm.setBezeichnung(bauprogramm.getBezeichnung());
        log.debug("Update Bauprogramm {}", foundBauprogramm);
        return bauprogrammeRepository.save(foundBauprogramm);
    }

    @PreAuthorize(Authorities.BAUPROGRAMM_DELETE)
    public void deleteBauprogramm(final Integer bauprogrammId) {
        log.debug("Delete Bauprogramm with ID {}", bauprogrammId);
        bauprogrammeRepository.deleteById(bauprogrammId);
    }

    private Bauprogramme getBauprogrammOrThrowException(final Integer bauprogrammId) {
        return bauprogrammeRepository
                .findById(bauprogrammId)
                .orElseThrow(() -> new NotFoundException(String.format(MSG_NOT_FOUND, bauprogrammId)));
    }
}

