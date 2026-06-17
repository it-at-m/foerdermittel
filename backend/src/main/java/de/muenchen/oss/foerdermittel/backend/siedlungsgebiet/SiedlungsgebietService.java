package de.muenchen.oss.foerdermittel.backend.siedlungsgebiet;

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
public class SiedlungsgebietService {

    private final SiedlungsgebietRepository siedlungsgebietRepository;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    public Siedlungsgebiet getSiedlungsgebiet(final Integer siedlungsgebietId) {
        log.info("Get Siedlungsgebiet with ID {}", siedlungsgebietId);
        return getSiedlungsgebietOrThrowException(siedlungsgebietId);
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    public Page<Siedlungsgebiet> getAllSiedlungsgebiete(final int pageNumber, final int pageSize) {
        log.info("Get all Siedlungsgebiete with Page {} and PageSize {}", pageNumber, pageSize);
        final Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
        return siedlungsgebietRepository.findAll(pageRequest);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Siedlungsgebiet createSiedlungsgebiet(final Siedlungsgebiet siedlungsgebiet) {
        log.debug("Create Siedlungsgebiet {}", siedlungsgebiet);
        return siedlungsgebietRepository.save(siedlungsgebiet);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Siedlungsgebiet updateSiedlungsgebiet(final Siedlungsgebiet siedlungsgebiet, final Integer siedlungsgebietId) {
        final Siedlungsgebiet foundSiedlungsgebiet = getSiedlungsgebietOrThrowException(siedlungsgebietId);
        foundSiedlungsgebiet.setBezeichnung(siedlungsgebiet.getBezeichnung());
        log.debug("Update Siedlungsgebiet {}", foundSiedlungsgebiet);
        return siedlungsgebietRepository.save(foundSiedlungsgebiet);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteSiedlungsgebiet(final Integer siedlungsgebietId) {
        log.debug("Delete Siedlungsgebiet with ID {}", siedlungsgebietId);
        siedlungsgebietRepository.deleteById(siedlungsgebietId);
    }

    private Siedlungsgebiet getSiedlungsgebietOrThrowException(final Integer siedlungsgebietId) {
        return siedlungsgebietRepository
                .findById(siedlungsgebietId)
                .orElseThrow(() -> new NotFoundException(String.format(MSG_NOT_FOUND, siedlungsgebietId)));
    }
}
