package de.muenchen.oss.foerdermittel.backend.projekt;

import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektResponseDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektUpdateDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektCreateDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
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
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(
        value = "/projekte",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@SecurityRequirement(name = OpenAPIDocumentationConfiguration.SECURITY_SCHEME_NAME)
public class ProjektController {

    private final ProjektService projektService;
    private final ProjektMapper projektMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ProjektResponseDTO> getProjekte(
            @ParameterObject final ProjektFilter projektFilter,
            @ParameterObject @PageableDefault(sort = "projnr")
            final Pageable pageable) {

        return projektService
                .getProjekte(projektFilter, pageable)
                .map(projektMapper::toDTO);
    }

    @GetMapping("/form-context")
    @ResponseStatus(HttpStatus.OK)
    public ProjektFormContext getProjektFormContext() {
        return projektService.getProjektFormContext();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjektResponseDTO createProjekt(@Valid @RequestBody final ProjektCreateDTO projektCreateDTO) {
        return projektMapper.toDTO(projektService.createProjekt(projektMapper.toEntity(projektCreateDTO)));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProjektResponseDTO updateProjekt(@Valid @RequestBody final ProjektUpdateDTO projektUpdateDTO,
                                                          @PathVariable("id") final String projektId) {
        return projektMapper
                .toDTO(projektService.updateProjekt(projektMapper.toEntity(projektUpdateDTO), projektId));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProjekt(@PathVariable("id") final String projektId) {
        projektService.deleteProjekt(projektId);
    }
}