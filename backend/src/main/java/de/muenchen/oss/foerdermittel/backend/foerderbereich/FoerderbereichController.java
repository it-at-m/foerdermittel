package de.muenchen.oss.foerdermittel.backend.foerderbereich;

import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.dto.FoerderbereichCreateDTO;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.dto.FoerderbereichMapper;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.dto.FoerderbereichResponseDTO;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.dto.FoerderbereichUpdateDTO;
import de.muenchen.oss.foerdermittel.backend.util.ControllerUtils;
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

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/foerderbereiche", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = OpenAPIDocumentationConfiguration.SECURITY_SCHEME_NAME)
public class FoerderbereichController {

    private final FoerderbereichService foerderbereichService;
    private final FoerderbereichMapper foerderbereichMapper;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FoerderbereichResponseDTO getFoerderbereich(@PathVariable("id") final String foerderbereichId) {
        return foerderbereichMapper.toDTO(foerderbereichService.getFoerderbereich(ControllerUtils.convertStringToBigDecimal(foerderbereichId)));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<FoerderbereichResponseDTO> getFoerderbereicheByPageable(@ParameterObject @PageableDefault(
            sort = "fb"
    ) final Pageable pageable) {
        final Page<Foerderbereich> pageWithFoerderbereiche = foerderbereichService.getAllFoerderbereiche(pageable);
        final List<FoerderbereichResponseDTO> foerderbereichResponseDTOList = pageWithFoerderbereiche.getContent().stream()
                .map(foerderbereichMapper::toDTO)
                .toList();
        return new PageImpl<>(foerderbereichResponseDTOList, pageWithFoerderbereiche.getPageable(), pageWithFoerderbereiche.getTotalElements());
    }

    @GetMapping("/form-context")
    @ResponseStatus(HttpStatus.OK)
    public FoerderbereichFormContext getFoerderbereichFormContext() {
        return foerderbereichService.getFoerderbereichFormContext();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FoerderbereichResponseDTO createFoerderbereich(@Valid @RequestBody final FoerderbereichCreateDTO foerderbereichCreateDTO) {
        return foerderbereichMapper.toDTO(foerderbereichService.createFoerderbereich(foerderbereichMapper.toEntity(foerderbereichCreateDTO)));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FoerderbereichResponseDTO updateFoerderbereich(@Valid @RequestBody final FoerderbereichUpdateDTO foerderbereichUpdateDTO,
            @PathVariable("id") final String foerderbereichId) {
        return foerderbereichMapper
                .toDTO(foerderbereichService.updateFoerderbereich(foerderbereichMapper.toEntity(foerderbereichUpdateDTO),
                        ControllerUtils.convertStringToBigDecimal(foerderbereichId)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFoerderbereich(@PathVariable("id") final String foerderbereichId) {
        foerderbereichService.deleteFoerderbereich(ControllerUtils.convertStringToBigDecimal(foerderbereichId));
    }

}
