package de.muenchen.oss.foerdermittel.backend.hauptabschnitt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import de.muenchen.oss.foerdermittel.backend.hauptabschnitt.dto.HauptabschnittCreateDTO;
import de.muenchen.oss.foerdermittel.backend.hauptabschnitt.dto.HauptabschnittFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.hauptabschnitt.dto.HauptabschnittMapper;
import de.muenchen.oss.foerdermittel.backend.hauptabschnitt.dto.HauptabschnittResponseDTO;
import de.muenchen.oss.foerdermittel.backend.hauptabschnitt.dto.HauptabschnittUpdateDTO;
import java.util.List;
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

    @Nested
    class ToFormContextDTO {

        @Test
        void givenEntity_thenReturnsCorrectDTO() {
            // given
            final Hauptabschnitt entity = new Hauptabschnitt("K", "test");

            // when
            final HauptabschnittFormContextDTO dto = hauptabschnittMapper.toFormContext(entity);

            // then
            assertThat(dto).isNotNull();
            assertThat(dto.ha()).isEqualTo(entity.getHa());
            assertThat(dto.bezeichnung()).isEqualTo(entity.getBezeichnung());
        }

        @Test
        void givenList_thenReturnsCorrectDTO() {
            // given
            final Hauptabschnitt entity1 = new Hauptabschnitt("K", "test");
            final Hauptabschnitt entity2 = new Hauptabschnitt("L", "test 2");
            final List<Hauptabschnitt> list = List.of(entity1, entity2);

            // when
            final List<HauptabschnittFormContextDTO> dto = hauptabschnittMapper.toFormContext(list);

            // then
            assertThat(dto).isNotNull();
            assertThat(dto).hasSize(list.size());
            assertThat(dto)
                    .extracting(
                            HauptabschnittFormContextDTO::ha,
                            HauptabschnittFormContextDTO::bezeichnung)
                    .containsExactly(
                            tuple("K", "test"),
                            tuple("L", "test 2"));
        }

    }

}
