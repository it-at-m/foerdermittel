package de.muenchen.oss.foerdermittel.backend.bauprogramme;
import de.muenchen.oss.foerdermittel.backend.bauprogramme.dto.BauprogrammeMapper;
import de.muenchen.oss.foerdermittel.backend.bauprogramme.dto.BauprogrammeRequestDTO;
import de.muenchen.oss.foerdermittel.backend.bauprogramme.dto.BauprogrammeResponseDTO;
import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/bauprogramm", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = OpenAPIDocumentationConfiguration.SECURITY_SCHEME_NAME)
public class BauprogrammeController {

    private final BauprogrammeService bauprogrammeService;
    private final BauprogrammeMapper bauprogrammeMapper;

    @GetMapping("{bauprogrammId}")
    @ResponseStatus(HttpStatus.OK)
    public BauprogrammeResponseDTO getBauprogramme(@PathVariable("bauprogrammId") final Integer bauprogrammId) {
        return bauprogrammeMapper.toDTO(bauprogrammeService.getBauprogramm(bauprogrammId));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<BauprogrammeResponseDTO> getBauprogrammeByPageAndSize(@RequestParam(defaultValue = "0") final int pageNumber,
                                                                     @RequestParam(defaultValue = "10") final int pageSize) {
        Page<Bauprogramme> pageWithBauprogramm = bauprogrammeService.getAllBauprogramme(pageNumber, pageSize);
        List<BauprogrammeResponseDTO> bauprogrammResponseDTOList = pageWithBauprogramm.getContent().stream()
                .map(bauprogrammeMapper::toDTO)
                .toList();
        return new PageImpl<>(bauprogrammResponseDTOList, pageWithBauprogramm.getPageable(), pageWithBauprogramm.getTotalElements());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BauprogrammeResponseDTO saveBauprogramm(@Valid @RequestBody final BauprogrammeRequestDTO bauprogrammeRequestDTO) {
        return bauprogrammeMapper.toDTO(bauprogrammeService.createBauprogramm(bauprogrammeMapper.toEntity(bauprogrammeRequestDTO)));
    }

    @PutMapping("/{bauprogrammId}")
    @ResponseStatus(HttpStatus.OK)
    public BauprogrammeResponseDTO updateBauprogramm(@Valid @RequestBody final BauprogrammeRequestDTO bauprogrammeRequestDTO,
                                                    @PathVariable("bauprogrammId") final Integer bauprogrammId) {
        return bauprogrammeMapper.toDTO(bauprogrammeService.updateBauprogramm(bauprogrammeMapper.toEntity(bauprogrammeRequestDTO), bauprogrammId));
    }

    @DeleteMapping("/{bauprogrammId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBauprogramm(@PathVariable("bauprogrammId") final Integer bauprogrammId) {
        bauprogrammeService.deleteBauprogramm(bauprogrammId);
    }
}

