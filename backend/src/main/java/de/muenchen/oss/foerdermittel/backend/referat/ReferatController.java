package de.muenchen.oss.foerdermittel.backend.referat;

import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
import de.muenchen.oss.foerdermittel.backend.referat.dto.ReferatCreateDTO;
import de.muenchen.oss.foerdermittel.backend.referat.dto.ReferatMapper;
import de.muenchen.oss.foerdermittel.backend.referat.dto.ReferatResponseDTO;
import de.muenchen.oss.foerdermittel.backend.referat.dto.ReferatUpdateDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/referate", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = OpenAPIDocumentationConfiguration.SECURITY_SCHEME_NAME)
public class ReferatController {

    private final ReferatService referatService;
    private final ReferatMapper referatMapper;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReferatResponseDTO getReferat(@PathVariable("id") final String referatId) {
        return referatMapper.toDTO(referatService.getReferat(parseReferatId(referatId)));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ReferatResponseDTO> getReferateByPageable(@ParameterObject @PageableDefault(
            sort = "refnr"
    ) final Pageable pageable) {
        final Page<Referat> pageWithReferate = referatService.getAllReferate(pageable);
        final List<ReferatResponseDTO> referatResponseDTOList = pageWithReferate.getContent().stream()
                .map(referatMapper::toDTO)
                .toList();
        return new PageImpl<>(referatResponseDTOList, pageWithReferate.getPageable(), pageWithReferate.getTotalElements());
    }

    @GetMapping("/form-context")
    @ResponseStatus(HttpStatus.OK)
    public ReferatFormContext getReferatFormContext() {
        return referatService.getReferatFormContext();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReferatResponseDTO createReferat(@Valid @RequestBody final ReferatCreateDTO referatCreateDTO) {
        return referatMapper.toDTO(referatService.createReferat(referatMapper.toEntity(referatCreateDTO)));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReferatResponseDTO updateReferat(@Valid @RequestBody final ReferatUpdateDTO referatUpdateDTO,
            @PathVariable("id") final String referatId) {
        return referatMapper
                .toDTO(referatService.updateReferat(referatMapper.toEntity(referatUpdateDTO), parseReferatId(referatId)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteReferat(@PathVariable("id") final String referatId) {
        referatService.deleteReferat(parseReferatId(referatId));
    }

    private BigDecimal parseReferatId(final String referatId) {
        try {
            return BigDecimal.valueOf(Integer.parseInt(referatId));
        } catch (final NumberFormatException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid referat ID: " + referatId, ex);
        }
    }

}
