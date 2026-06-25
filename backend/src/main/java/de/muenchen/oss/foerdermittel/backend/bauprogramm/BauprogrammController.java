package de.muenchen.oss.foerdermittel.backend.bauprogramm;

import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammCreateDTO;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammMapper;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammResponseDTO;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammUpdateDTO;
import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
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
@RequestMapping(value = "/bauprogramme", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = OpenAPIDocumentationConfiguration.SECURITY_SCHEME_NAME)
public class BauprogrammController {

    private final BauprogrammService bauprogrammService;
    private final BauprogrammMapper bauprogrammMapper;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BauprogrammResponseDTO getBauprogramm(@PathVariable("id") final String bauprogrammId) {
        return bauprogrammMapper.toDTO(bauprogrammService.getBauprogramm(parseBauprogrammId(bauprogrammId)));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<BauprogrammResponseDTO> getBauprogrammeByPageable(@ParameterObject @PageableDefault(
            sort = "bauprogramm"
    ) final Pageable pageable) {
        final Page<Bauprogramm> pageWithBauprogramm = bauprogrammService.getAllBauprogramme(pageable);
        final List<BauprogrammResponseDTO> bauprogrammResponseDTOList = pageWithBauprogramm.getContent().stream()
                .map(bauprogrammMapper::toDTO)
                .toList();
        return new PageImpl<>(bauprogrammResponseDTOList, pageWithBauprogramm.getPageable(), pageWithBauprogramm.getTotalElements());
    }

    @GetMapping("/formContext")
    @ResponseStatus(HttpStatus.OK)
    public BauprogrammFormContextDTO getBauprogrammFormContext() {
        return bauprogrammMapper.toDTO(bauprogrammService.getBauprogrammFormContext());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BauprogrammResponseDTO saveBauprogramm(@Valid @RequestBody final BauprogrammCreateDTO bauprogrammCreateDTO) {
        return bauprogrammMapper.toDTO(bauprogrammService.createBauprogramm(bauprogrammMapper.toEntity(bauprogrammCreateDTO)));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BauprogrammResponseDTO updateBauprogramm(@Valid @RequestBody final BauprogrammUpdateDTO bauprogrammUpdateDTO,
            @PathVariable("id") final String bauprogrammId) {
        return bauprogrammMapper
                .toDTO(bauprogrammService.updateBauprogramm(bauprogrammMapper.toEntity(bauprogrammUpdateDTO), parseBauprogrammId(bauprogrammId)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBauprogramm(@PathVariable("id") final String bauprogrammId) {
        bauprogrammService.deleteBauprogramm(parseBauprogrammId(bauprogrammId));
    }

    private Integer parseBauprogrammId(final String bauprogrammId) {
        try {
            return Integer.valueOf(bauprogrammId);
        } catch (final NumberFormatException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid bauprogramm ID: " + bauprogrammId, ex);
        }
    }

}
