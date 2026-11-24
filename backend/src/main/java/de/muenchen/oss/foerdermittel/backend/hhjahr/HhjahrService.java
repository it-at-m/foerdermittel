
package de.muenchen.oss.foerdermittel.backend.hhjahr;

import de.muenchen.oss.foerdermittel.backend.hhplan.Hhplan;
import de.muenchen.oss.foerdermittel.backend.hhplan.HhplanRepository;
import de.muenchen.oss.foerdermittel.backend.security.Authorities;
import de.muenchen.oss.foerdermittel.backend.util.ServiceUtils;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
public class HhjahrService {

    private final HhjahrRepository hhjahrRepository;
    private final HhplanRepository hhplanRepository;

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Hhjahr getHhjahr(final BigDecimal hhjahr) {
        log.info("Get Haushaltsjahr with hhjahr {}", hhjahr);
        return ServiceUtils.getEntityOrThrowNotFoundException(
                hhjahr, hhjahrRepository, Hhjahr.class);
    }

    @PreAuthorize(Authorities.HAS_ANY_ROLE)
    @Transactional(readOnly = true)
    public Page<Hhjahr> getHhjahre(final Pageable pageable) {
        log.info("Get Haushaltsjahre with Pageable {}", pageable);

        final Page<Hhjahr> hhjahre = hhjahrRepository.findAll(pageable);

        final List<BigDecimal> hhjahrIds = hhjahre.getContent()
                .stream()
                .map(Hhjahr::getHhjahr)
                .toList();

        if (hhjahrIds.isEmpty()) {
            return hhjahre;
        }

        final Map<BigDecimal, List<Hhplan>> hhplanByHhjahr = hhplanRepository.findAllByHhjJahrIn(hhjahrIds)
                .stream()
                .collect(Collectors.groupingBy(
                        hhplan -> hhplan.getId().getHhjJahr()));

        hhjahre.forEach(hhjahr -> hhjahr.setHhplanlisten(
                hhplanByHhjahr.getOrDefault(hhjahr.getHhjahr(), List.of())));

        return hhjahre;
    }

}
