package de.muenchen.oss.foerdermittel.backend.unterabschnitt;

import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.hauptabschnitt.Hauptabschnitt;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.dto.UnterabschnittCreateDTO;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.dto.UnterabschnittMapper;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.dto.UnterabschnittResponseDTO;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.dto.UnterabschnittUpdateDTO;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class UnterabschnittMapperTest {

    private final UnterabschnittMapper unterabschnittMapper = Mappers.getMapper(UnterabschnittMapper.class);

    @Nested
    class ToDTO {

        @Test
        void givenEntity_thenReturnsCorrectDTO() {
            // given
            Unterabschnitt entity = new Unterabschnitt("K", "test", new Hauptabschnitt("H", "test"));

            // when
            UnterabschnittResponseDTO dto = unterabschnittMapper.toDTO(entity);

            // then
            assertThat(dto).isNotNull();
            assertThat(dto.id()).isEqualTo(entity.getUa());
            assertThat(dto.ua()).isEqualTo(entity.getUa());
            assertThat(dto.bezeichnung()).isEqualTo(entity.getBezeichnung());
            assertThat(dto.hasHa()).isEqualTo(entity.getHasHa().getHa());
            assertThat(dto.haBezeichnung()).isEqualTo(entity.getHasHa().getBezeichnung());
        }

    }

    @Nested
    class ToEntity {

        @Test
        void givenCreateDTO_thenReturnsCorrectEntity() {
            // given
            UnterabschnittCreateDTO dto = new UnterabschnittCreateDTO("K", "Test 1", "H");

            // when
            Unterabschnitt entity = unterabschnittMapper.toEntity(dto);

            // then
            assertThat(entity).isNotNull();
            assertThat(entity.getUa()).isEqualTo(dto.ua());
            assertThat(entity.getBezeichnung()).isEqualTo(dto.bezeichnung());
            assertThat(entity.getHasHa().getHa()).isEqualTo(dto.hasHa());
        }

        @Test
        void givenUpdateDTO_thenReturnsCorrectEntity() {
            // given
            UnterabschnittUpdateDTO dto = new UnterabschnittUpdateDTO("Test 2", "H");

            // when
            Unterabschnitt entity = unterabschnittMapper.toEntity(dto);

            // then
            assertThat(entity).isNotNull();
            assertThat(entity.getUa()).isNull();
            assertThat(entity.getBezeichnung()).isEqualTo(dto.bezeichnung());
            assertThat(entity.getHasHa().getHa()).isEqualTo(dto.hasHa());
        }

    }

}
