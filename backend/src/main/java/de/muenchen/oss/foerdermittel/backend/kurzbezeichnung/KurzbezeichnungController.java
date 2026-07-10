package de.muenchen.oss.foerdermittel.backend.kurzbezeichnung;

import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto.KurzbezeichnungCreateDTO;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto.KurzbezeichnungMapper;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto.KurzbezeichnungResponseDTO;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto.KurzbezeichnungUpdateDTO;
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
@RequestMapping(value = "/kurzbezeichnungen", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = OpenAPIDocumentationConfiguration.SECURITY_SCHEME_NAME)
public class KurzbezeichnungController {

    private final KurzbezeichnungService kurzbezeichnungService;
    private final KurzbezeichnungMapper kurzbezeichnungMapper;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public KurzbezeichnungResponseDTO getKurzbezeichnung(@PathVariable("id") final String kurzbezeichnungId) {
        return kurzbezeichnungMapper.toDTO(kurzbezeichnungService.getKurzbezeichnung(kurzbezeichnungId));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<KurzbezeichnungResponseDTO> getKurzbezeichnungenByPageable(@ParameterObject @PageableDefault(
            sort = "kurzbez"
    ) final Pageable pageable) {
        final Page<Kurzbezeichnung> pageWithKurzbezeichnung = kurzbezeichnungService.getAllKurzbezeichnungen(pageable);
        final List<KurzbezeichnungResponseDTO> kurzbezeichnungResponseDTOList = pageWithKurzbezeichnung.getContent().stream()
                .map(kurzbezeichnungMapper::toDTO)
                .toList();
        return new PageImpl<>(kurzbezeichnungResponseDTOList, pageWithKurzbezeichnung.getPageable(), pageWithKurzbezeichnung.getTotalElements());
    }

    @GetMapping("/form-context")
    @ResponseStatus(HttpStatus.OK)
    public KurzbezeichnungFormContext getKurzbezeichnungFormContext() {
        return kurzbezeichnungService.getKurzbezeichnungFormContext();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KurzbezeichnungResponseDTO createKurzbezeichnung(@Valid @RequestBody final KurzbezeichnungCreateDTO kurzbezeichnungCreateDTO) {
        return kurzbezeichnungMapper.toDTO(kurzbezeichnungService.createKurzbezeichnung(kurzbezeichnungMapper.toEntity(kurzbezeichnungCreateDTO)));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public KurzbezeichnungResponseDTO updateKurzbezeichnung(@Valid @RequestBody final KurzbezeichnungUpdateDTO kurzbezeichnungUpdateDTO,
            @PathVariable("id") final String kurzbezeichnungId) {
        return kurzbezeichnungMapper
                .toDTO(kurzbezeichnungService.updateKurzbezeichnung(kurzbezeichnungMapper.toEntity(kurzbezeichnungUpdateDTO), kurzbezeichnungId));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteKurzbezeichnung(@PathVariable("id") final String kurzbezeichnungId) {
        kurzbezeichnungService.deleteKurzbezeichnung(kurzbezeichnungId);
    }

}
