package de.muenchen.oss.foerdermittel.backend.siedlungsgebiet;

import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.util.ServiceUtils;
import java.math.BigDecimal;
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
public class SiedlungsgebietService {

    private final SiedlungsgebietRepository siedlungsgebietRepository;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Siedlungsgebiet getSiedlungsgebiet(final BigDecimal siedlungsgebietId) {
        log.info("Get Siedlungsgebiet with ID {}", siedlungsgebietId);
        return ServiceUtils.getEntityOrThrowNotFoundException(siedlungsgebietId, siedlungsgebietRepository);
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Page<Siedlungsgebiet> getAllSiedlungsgebiete(final Pageable pageable) {
        log.info("Get all Siedlungsgebiete with Pageable {}", pageable);
        return siedlungsgebietRepository.findAll(pageable);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    @Transactional(readOnly = true)
    public SiedlungsgebietFormContext getSiedlungsgebietFormContext() {
        log.info("Get Siedlungsgebiet form context");
        return new SiedlungsgebietFormContext(siedlungsgebietRepository.findAllSiedlungsgebiete());
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Siedlungsgebiet createSiedlungsgebiet(final Siedlungsgebiet siedlungsgebiet) {
        log.debug("Create Siedlungsgebiet {}", siedlungsgebiet);
        return siedlungsgebietRepository.insert(siedlungsgebiet);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Siedlungsgebiet updateSiedlungsgebiet(final Siedlungsgebiet siedlungsgebiet, final BigDecimal siedlungsgebietId) {
        final Siedlungsgebiet foundSiedlungsgebiet = ServiceUtils.getEntityOrThrowNotFoundException(
                siedlungsgebietId, siedlungsgebietRepository);

        foundSiedlungsgebiet.setBezeichnung(siedlungsgebiet.getBezeichnung());
        log.debug("Update Siedlungsgebiet {}", foundSiedlungsgebiet);
        return siedlungsgebietRepository.update(foundSiedlungsgebiet);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteSiedlungsgebiet(final BigDecimal siedlungsgebietId) {
        log.debug("Delete Siedlungsgebiet with ID {}", siedlungsgebietId);
        ServiceUtils.getEntityOrThrowNotFoundException(siedlungsgebietId, siedlungsgebietRepository);
        siedlungsgebietRepository.deleteById(siedlungsgebietId);
    }
}
