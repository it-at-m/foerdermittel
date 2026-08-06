package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste;

import de.muenchen.oss.foerdermittel.backend.stadtbezirk.Stadtbezirk;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.ListennameStadtbezirkslisteAssignmentMapper;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.StadtbezirkslisteAssignmentResponseDTO;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ListennameStadtbezirkslisteAssignmentMapperTest {

private final ListennameStadtbezirkslisteAssignmentMapper mapper =
        Mappers.getMapper(ListennameStadtbezirkslisteAssignmentMapper.class);

@Nested
class ToDTO {

    @Test
    void givenEntity_thenReturnsCorrectDTO() {
        // given
        Stadtbezirk stadtbezirk = new Stadtbezirk(BigDecimal.ONE, "Altstadt");

        Stadtbezirksliste entity = new Stadtbezirksliste();
        entity.setId(new StadtbezirkslistePrimaryKey("SL1", BigDecimal.ONE));
        entity.setStadtbezirk(stadtbezirk);
        entity.setBezeichnung("Test");

        // when
        StadtbezirkslisteAssignmentResponseDTO dto = mapper.toDTO(entity);

        // then
        assertThat(dto).isNotNull();
        assertThat(dto.stadtbezirkId()).isEqualTo(BigDecimal.ONE);
        assertThat(dto.stadtbezirkBezeichnung()).isEqualTo("Altstadt");
        assertThat(dto.bezeichnung()).isEqualTo("Test");
    }
}

@Nested
class ToEntity {

    @Test
    void givenDTO_thenReturnsCorrectEntity() {
        // given
        StadtbezirkslisteAssignmentResponseDTO dto =
                new StadtbezirkslisteAssignmentResponseDTO(
                        BigDecimal.ONE,
                        "Altstadt",
                        "Test");

        // when
        Stadtbezirksliste entity = mapper.toEntity(dto);

        // then
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isNull();
        assertThat(entity.getListenName()).isNull();
        assertThat(entity.getStadtbezirk().getStadtbezirk())
                .isEqualTo(BigDecimal.ONE);
        assertThat(entity.getBezeichnung()).isEqualTo("Test");
    }
}
}