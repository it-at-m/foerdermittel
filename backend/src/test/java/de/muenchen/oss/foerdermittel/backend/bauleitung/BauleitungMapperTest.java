package de.muenchen.oss.foerdermittel.backend.bauleitung;

import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.bauleitung.dto.BauleitungCreateDTO;
import de.muenchen.oss.foerdermittel.backend.bauleitung.dto.BauleitungMapper;
import de.muenchen.oss.foerdermittel.backend.bauleitung.dto.BauleitungResponseDTO;
import de.muenchen.oss.foerdermittel.backend.bauleitung.dto.BauleitungUpdateDTO;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class BauleitungMapperTest {

    private final BauleitungMapper bauleitungMapper = Mappers.getMapper(BauleitungMapper.class);

    @Nested
    class ToDTO {

        @Test
        void givenEntity_thenReturnsCorrectDTO() {
            // given
            Bauleitung entity = new Bauleitung("1", "test");

            // when
            BauleitungResponseDTO dto = bauleitungMapper.toDTO(entity);

            // then
            assertThat(dto).isNotNull();
            assertThat(dto.id()).isEqualTo(entity.getBauleitung());
            assertThat(dto.bauleitung()).isEqualTo(entity.getBauleitung());
            assertThat(dto.bezeichnung()).isEqualTo(entity.getBezeichnung());
        }

    }

    @Nested
    class ToEntity {

        @Test
        void givenCreateDTO_thenReturnsCorrectEntity() {
            // given
            BauleitungCreateDTO dto = new BauleitungCreateDTO("1", "Test 1");

            // when
            Bauleitung entity = bauleitungMapper.toEntity(dto);

            // then
            assertThat(entity).isNotNull();
            assertThat(entity.getBauleitung()).isEqualTo(dto.bauleitung());
            assertThat(entity.getBezeichnung()).isEqualTo(dto.bezeichnung());
        }

        @Test
        void givenUpdateDTO_thenReturnsCorrectEntity() {
            // given
            BauleitungUpdateDTO dto = new BauleitungUpdateDTO("Test 2");

            // when
            Bauleitung entity = bauleitungMapper.toEntity(dto);

            // then
            assertThat(entity).isNotNull();
            assertThat(entity.getBauleitung()).isNull();
            assertThat(entity.getBezeichnung()).isEqualTo(dto.bezeichnung());
        }

    }

}
