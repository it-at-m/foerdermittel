package de.muenchen.oss.foerdermittel.backend.archiv;

import de.muenchen.oss.foerdermittel.backend.archiv.dto.ArchivCreateDTO;
import de.muenchen.oss.foerdermittel.backend.archiv.dto.ArchivMapper;
import de.muenchen.oss.foerdermittel.backend.archiv.dto.ArchivResponseDTO;
import de.muenchen.oss.foerdermittel.backend.archiv.dto.ArchivUpdateDTO;
import de.muenchen.oss.foerdermittel.backend.bauleitung.Bauleitung;
import de.muenchen.oss.foerdermittel.backend.bauleitung.BauleitungRepository;
import de.muenchen.oss.foerdermittel.backend.bauleitung.dto.BauleitungResponseDTO;
import de.muenchen.oss.foerdermittel.backend.bauleitung.dto.BauleitungUpdateDTO;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.BauprogrammFormContext;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammCreateDTO;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammResponseDTO;
import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/archiv", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = OpenAPIDocumentationConfiguration.SECURITY_SCHEME_NAME)
public class ArchivController {

    final private ArchivService archivService;
    final private ArchivMapper archivMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ArchivResponseDTO> getArchiveintrag( @ParameterObject @PageableDefault (sort = "speicherDatum"
    ) final Pageable pageable) {
        final Page<Archiv> pageWithArchiv = archivService.getArchiveintraege(pageable);
        final List<ArchivResponseDTO> archivResponseDTOList = pageWithArchiv.getContent().stream()
                .map(archivMapper::toDTO)
                .toList();
        return new PageImpl<>(archivResponseDTOList, pageWithArchiv.getPageable(), pageWithArchiv.getTotalElements());
    }

    @GetMapping("/form-context")
    @ResponseStatus(HttpStatus.OK)
    public ArchivFormContext getArchivFormContext() {
        return archivService.getArchivFormContext();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArchivResponseDTO createArchive(@Valid @RequestBody final ArchivCreateDTO archivCreateDTO) {
        return archivMapper.toDTO(archivService.createArchiv(archivMapper.toEntity(archivCreateDTO)));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ArchivResponseDTO updateArchivNotiz(@Valid @RequestBody final ArchivUpdateDTO archivUpdateDTO,
                                                  @PathVariable("id") final Long archivId) {
        return archivMapper
                .toDTO(archivService.updateArchiv(archivMapper.toEntity(archivUpdateDTO), archivId));
    }

}
