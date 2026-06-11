package de.muenchen.oss.foerdermittel.backend.bauprogramm;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammMapper;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammRequestDTO;
import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammResponseDTO;
import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api/backend/bauprogramm", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = OpenAPIDocumentationConfiguration.SECURITY_SCHEME_NAME)
public class BauprogrammController {

    private final BauprogrammService bauprogrammService;
    private final BauprogrammMapper bauprogrammMapper;

    @GetMapping("{bauprogrammId}")
    @ResponseStatus(HttpStatus.OK)
    public BauprogrammResponseDTO getBauprogramme(@PathVariable("bauprogrammId") final Integer bauprogrammId) {
        return bauprogrammMapper.toDTO(bauprogrammService.getBauprogramm(bauprogrammId));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<BauprogrammResponseDTO> getBauprogrammeByPageAndSize(@RequestParam(defaultValue = "0") final int pageNumber,
                                                                     @RequestParam(defaultValue = "10") final int pageSize) {
        Page<Bauprogramm> pageWithBauprogramm = bauprogrammService.getAllBauprogramme(pageNumber, pageSize);
        List<BauprogrammResponseDTO> bauprogrammResponseDTOList = pageWithBauprogramm.getContent().stream()
                .map(bauprogrammMapper::toDTO)
                .toList();
        return new PageImpl<>(bauprogrammResponseDTOList, pageWithBauprogramm.getPageable(), pageWithBauprogramm.getTotalElements());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BauprogrammResponseDTO saveBauprogramm(@Valid @RequestBody final BauprogrammRequestDTO bauprogrammRequestDTO) {
        return bauprogrammMapper.toDTO(bauprogrammService.createBauprogramm(bauprogrammMapper.toEntity(bauprogrammRequestDTO)));
    }

    @PutMapping("/{bauprogrammId}")
    @ResponseStatus(HttpStatus.OK)
    public BauprogrammResponseDTO updateBauprogramm(@Valid @RequestBody final BauprogrammRequestDTO bauprogrammRequestDTO,
                                                    @PathVariable("bauprogrammId") final Integer bauprogrammId) {
        return bauprogrammMapper.toDTO(bauprogrammService.updateBauprogramm(bauprogrammMapper.toEntity(bauprogrammRequestDTO), bauprogrammId));
    }

    @DeleteMapping("/{bauprogrammId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBauprogramm(@PathVariable("bauprogrammId") final Integer bauprogrammId) {
        bauprogrammService.deleteBauprogramm(bauprogrammId);
    }
}

