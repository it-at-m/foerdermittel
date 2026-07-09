package de.muenchen.oss.foerdermittel.backend.benutzerhinweis;

import de.muenchen.oss.foerdermittel.backend.benutzerhinweis.dto.BenutzerhinweisCreateDTO;
import de.muenchen.oss.foerdermittel.backend.benutzerhinweis.dto.BenutzerhinweisMapper;
import de.muenchen.oss.foerdermittel.backend.benutzerhinweis.dto.BenutzerhinweisResponseDTO;
import de.muenchen.oss.foerdermittel.backend.benutzerhinweis.dto.BenutzerhinweisUpdateDTO;
import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping(value = "/benutzerhinweise", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = OpenAPIDocumentationConfiguration.SECURITY_SCHEME_NAME)
public class BenutzerhinweisController {

    private final BenutzerhinweisService benutzerhinweisService;
    private final BenutzerhinweisMapper benutzerhinweisMapper;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BenutzerhinweisResponseDTO getBenutzerhinweis(@PathVariable("id") final String viewId) {
        return benutzerhinweisMapper.toDTO(benutzerhinweisService.getBenutzerhinweis(viewId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BenutzerhinweisResponseDTO createBenutzerhinweis(@Valid @RequestBody final BenutzerhinweisCreateDTO benutzerhinweisCreateDTO) {
        return benutzerhinweisMapper.toDTO(benutzerhinweisService.createBenutzerhinweis(benutzerhinweisMapper.toEntity(benutzerhinweisCreateDTO)));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BenutzerhinweisResponseDTO updateBenutzerhinweis(@Valid @RequestBody final BenutzerhinweisUpdateDTO benutzerhinweisUpdateDTO,
            @PathVariable("id") final String viewId) {
        return benutzerhinweisMapper
                .toDTO(benutzerhinweisService.updateBenutzerhinweis(benutzerhinweisMapper.toEntity(benutzerhinweisUpdateDTO), viewId));
    }

}
