package de.muenchen.oss.foerdermittel.backend.unterabschnitt;

import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.dto.UnterabschnittCreateDTO;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.dto.UnterabschnittMapper;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.dto.UnterabschnittResponseDTO;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.dto.UnterabschnittUpdateDTO;
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
@RequestMapping(value = "/unterabschnitte", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = OpenAPIDocumentationConfiguration.SECURITY_SCHEME_NAME)
public class UnterabschnittController {

    private final UnterabschnittService unterabschnittService;
    private final UnterabschnittMapper unterabschnittMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<UnterabschnittResponseDTO> getUnterabschnitte(@ParameterObject @PageableDefault(
            sort = "hasHa"
    ) final Pageable pageable) {
        final Page<Unterabschnitt> pageWithUnterabschnitt = unterabschnittService.getUnterabschnitte(pageable);
        final List<UnterabschnittResponseDTO> unterabschnittResponseDTOList = pageWithUnterabschnitt.getContent().stream()
                .map(unterabschnittMapper::toDTO)
                .toList();
        return new PageImpl<>(unterabschnittResponseDTOList, pageWithUnterabschnitt.getPageable(), pageWithUnterabschnitt.getTotalElements());
    }

    @GetMapping("/form-context")
    @ResponseStatus(HttpStatus.OK)
    public UnterabschnittFormContext getUnterabschnittFormContext() {
        return unterabschnittService.getUnterabschnittFormContext();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UnterabschnittResponseDTO createUnterabschnitt(@Valid @RequestBody final UnterabschnittCreateDTO unterabschnittCreateDTO) {
        final Unterabschnitt unterabschnitt = unterabschnittMapper.toEntity(unterabschnittCreateDTO);
        return unterabschnittMapper.toDTO(unterabschnittService.createUnterabschnitt(unterabschnitt, unterabschnittCreateDTO.hasHa()));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UnterabschnittResponseDTO updateUnterabschnitt(@Valid @RequestBody final UnterabschnittUpdateDTO unterabschnittUpdateDTO,
            @PathVariable("id") final String unterabschnittId) {
        return unterabschnittMapper
                .toDTO(unterabschnittService.updateUnterabschnitt(unterabschnittMapper.toEntity(unterabschnittUpdateDTO), unterabschnittId));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUnterabschnitt(@PathVariable("id") final String unterabschnittId) {
        unterabschnittService.deleteUnterabschnitt(unterabschnittId);
    }

}
