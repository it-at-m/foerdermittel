package de.muenchen.oss.foerdermittel.backend.foerderbereich;

import de.muenchen.oss.foerdermittel.backend.foerderbereich.dto.FoerderbereichFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.dto.FoerderbereichMapper;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.util.ServiceUtils;
import java.math.BigDecimal;
import java.util.List;

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
public class FoerderbereichService {

    private final FoerderbereichRepository foerderbereichRepository;
    private final FoerderbereichMapper foerderbereichMapper;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Page<Foerderbereich> getFoerderbereiche(final Pageable pageable) {
        log.info("Get Foerderbereiche with Pageable {}", pageable);
        return foerderbereichRepository.findAll(pageable);
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public List<FoerderbereichFormContextDTO> getFoerderbereichFormContextDTOs() {
        return foerderbereichMapper.toFormContext(foerderbereichRepository.findAll());
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    @Transactional(readOnly = true)
    public FoerderbereichFormContext getFoerderbereichFormContext() {
        log.info("Get Foerderbereich form context");
        return new FoerderbereichFormContext(foerderbereichRepository.findAllFb());
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Foerderbereich createFoerderbereich(final Foerderbereich foerderbereich) {
        log.debug("Create Foerderbereich {}", foerderbereich);
        return foerderbereichRepository.insert(foerderbereich);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public Foerderbereich updateFoerderbereich(final Foerderbereich foerderbereich, final BigDecimal foerderbereichId) {
        final Foerderbereich foundFoerderbereich = ServiceUtils.getEntityOrThrowNotFoundException(foerderbereichId, foerderbereichRepository,
                Foerderbereich.class);
        foundFoerderbereich.setBezeichnung(foerderbereich.getBezeichnung());
        foundFoerderbereich.setFinanzausgleich(foerderbereich.getFinanzausgleich());
        foundFoerderbereich.setJahresstatistik(foerderbereich.getJahresstatistik());
        foundFoerderbereich.setKindergarten(foerderbereich.getKindergarten());
        foundFoerderbereich.setNichtRelevant(foerderbereich.getNichtRelevant());
        log.debug("Update Foerderbereich {}", foundFoerderbereich);
        return foerderbereichRepository.update(foundFoerderbereich);
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    public void deleteFoerderbereich(final BigDecimal foerderbereichId) {
        log.debug("Delete Foerderbereich with ID {}", foerderbereichId);
        ServiceUtils.getEntityOrThrowNotFoundException(foerderbereichId, foerderbereichRepository, Foerderbereich.class);
        foerderbereichRepository.deleteById(foerderbereichId);
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public void checkExistsByFoerderbereich(final BigDecimal bereich) {
        ServiceUtils.checkExistsOrThrowNotFoundException(bereich, foerderbereichRepository, Foerderbereich.class);
    }

}
