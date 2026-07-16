package de.muenchen.oss.foerdermittel.backend.stichwortbereich;

import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.stichwortbereich.dto.StichwortbereichCreateDTO;
import de.muenchen.oss.foerdermittel.backend.stichwortbereich.dto.StichwortbereichMapper;
import de.muenchen.oss.foerdermittel.backend.stichwortbereich.dto.StichwortbereichResponseDTO;
import de.muenchen.oss.foerdermittel.backend.stichwortbereich.dto.StichwortbereichUpdateDTO;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class StichwortbereichMapperTest {

    private final StichwortbereichMapper stichwortbereichMapper = Mappers.getMapper(StichwortbereichMapper.class);

    @Nested
    class ToDTO {

        @Test
        void givenEntity_thenReturnsCorrectDTO() {
            // given
            Stichwortbereich entity = new Stichwortbereich("STICHWORTBEREICH-1", "test");

            // when
            StichwortbereichResponseDTO dto = stichwortbereichMapper.toDTO(entity);

            // then
            assertThat(dto).isNotNull();
            assertThat(dto.id()).isEqualTo(entity.getBereich());
            assertThat(dto.bereich()).isEqualTo(entity.getBereich());
            assertThat(dto.bezeichnung()).isEqualTo(entity.getBezeichnung());
        }

    }

    @Nested
    class ToEntity {

        @Test
        void givenCreateDTO_thenReturnsCorrectEntity() {
            // given
            StichwortbereichCreateDTO dto = new StichwortbereichCreateDTO("STICHWORTBEREICH-1", "Test 1");

            // when
            Stichwortbereich entity = stichwortbereichMapper.toEntity(dto);

            // then
            assertThat(entity).isNotNull();
            assertThat(entity.getBereich()).isEqualTo(dto.bereich());
            assertThat(entity.getBezeichnung()).isEqualTo(dto.bezeichnung());
        }

        @Test
        void givenUpdateDTO_thenReturnsCorrectEntity() {
            // given
            StichwortbereichUpdateDTO dto = new StichwortbereichUpdateDTO("Test 2");

            // when
            Stichwortbereich entity = stichwortbereichMapper.toEntity(dto);

            // then
            assertThat(entity).isNotNull();
            assertThat(entity.getBereich()).isNull();
            assertThat(entity.getBezeichnung()).isEqualTo(dto.bezeichnung());
        }

    }
}
