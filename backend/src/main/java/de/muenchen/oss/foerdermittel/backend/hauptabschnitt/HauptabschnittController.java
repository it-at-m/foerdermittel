package de.muenchen.oss.foerdermittel.backend.hauptabschnitt;

import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
import de.muenchen.oss.foerdermittel.backend.hauptabschnitt.dto.HauptabschnittCreateDTO;
import de.muenchen.oss.foerdermittel.backend.hauptabschnitt.dto.HauptabschnittMapper;
import de.muenchen.oss.foerdermittel.backend.hauptabschnitt.dto.HauptabschnittResponseDTO;
import de.muenchen.oss.foerdermittel.backend.hauptabschnitt.dto.HauptabschnittUpdateDTO;
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
@RequestMapping(value = "/hauptabschnitte", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = OpenAPIDocumentationConfiguration.SECURITY_SCHEME_NAME)
public class HauptabschnittController {

    private final HauptabschnittService hauptabschnittService;
    private final HauptabschnittMapper hauptabschnittMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<HauptabschnittResponseDTO> getHauptabschnitte(@ParameterObject @PageableDefault(
            sort = "ha"
    ) final Pageable pageable) {
        final Page<Hauptabschnitt> pageWithHauptabschnitt = hauptabschnittService.getHauptabschnitte(pageable);
        final List<HauptabschnittResponseDTO> hauptabschnittResponseDTOList = pageWithHauptabschnitt.getContent().stream()
                .map(hauptabschnittMapper::toDTO)
                .toList();
        return new PageImpl<>(hauptabschnittResponseDTOList, pageWithHauptabschnitt.getPageable(), pageWithHauptabschnitt.getTotalElements());
    }

    @GetMapping("/form-context")
    @ResponseStatus(HttpStatus.OK)
    public HauptabschnittFormContext getHauptabschnittFormContext() {
        return hauptabschnittService.getHauptabschnittFormContext();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HauptabschnittResponseDTO createHauptabschnitt(@Valid @RequestBody final HauptabschnittCreateDTO hauptabschnittCreateDTO) {
        return hauptabschnittMapper.toDTO(hauptabschnittService.createHauptabschnitt(hauptabschnittMapper.toEntity(hauptabschnittCreateDTO)));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HauptabschnittResponseDTO updateHauptabschnitt(@Valid @RequestBody final HauptabschnittUpdateDTO hauptabschnittUpdateDTO,
            @PathVariable("id") final String hauptabschnittId) {
        return hauptabschnittMapper
                .toDTO(hauptabschnittService.updateHauptabschnitt(hauptabschnittMapper.toEntity(hauptabschnittUpdateDTO), hauptabschnittId));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteHauptabschnitt(@PathVariable("id") final String hauptabschnittId) {
        hauptabschnittService.deleteHauptabschnitt(hauptabschnittId);
    }

}
