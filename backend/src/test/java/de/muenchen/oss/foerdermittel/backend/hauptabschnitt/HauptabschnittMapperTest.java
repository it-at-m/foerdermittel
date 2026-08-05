package de.muenchen.oss.foerdermittel.backend.hauptabschnitt;

import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.hauptabschnitt.dto.HauptabschnittCreateDTO;
import de.muenchen.oss.foerdermittel.backend.hauptabschnitt.dto.HauptabschnittMapper;
import de.muenchen.oss.foerdermittel.backend.hauptabschnitt.dto.HauptabschnittResponseDTO;
import de.muenchen.oss.foerdermittel.backend.hauptabschnitt.dto.HauptabschnittUpdateDTO;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class HauptabschnittMapperTest {

    private final HauptabschnittMapper hauptabschnittMapper = Mappers.getMapper(HauptabschnittMapper.class);

    @Nested
    class ToDTO {

        @Test
        void givenEntity_thenReturnsCorrectDTO() {
            // given
            Hauptabschnitt entity = new Hauptabschnitt("K", "test");

            // when
            HauptabschnittResponseDTO dto = hauptabschnittMapper.toDTO(entity);

            // then
            assertThat(dto).isNotNull();
            assertThat(dto.id()).isEqualTo(entity.getHa());
            assertThat(dto.ha()).isEqualTo(entity.getHa());
            assertThat(dto.bezeichnung()).isEqualTo(entity.getBezeichnung());
        }

    }

    @Nested
    class ToEntity {

        @Test
        void givenCreateDTO_thenReturnsCorrectEntity() {
            // given
            HauptabschnittCreateDTO dto = new HauptabschnittCreateDTO("K", "Test 1");

            // when
            Hauptabschnitt entity = hauptabschnittMapper.toEntity(dto);

            // then
            assertThat(entity).isNotNull();
            assertThat(entity.getHa()).isEqualTo(dto.ha());
            assertThat(entity.getBezeichnung()).isEqualTo(dto.bezeichnung());
        }

        @Test
        void givenUpdateDTO_thenReturnsCorrectEntity() {
            // given
            HauptabschnittUpdateDTO dto = new HauptabschnittUpdateDTO("Test 2");

            // when
            Hauptabschnitt entity = hauptabschnittMapper.toEntity(dto);

            // then
            assertThat(entity).isNotNull();
            assertThat(entity.getHa()).isNull();
            assertThat(entity.getBezeichnung()).isEqualTo(dto.bezeichnung());
        }

    }

}
