package de.muenchen.oss.foerdermittel.backend.listenname;

import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
import de.muenchen.oss.foerdermittel.backend.listenname.dto.ListennameCreateDTO;
import de.muenchen.oss.foerdermittel.backend.listenname.dto.ListennameMapper;
import de.muenchen.oss.foerdermittel.backend.listenname.dto.ListennameResponseDTO;
import de.muenchen.oss.foerdermittel.backend.listenname.dto.ListennameUpdateDTO;
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
@RequestMapping(value = "/listennamen", produces = MediaType.APPLICATION_JSON_VALUE)
@SecurityRequirement(name = OpenAPIDocumentationConfiguration.SECURITY_SCHEME_NAME)
public class ListennameController {

    private final ListennameService listennameService;
    private final ListennameMapper listennameMapper;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ListennameResponseDTO getListenname(@PathVariable("id") final String listennameId) {
        return listennameMapper.toDTO(listennameService.getListenname(listennameId));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ListennameResponseDTO> getListennamenByPageable(@ParameterObject @PageableDefault(
            sort = "kurzbez"
    ) final Pageable pageable) {
        final Page<Listenname> pageWithListenname = listennameService.getAllListennamen(pageable);
        final List<ListennameResponseDTO> listennameResponseDTOList = pageWithListenname.getContent().stream()
                .map(listennameMapper::toDTO)
                .toList();
        return new PageImpl<>(listennameResponseDTOList, pageWithListenname.getPageable(), pageWithListenname.getTotalElements());
    }

    @GetMapping("/form-context")
    @ResponseStatus(HttpStatus.OK)
    public ListennameFormContext getListennameFormContext() {
        return listennameService.getListennameFormContext();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ListennameResponseDTO createListenname(@Valid @RequestBody final ListennameCreateDTO listennameCreateDTO) {
        return listennameMapper.toDTO(listennameService.createListenname(listennameMapper.toEntity(listennameCreateDTO)));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ListennameResponseDTO updateListenname(@Valid @RequestBody final ListennameUpdateDTO listennameUpdateDTO,
                                                    @PathVariable("id") final String listennameId) {
        return listennameMapper
                .toDTO(listennameService.updateListenname(listennameMapper.toEntity(listennameUpdateDTO), listennameId));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteListenname(@PathVariable("id") final String listennameId) {
        listennameService.deleteListenname(listennameId);
    }

}
