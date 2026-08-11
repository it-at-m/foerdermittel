package de.muenchen.oss.foerdermittel.backend.archiv;

import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.oss.foerdermittel.backend.archiv.dto.ArchivMapper;
import de.muenchen.oss.foerdermittel.backend.archiv.dto.ArchivResponseDTO;
import de.muenchen.oss.foerdermittel.backend.archiv.dto.ArchivUpdateDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
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

            Projekt projekt = new Projekt();
            projekt.setProjnr("1124101");

            Archiv entity = new Archiv(
                    1L,
                    projekt,
                    LocalDate.of(2024, 9, 14),
                    true,
                    true,
                    LocalDate.of(2024, 9, 16),
                    LocalDate.of(2024, 9, 18),
                    "Test"
            );

            ArchivResponseDTO dto = archivMapper.toDTO(entity);

            // then
            assertThat(dto).isNotNull();
            assertThat(dto.id()).isEqualTo(entity.getId());
            assertThat(dto.projnr()).isEqualTo(entity.getProjekt().getProjnr());
        }

    }

    @Nested
    class ToEntity {

            @Test
            void givenEntity_thenReturnsCorrectDTO() {
                // given
                Projekt projekt = new Projekt();
                projekt.setProjnr("1124101");

                Archiv entity = new Archiv(
                        1L,
                        projekt,
                        LocalDate.of(2024, 9, 14),
                        true,
                        true,
                        LocalDate.of(2024, 9, 16),
                        LocalDate.of(2024, 9, 18),
                        "Test 1"
                );

                // when
                ArchivResponseDTO dto = archivMapper.toDTO(entity);

                // then
                assertThat(dto).isNotNull();
                assertThat(dto.id()).isEqualTo(entity.getId());
                assertThat(dto.projnr()).isEqualTo(entity.getProjekt().getProjnr());
            }

        }


        @Test
        void givenUpdateDTO_thenReturnsCorrectEntity() {
            // given
            ArchivUpdateDTO dto = new ArchivUpdateDTO(
                    LocalDate.of(2024, 9, 14),
                    true,
                    true,
                    LocalDate.of(2024, 9, 16),
                    LocalDate.of(2024, 9, 18),
                    "Test 2"
            );

            // when
            Archiv entity = archivMapper.toEntity(dto);

            // then
            assertThat(entity).isNotNull();
            assertThat(entity.getId()).isNull();
            assertThat(entity.getProjekt()).isNull();
        }

    }

