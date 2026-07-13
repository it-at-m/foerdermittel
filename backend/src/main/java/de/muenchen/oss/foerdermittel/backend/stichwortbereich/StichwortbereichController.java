package de.muenchen.oss.foerdermittel.backend.stichwortbereich;

import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
import de.muenchen.oss.foerdermittel.backend.stichwortbereich.dto.StichwortbereichCreateDTO;
import de.muenchen.oss.foerdermittel.backend.stichwortbereich.dto.StichwortbereichMapper;
import de.muenchen.oss.foerdermittel.backend.stichwortbereich.dto.StichwortbereichResponseDTO;
import de.muenchen.oss.foerdermittel.backend.stichwortbereich.dto.StichwortbereichUpdateDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/stichwortbereiche", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = OpenAPIDocumentationConfiguration.SECURITY_SCHEME_NAME)
public class StichwortbereichController {

    private final StichwortbereichService stichwortbereichService;
    private final StichwortbereichMapper stichwortbereichMapper;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StichwortbereichResponseDTO getStichwortbereich(@PathVariable("id") final String stichwortbereichId) {
        return stichwortbereichMapper.toDTO(stichwortbereichService.getStichwortbereich(stichwortbereichId));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<StichwortbereichResponseDTO> getStichwortbereicheByPageable(@ParameterObject @PageableDefault(
            sort = "bereich"
    ) final Pageable pageable) {
        final Page<Stichwortbereich> pageWithStichwortbereich = stichwortbereichService.getAllStichwortbereiche(pageable);
        final List<StichwortbereichResponseDTO> stichwortbereichResponseDTOList = pageWithStichwortbereich.getContent().stream()
                .map(stichwortbereichMapper::toDTO)
                .toList();
        return new PageImpl<>(stichwortbereichResponseDTOList, pageWithStichwortbereich.getPageable(), pageWithStichwortbereich.getTotalElements());
    }

    @GetMapping("/form-context")
    @ResponseStatus(HttpStatus.OK)
    public StichwortbereichFormContext getStichwortbereichFormContext() {
        return stichwortbereichService.getStichwortbereichFormContext();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StichwortbereichResponseDTO createStichwortbereich(@Valid @RequestBody final StichwortbereichCreateDTO stichwortbereichCreateDTO) {
        return stichwortbereichMapper.toDTO(stichwortbereichService.createStichwortbereich(stichwortbereichMapper.toEntity(stichwortbereichCreateDTO)));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StichwortbereichResponseDTO updateStichwortbereich(@Valid @RequestBody final StichwortbereichUpdateDTO stichwortbereichUpdateDTO,
                                                    @PathVariable("id") final String stichwortbereichId) {
        return stichwortbereichMapper
                .toDTO(stichwortbereichService.updateStichwortbereich(stichwortbereichMapper.toEntity(stichwortbereichUpdateDTO), stichwortbereichId));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStichwortbereich(@PathVariable("id") final String stichwortbereichId) {
        stichwortbereichService.deleteStichwortbereich(stichwortbereichId);
    }
}
