package de.muenchen.oss.foerdermittel.backend.termin;

import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
import de.muenchen.oss.foerdermittel.backend.termin.dto.TerminCreateDTO;
import de.muenchen.oss.foerdermittel.backend.termin.dto.TerminMapper;
import de.muenchen.oss.foerdermittel.backend.termin.dto.TerminResponseDTO;
import de.muenchen.oss.foerdermittel.backend.termin.dto.TerminUpdateDTO;
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
@RequestMapping(value = "/termine", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = OpenAPIDocumentationConfiguration.SECURITY_SCHEME_NAME)
public class TerminController {

    private final TerminService terminService;
    private final TerminMapper terminMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<TerminResponseDTO> getTermin(@ParameterObject @PageableDefault(sort = { "projekt.projnr", "termin" }) final Pageable pageable) {
        final Page<Termin> pageWithArchiv = terminService.getTermin(pageable);

        final List<TerminResponseDTO> terminResponseDTOList = pageWithArchiv.getContent().stream()
                .map(terminMapper::toDTO)
                .toList();

        return new PageImpl<>(
                terminResponseDTOList,
                pageWithArchiv.getPageable(),
                pageWithArchiv.getTotalElements());
    }

    @GetMapping("/form-context")
    @ResponseStatus(HttpStatus.OK)
    public TerminFormContext getTerminFormContext() {
        return terminService.getTerminFormContext();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TerminResponseDTO createTermin(@Valid @RequestBody final TerminCreateDTO terminCreateDTO) {

        final Termin termin = terminMapper.toEntity(terminCreateDTO);

        return terminMapper.toDTO(terminService.createTermin(termin, terminCreateDTO.projnr()));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TerminResponseDTO updateTermin(
            @Valid @RequestBody final TerminUpdateDTO terminUpdateDTO,
            @PathVariable("id") final String terminID) {

        final Termin termin = terminMapper.toEntity(terminUpdateDTO);

        return terminMapper.toDTO(terminService.updateTermin(termin,
                ControllerUtils.convertStringToLong(terminID)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTermin(@PathVariable("id") final String terminID) {
        terminService.deleteTermin(ControllerUtils.convertStringToLong(terminID));
    }

}
