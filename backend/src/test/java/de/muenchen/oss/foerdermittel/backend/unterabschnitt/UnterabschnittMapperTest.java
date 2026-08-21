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
            // Given
            final Hauptabschnitt hauptabschnitt = new Hauptabschnitt("H", "test");
            final Unterabschnitt entity = new Unterabschnitt("K", "test", hauptabschnitt);

            // When
            final UnterabschnittResponseDTO dto = unterabschnittMapper.toDTO(entity);

            // Then
            assertThat(dto).isNotNull();
            assertThat(dto.id()).isEqualTo(entity.getUa());
            assertThat(dto.ua()).isEqualTo(entity.getUa());
            assertThat(dto.bezeichnung()).isEqualTo(entity.getBezeichnung());
            assertThat(dto.hasHa()).isEqualTo(hauptabschnitt.getHa());
            assertThat(dto.haBezeichnung()).isEqualTo(hauptabschnitt.getBezeichnung());
        }
    }

    @Nested
    class ToEntity {

        @Test
        void givenCreateDTO_thenReturnsCorrectEntity() {
            // Given
            final UnterabschnittCreateDTO dto = new UnterabschnittCreateDTO("K", "Test 1", "H");

            // When
            final Unterabschnitt entity = unterabschnittMapper.toEntity(dto);

            // Then
            assertThat(entity).isNotNull();
            assertThat(entity.getUa()).isEqualTo(dto.ua());
            assertThat(entity.getBezeichnung()).isEqualTo(dto.bezeichnung());
            assertThat(entity.getHasHa()).isNull();
        }

        @Test
        void givenUpdateDTO_thenReturnsCorrectEntity() {
            // Given
            final UnterabschnittUpdateDTO dto = new UnterabschnittUpdateDTO("Test 2", "H");

            // When
            final Unterabschnitt entity = unterabschnittMapper.toEntity(dto);

            // Then
            assertThat(entity).isNotNull();
            assertThat(entity.getUa()).isNull();
            assertThat(entity.getBezeichnung()).isEqualTo(dto.bezeichnung());
            assertThat(entity.getHasHa()).isNull();
        }
    }
}
