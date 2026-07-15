package de.muenchen.oss.foerdermittel.backend.publikation;

import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
import de.muenchen.oss.foerdermittel.backend.publikation.dto.PublikationCreateDTO;
import de.muenchen.oss.foerdermittel.backend.publikation.dto.PublikationMapper;
import de.muenchen.oss.foerdermittel.backend.publikation.dto.PublikationResponseDTO;
import de.muenchen.oss.foerdermittel.backend.publikation.dto.PublikationUpdateDTO;
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
@RequestMapping(value = "/publikationen", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = OpenAPIDocumentationConfiguration.SECURITY_SCHEME_NAME)
public class PublikationController {

    private final PublikationService publikationService;
    private final PublikationMapper publikationMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<PublikationResponseDTO> getPublikationenByPageable(@ParameterObject @PageableDefault(
            sort = "kurzform"
    ) final Pageable pageable) {
        final Page<Publikation> pageWithPublikation = publikationService.getAllPublikationen(pageable);
        final List<PublikationResponseDTO> publikationResponseDTOList = pageWithPublikation.getContent().stream()
                .map(publikationMapper::toDTO)
                .toList();
        return new PageImpl<>(publikationResponseDTOList, pageWithPublikation.getPageable(), pageWithPublikation.getTotalElements());
    }

    @GetMapping("/form-context")
    @ResponseStatus(HttpStatus.OK)
    public PublikationFormContext getPublikationFormContext() {
        return publikationService.getPublikationFormContext();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PublikationResponseDTO createPublikation(@Valid @RequestBody final PublikationCreateDTO publikationCreateDTO) {
        return publikationMapper.toDTO(publikationService.createPublikation(publikationMapper.toEntity(publikationCreateDTO)));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PublikationResponseDTO updatePublikation(@Valid @RequestBody final PublikationUpdateDTO publikationUpdateDTO,
            @PathVariable("id") final String publikationId) {
        return publikationMapper
                .toDTO(publikationService.updatePublikation(publikationMapper.toEntity(publikationUpdateDTO), publikationId));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePublikation(@PathVariable("id") final String publikationId) {
        publikationService.deletePublikation(publikationId);
    }

}
