package de.muenchen.oss.foerdermittel.backend.stadtbezirk;

import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto.StadtbezirkCreateDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto.StadtbezirkMapper;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto.StadtbezirkResponseDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto.StadtbezirkUpdateDTO;
import java.math.BigDecimal;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class StadtbezirkMapperTest {

    private final StadtbezirkMapper stadtbezirkMapper = Mappers.getMapper(StadtbezirkMapper.class);

    @Nested
    class ToDTO {

        @Test
        void givenEntity_thenReturnsCorrectDTO() {
            // given
            Stadtbezirk entity = new Stadtbezirk(BigDecimal.valueOf(1), "test");

            // when
            StadtbezirkResponseDTO dto = stadtbezirkMapper.toDTO(entity);

            // then
            assertThat(dto).isNotNull();
            assertThat(dto.id()).isEqualTo(entity.getStadtbezirk().toString());
            assertThat(dto.stadtbezirk()).isEqualTo(entity.getStadtbezirk().intValueExact());
            assertThat(dto.bezeichnung()).isEqualTo(entity.getBezeichnung());
        }

    }

    @Nested
    class ToEntity {

        @Test
        void givenCreateDTO_thenReturnsCorrectEntity() {
            // given
            StadtbezirkCreateDTO dto = new StadtbezirkCreateDTO(1, "Test 1");

            // when
            Stadtbezirk entity = stadtbezirkMapper.toEntity(dto);

            // then
            assertThat(entity).isNotNull();
            assertThat(entity.getStadtbezirk()).isEqualTo(BigDecimal.valueOf(dto.stadtbezirk()));
            assertThat(entity.getBezeichnung()).isEqualTo(dto.bezeichnung());
        }

        @Test
        void givenUpdateDTO_thenReturnsCorrectEntity() {
            // given
            StadtbezirkUpdateDTO dto = new StadtbezirkUpdateDTO("Test 2");

            // when
            Stadtbezirk entity = stadtbezirkMapper.toEntity(dto);

            // then
            assertThat(entity).isNotNull();
            assertThat(entity.getStadtbezirk()).isNull();
            assertThat(entity.getBezeichnung()).isEqualTo(dto.bezeichnung());
        }

    }

}
