package de.muenchen.oss.foerdermittel.backend.projekt;

import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto.KurzbezeichnungMapper;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektMapper;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektResponseDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
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

//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public Page<Projekt> getProjekte(
//            @ParameterObject final ProjektFilter filter,
//            @ParameterObject @PageableDefault(sort = "projnr") final Pageable pageable) {
//
//        return projektService.getProjekte(filter, pageable);
//    }

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
}