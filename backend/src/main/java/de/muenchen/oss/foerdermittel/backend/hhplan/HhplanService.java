package de.muenchen.oss.foerdermittel.backend.hhplan;

import de.muenchen.oss.foerdermittel.backend.hhplan.Hhplan;
import de.muenchen.oss.foerdermittel.backend.hhplan.HhplanFormContext;
import de.muenchen.oss.foerdermittel.backend.hhplan.HhplanRepository;
import de.muenchen.oss.foerdermittel.backend.hhplan.dto.HhplanFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.hhplan.dto.HhplanMapper;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.util.ServiceUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class HhplanService {

    private final HhplanRepository hhplanRepository;
    private final HhplanMapper hhplanMapper;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Page<Hhplan> getHhplan(final Pageable pageable) {
        log.info("Get Hhplan with Pageable {}", pageable);
        return hhplanRepository.findAll(pageable);
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public List<HhplanFormContextDTO> getHhplanFormContextDTOs() {
        return hhplanMapper.toFormContext(hhplanRepository.findAll());
    }

    @PreAuthorize(Authorities.HAS_ROLE_ADMIN)
    @Transactional(readOnly = true)
    public HhplanFormContext getHhplanFormContext() {
        log.info("Get Hhplan form context");
        return new HhplanFormContext(hhplanRepository.findAllHhplan());
    }


    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public void checkExistsByHhplan(final BigDecimal bereich) {
        ServiceUtils.checkExistsOrThrowNotFoundException(bereich, hhplanRepository, Hhplan.class);
    }

}
