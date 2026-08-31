package de.muenchen.oss.foerdermittel.backend.archiv;

import de.muenchen.oss.foerdermittel.backend.archiv.dto.ArchivCreateDTO;
import de.muenchen.oss.foerdermittel.backend.archiv.dto.ArchivMapper;
import de.muenchen.oss.foerdermittel.backend.archiv.dto.ArchivResponseDTO;
import de.muenchen.oss.foerdermittel.backend.archiv.dto.ArchivUpdateDTO;
import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
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
@RequestMapping(value = "/archiv", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = OpenAPIDocumentationConfiguration.SECURITY_SCHEME_NAME)
public class ArchivController {

    private final ArchivService archivService;
    private final ArchivMapper archivMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ArchivResponseDTO> getArchiveintraege(
            @ParameterObject @PageableDefault(sort = { "projekt.projnr", "speicherDatum" }) final Pageable pageable) {

        final Page<Archiv> pageWithArchiv = archivService.getArchiveintraege(pageable);

        final List<ArchivResponseDTO> archivResponseDTOList = pageWithArchiv.getContent().stream()
                .map(archivMapper::toDTO)
                .toList();

        return new PageImpl<>(
                archivResponseDTOList,
                pageWithArchiv.getPageable(),
                pageWithArchiv.getTotalElements());
    }

    @GetMapping("/form-context")
    @ResponseStatus(HttpStatus.OK)
    public ArchivFormContext getArchivFormContext() {
        return archivService.getArchivFormContext();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArchivResponseDTO createArchiv(@Valid @RequestBody final ArchivCreateDTO archivCreateDTO) {

        final Archiv archiv = archivMapper.toEntity(archivCreateDTO);

        return archivMapper.toDTO(archivService.createArchiv(archiv, archivCreateDTO.projnr()));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ArchivResponseDTO updateArchiv(
            @Valid @RequestBody final ArchivUpdateDTO archivUpdateDTO,
            @PathVariable("id") final Long archivId) {

        final Archiv archiv = archivMapper.toEntity(archivUpdateDTO);

        return archivMapper.toDTO(archivService.updateArchiv(archiv,
                        archivId));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteArchiv(@PathVariable("id") final Long archivID) {
        archivService.deleteArchiv(archivID);
    }

}
