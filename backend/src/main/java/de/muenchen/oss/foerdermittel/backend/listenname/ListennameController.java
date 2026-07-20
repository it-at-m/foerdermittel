package de.muenchen.oss.foerdermittel.backend.listenname;

import de.muenchen.oss.foerdermittel.backend.configuration.OpenAPIDocumentationConfiguration;
import de.muenchen.oss.foerdermittel.backend.listenname.dto.ListennameCreateDTO;
import de.muenchen.oss.foerdermittel.backend.listenname.dto.ListennameMapper;
import de.muenchen.oss.foerdermittel.backend.listenname.dto.ListennameResponseDTO;
import de.muenchen.oss.foerdermittel.backend.listenname.dto.ListennameUpdateDTO;
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
@RequestMapping(
        value = "/listennamen",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@SecurityRequirement(name = OpenAPIDocumentationConfiguration.SECURITY_SCHEME_NAME)
public class ListennameController {


    private final ListennameService listennameService;
    private final ListennameMapper listennameMapper;


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ListennameResponseDTO getListenname(
            @PathVariable("id") final String kurzbez) {

        return listennameMapper.toDTO(
                listennameService.getListenname(kurzbez)
        );
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ListennameResponseDTO> getListennamen(
            @ParameterObject
            @PageableDefault(sort = "kurzbez")
            final Pageable pageable) {


        Page<Listenname> page =
                listennameService.getAllListennamen(pageable);


        List<ListennameResponseDTO> dto =
                page.getContent()
                        .stream()
                        .map(listennameMapper::toDTO)
                        .toList();


        return new PageImpl<>(
                dto,
                page.getPageable(),
                page.getTotalElements()
        );
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ListennameResponseDTO createListenname(
            @Valid
            @RequestBody final ListennameCreateDTO dto) {


        return listennameMapper.toDTO(
                listennameService.createListenname(
                        listennameMapper.toEntity(dto)
                )
        );
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ListennameResponseDTO updateListenname(
            @PathVariable("id") final String kurzbez,
            @Valid
            @RequestBody final ListennameUpdateDTO dto) {


        return listennameMapper.toDTO(
                listennameService.updateListenname(
                        listennameMapper.toEntity(dto),
                        kurzbez
                )
        );
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteListenname(
            @PathVariable("id") final String kurzbez) {

        listennameService.deleteListenname(kurzbez);
    }

}