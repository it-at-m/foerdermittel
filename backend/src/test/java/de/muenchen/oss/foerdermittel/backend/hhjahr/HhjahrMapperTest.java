
package de.muenchen.oss.foerdermittel.backend.hhjahr;

import de.muenchen.oss.foerdermittel.backend.hhjahr.dto.HhjahrCreateDTO;
import de.muenchen.oss.foerdermittel.backend.hhjahr.dto.HhjahrFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.hhjahr.dto.HhjahrMapper;
import de.muenchen.oss.foerdermittel.backend.hhjahr.dto.HhjahrResponseDTO;
import de.muenchen.oss.foerdermittel.backend.hhjahr.dto.HhjahrUpdateDTO;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class HhjahrMapperTest {

    private final HhjahrMapper hhjahrMapper = Mappers.getMapper(HhjahrMapper.class);

    @Nested
    class ToDTO {

        @Test
        void givenEntity_thenReturnsCorrectDTO() {
            // given
            Hhjahr entity = new Hhjahr(
                    BigDecimal.valueOf(2024),
                    "Haushaltsjahr 2024",
                    "Testnotiz",
                    new ArrayList<>()
            );

            // when
            HhjahrResponseDTO dto = hhjahrMapper.toDTO(entity);

            // then
            assertThat(dto).isNotNull();
            assertThat(dto.id()).isEqualTo("2024");
            assertThat(dto.hhjahr()).isEqualTo(entity.getHhjahr());
            assertThat(dto.hhbezeichnung()).isEqualTo(entity.getHhbezeichnung());
            assertThat(dto.notizen()).isEqualTo(entity.getNotizen());
            assertThat(dto.assignedProProjnr()).isEmpty();
        }
    }

    @Nested
    class ToUpdateDTO {

        @Test
        void givenEntity_thenReturnsCorrectDTO() {
            // given
            Hhjahr entity = new Hhjahr(
                    BigDecimal.valueOf(2024),
                    "Haushaltsjahr 2024",
                    "Testnotiz",
                    new ArrayList<>()
            );

            // when
            HhjahrResponseDTO dto = hhjahrMapper.toUpdateDTO(entity);

            // then
            assertThat(dto).isNotNull();
            assertThat(dto.id()).isEqualTo("2024");
            assertThat(dto.hhjahr()).isEqualTo(entity.getHhjahr());
            assertThat(dto.hhbezeichnung()).isEqualTo(entity.getHhbezeichnung());
            assertThat(dto.notizen()).isEqualTo(entity.getNotizen());
            assertThat(dto.assignedProProjnr()).isEmpty();
        }
    }

    @Nested
    class ToEntity {

        @Test
        void givenCreateDTO_thenReturnsCorrectEntity() {
            // given
            HhjahrCreateDTO dto = new HhjahrCreateDTO(
                    BigDecimal.valueOf(2024),
                    "Haushaltsjahr 2024",
                    "Testnotiz"
            );

            // when
            Hhjahr entity = hhjahrMapper.toEntity(dto);

            // then
            assertThat(entity).isNotNull();
            assertThat(entity.getHhjahr()).isEqualTo(dto.hhjahr());
            assertThat(entity.getHhbezeichnung()).isEqualTo(dto.hhbezeichnung());
            assertThat(entity.getNotizen()).isEqualTo(dto.notizen());
            assertThat(entity.getHhplanlisten()).isNotNull().isEmpty();
        }

        @Test
        void givenUpdateDTO_thenReturnsCorrectEntity() {
            // given
            HhjahrUpdateDTO dto = new HhjahrUpdateDTO(
                    "Haushaltsjahr aktualisiert",
                    "Aktualisierte Notiz"
            );

            // when
            Hhjahr entity = hhjahrMapper.toEntity(dto);

            // then
            assertThat(entity).isNotNull();
            assertThat(entity.getHhjahr()).isNull();
            assertThat(entity.getHhbezeichnung()).isEqualTo(dto.hhbezeichnung());
            assertThat(entity.getNotizen()).isEqualTo(dto.notizen());
        }
    }

    @Nested
    class ToFormContextDTO {

        @Test
        void givenEntity_thenReturnsCorrectDTO() {
            // given
            Hhjahr entity = new Hhjahr(
                    BigDecimal.valueOf(2024),
                    "Haushaltsjahr 2024",
                    "Testnotiz",
                    new ArrayList<>()
            );

            // when
            HhjahrFormContextDTO dto = hhjahrMapper.toFormContext(entity);

            // then
            assertThat(dto).isNotNull();
            assertThat(dto.hhjahr()).isEqualTo("2024");
            assertThat(dto.hhbezeichnung()).isEqualTo(entity.getHhbezeichnung());
            assertThat(dto.notizen()).isEqualTo(entity.getNotizen());
        }

        @Test
        void givenList_thenReturnsCorrectDTO() {
            // given
            Hhjahr entity1 = new Hhjahr(
                    BigDecimal.valueOf(2024),
                    "Haushaltsjahr 2024",
                    "Notiz 1",
                    new ArrayList<>()
            );

            Hhjahr entity2 = new Hhjahr(
                    BigDecimal.valueOf(2025),
                    "Haushaltsjahr 2025",
                    "Notiz 2",
                    new ArrayList<>()
            );

            List<Hhjahr> list = List.of(entity1, entity2);

            // when
            List<HhjahrFormContextDTO> dto = hhjahrMapper.toFormContext(list);

            // then
            assertThat(dto).hasSize(2);
            assertThat(dto)
                    .extracting(
                            HhjahrFormContextDTO::hhjahr,
                            HhjahrFormContextDTO::hhbezeichnung,
                            HhjahrFormContextDTO::notizen
                    )
                    .containsExactly(
                            tuple("2024", "Haushaltsjahr 2024", "Notiz 1"),
                            tuple("2025", "Haushaltsjahr 2025", "Notiz 2")
                    );
        }
    }
}
