package de.muenchen.oss.foerdermittel.backend.archiv;

import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.archiv.dto.ArchivCreateDTO;
import de.muenchen.oss.foerdermittel.backend.archiv.dto.ArchivMapper;
import de.muenchen.oss.foerdermittel.backend.archiv.dto.ArchivResponseDTO;
import de.muenchen.oss.foerdermittel.backend.archiv.dto.ArchivUpdateDTO;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.Foerderbereich;
import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class ArchivMapperTest {

    private final ArchivMapper archivMapper = Mappers.getMapper(ArchivMapper.class);

    @Nested
    class ToDTO {

        @Test
        void givenEntity_thenReturnsCorrectDTO() {

            final Foerderbereich foerderbereich = new Foerderbereich();
            foerderbereich.setFb(BigDecimal.valueOf(123));

            final Projekt projekt = new Projekt();
            projekt.setProjnr("1124101");
            projekt.setPname("Test Projekt");
            projekt.setPstrasse("Teststraße 1");
            projekt.setFoerderbereich(foerderbereich);

            final Archiv entity = new Archiv(
                    1L,
                    projekt,
                    LocalDate.of(2024, 9, 14),
                    true,
                    true,
                    LocalDate.of(2024, 9, 16),
                    LocalDate.of(2024, 9, 18),
                    "Test");

            final ArchivResponseDTO dto = archivMapper.toDTO(entity);

            assertThat(dto).isNotNull();
            assertThat(dto.id()).isEqualTo(entity.getId());
            assertThat(dto.speicherDatum()).isEqualTo(entity.getSpeicherDatum());
            assertThat(dto.speicherAkt()).isEqualTo(entity.getSpeicherAkt());
            assertThat(dto.speicherRechnungen()).isEqualTo(entity.getSpeicherRechnungen());
            assertThat(dto.mikroDatPlan()).isEqualTo(entity.getMikroDatPlan());
            assertThat(dto.mikroDat()).isEqualTo(entity.getMikroDat());
            assertThat(dto.notizen()).isEqualTo(entity.getNotizen());
            assertThat(dto.projnr()).isEqualTo(entity.getProjekt().getProjnr());
            assertThat(dto.pname()).isEqualTo(entity.getProjekt().getPname());
            assertThat(dto.pstrasse()).isEqualTo(entity.getProjekt().getPstrasse());
            assertThat(dto.fob_fb()).isEqualTo(entity.getProjekt().getFoerderbereich().getFb());
        }
    }

    @Nested
    class ToEntity {

        @Test
        void givenCreateDTO_thenReturnsCorrectEntity() {
            // given
            final ArchivCreateDTO dto = new ArchivCreateDTO(
                    LocalDate.of(2024, 9, 14),
                    true,
                    true,
                    LocalDate.of(2024, 9, 16),
                    LocalDate.of(2024, 9, 18),
                    "Test 1",
                    "1124101");

            // when
            final Archiv entity = archivMapper.toEntity(dto);

            // then
            assertThat(entity).isNotNull();

            assertThat(entity.getId()).isNull();

            assertThat(entity.getSpeicherDatum()).isEqualTo(dto.speicherDatum());
            assertThat(entity.getSpeicherAkt()).isEqualTo(dto.speicherAkt());
            assertThat(entity.getSpeicherRechnungen()).isEqualTo(dto.speicherRechnungen());
            assertThat(entity.getMikroDatPlan()).isEqualTo(dto.mikroDatPlan());
            assertThat(entity.getMikroDat()).isEqualTo(dto.mikroDat());
            assertThat(entity.getNotizen()).isEqualTo(dto.notizen());

            assertThat(entity.getProjekt()).isNotNull();
            assertThat(entity.getProjekt().getProjnr()).isEqualTo(dto.projnr());
        }

        @Test
        void givenUpdateDTO_thenReturnsCorrectEntity() {
            // given
            final ArchivUpdateDTO dto = new ArchivUpdateDTO(
                    LocalDate.of(2024, 9, 14),
                    true,
                    true,
                    LocalDate.of(2024, 9, 16),
                    LocalDate.of(2024, 9, 18),
                    "Test 2");

            // when
            final Archiv entity = archivMapper.toEntity(dto);

            // then
            assertThat(entity).isNotNull();

            assertThat(entity.getId()).isNull();
            assertThat(entity.getProjekt()).isNull();

            assertThat(entity.getSpeicherDatum()).isEqualTo(dto.speicherDatum());
            assertThat(entity.getSpeicherAkt()).isEqualTo(dto.speicherAkt());
            assertThat(entity.getSpeicherRechnungen()).isEqualTo(dto.speicherRechnungen());
            assertThat(entity.getMikroDatPlan()).isEqualTo(dto.mikroDatPlan());
            assertThat(entity.getMikroDat()).isEqualTo(dto.mikroDat());
            assertThat(entity.getNotizen()).isEqualTo(dto.notizen());
        }
    }
}
