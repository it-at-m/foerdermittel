package de.muenchen.oss.foerdermittel.backend.archiv;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.common.NotFoundException;
import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import de.muenchen.oss.foerdermittel.backend.projekt.ProjektRepository;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektMapper;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class ArchivServiceTest {

    private static final String PROJNR = "1124101";

    @Mock
    private ArchivRepository archivRepository;

    @Mock
    private ProjektRepository projektRepository;

    @Mock
    private ProjektMapper projektMapper;

    @InjectMocks
    private ArchivService unitUnderTest;

    @Nested
    class GetArchiveintraege {

        @Test
        void givenPageable_thenReturnPageOfEntities() {
            // Given
            final int pageNumber = 0;
            final int pageSize = 10;
            final Pageable pageable = PageRequest.of(pageNumber, pageSize);

            final Archiv entity1 = new Archiv();
            final Archiv entity2 = new Archiv();

            final List<Archiv> entities = List.of(entity1, entity2);

            final Page<Archiv> expectedPage =
                    new PageImpl<>(entities, pageable, entities.size());

            when(archivRepository.findAll(pageable))
                    .thenReturn(expectedPage);

            // When
            final Page<Archiv> result =
                    unitUnderTest.getArchiveintraege(pageable);

            // Then
            verify(archivRepository, times(1))
                    .findAll(pageable);

            assertThat(result)
                    .isEqualTo(expectedPage);
        }
    }

    @Nested
    class CreateArchiv {

        @Test
        void givenArchivAndExistingProjekt_thenCallInsertEntity() {
            // Given
            // Projekt existiert bereits und wird nur über die Projektnummer
            // identifiziert.
            final Projekt existingProjekt =
                    new Projekt(PROJNR,null,null,null, null );

            final Archiv entityToInsert =
                    new Archiv(
                            null,
                            new Projekt(PROJNR, null,null,null, null),
                            LocalDate.of(2024, 9, 14),
                            false,
                            true,
                            LocalDate.of(2024, 9, 16),
                            LocalDate.of(2024, 9, 18),
                            "Fusce tincidunt, nisl quis bibendum fermentum"
                    );

            final Archiv expectedEntity =
                    new Archiv(
                            1L,
                            existingProjekt,
                            LocalDate.of(2024, 9, 14),
                            false,
                            true,
                            LocalDate.of(2024, 9, 16),
                            LocalDate.of(2024, 9, 18),
                            "Fusce tincidunt, nisl quis bibendum fermentum"
                    );

            when(projektRepository.findById(PROJNR))
                    .thenReturn(Optional.of(existingProjekt));

            when(archivRepository.insert(entityToInsert))
                    .thenReturn(expectedEntity);

            // When
            final Archiv result =
                    unitUnderTest.createArchiv(entityToInsert);

            // Then
            verify(projektRepository, times(1))
                    .findById(PROJNR);

            verify(archivRepository, times(1))
                    .insert(entityToInsert);

            assertThat(entityToInsert.getProjekt())
                    .isEqualTo(existingProjekt);

            assertThat(result)
                    .usingRecursiveComparison()
                    .isEqualTo(expectedEntity);
        }

        @Test
        void givenProjektNotExists_thenThrowEntityNotFoundException() {
            // Given
            final Archiv entityToInsert =
                    new Archiv(
                            null,
                            new Projekt(PROJNR, null,null,null, null),
                            LocalDate.of(2024, 9, 14),
                            false,
                            true,
                            LocalDate.of(2024, 9, 16),
                            LocalDate.of(2024, 9, 18),
                            "Fusce tincidunt, nisl quis bibendum fermentum"
                    );

            when(projektRepository.findById(PROJNR))
                    .thenReturn(Optional.empty());

            // When
            final Exception exception =
                    Assertions.assertThrows(
                            EntityNotFoundException.class,
                            () -> unitUnderTest.createArchiv(entityToInsert)
                    );

            // Then
            verify(projektRepository, times(1))
                    .findById(PROJNR);

            verify(archivRepository, never())
                    .insert(entityToInsert);

            assertThat(exception.getMessage())
                    .isEqualTo(
                            "Projekt mit Projektnummer "
                                    + PROJNR
                                    + " wurde nicht gefunden"
                    );
        }
    }

    @Nested
    class UpdateArchiv {

        @Test
        void givenEntityExists_thenReturnUpdatedEntity() {
            // Given
            final Long id = 1L;

            final Projekt existingProjekt =
                    new Projekt(PROJNR, null,null,null, null);

            final Archiv entityToUpdate =
                    new Archiv(
                            id,
                            existingProjekt,
                            LocalDate.of(2026, 8, 1),
                            false,
                            false,
                            LocalDate.of(2026, 8, 1),
                            LocalDate.of(2026, 8, 1),
                            "Updated"
                    );

            final Archiv foundEntity =
                    new Archiv(
                            id,
                            existingProjekt,
                            LocalDate.of(2024, 9, 14),
                            false,
                            true,
                            LocalDate.of(2024, 9, 16),
                            LocalDate.of(2024, 9, 18),
                            "Test"
                    );

            final Archiv expectedEntity =
                    new Archiv(
                            id,
                            existingProjekt,
                            LocalDate.of(2026, 8, 1),
                            false,
                            false,
                            LocalDate.of(2026, 8, 1),
                            LocalDate.of(2026, 8, 1),
                            "Updated"
                    );

            when(archivRepository.findById(id))
                    .thenReturn(Optional.of(foundEntity));

            when(archivRepository.update(foundEntity))
                    .thenReturn(expectedEntity);

            // When
            final Archiv result =
                    unitUnderTest.updateArchiv(entityToUpdate, id);

            // Then
            verify(archivRepository, times(1))
                    .findById(id);

            verify(archivRepository, times(1))
                    .update(foundEntity);

            // Projekt wird beim Update nicht neu gesucht/manipuliert.
            verify(projektRepository, never())
                    .findById(PROJNR);

            assertThat(result)
                    .usingRecursiveComparison()
                    .isEqualTo(expectedEntity);
        }

        @Test
        void givenEntityNotExists_thenThrowNotFoundException() {
            // Given
            final Long id = 1L;

            final Archiv entityToUpdate =
                    new Archiv();

            when(archivRepository.findById(id))
                    .thenReturn(Optional.empty());

            // When
            final Exception exception =
                    Assertions.assertThrows(
                            NotFoundException.class,
                            () -> unitUnderTest.updateArchiv(
                                    entityToUpdate,
                                    id
                            )
                    );

            // Then
            verify(archivRepository, times(1))
                    .findById(id);

            verify(archivRepository, never())
                    .update(entityToUpdate);

            assertThat(exception.getMessage())
                    .isEqualTo(
                            String.format(
                                    "404 NOT_FOUND \"Could not find entity with ID %s\"",
                                    id
                            )
                    );
        }
    }

    @Nested
    class DeleteArchiv {

        @Test
        void givenIdExists_thenReturnVoid() {
            // Given
            final Long id = 1L;

            final Archiv archiv =
                    new Archiv();

            when(archivRepository.findById(id))
                    .thenReturn(Optional.of(archiv));

            // When
            unitUnderTest.deleteArchiv(id);

            // Then
            verify(archivRepository, times(1))
                    .findById(id);

            verify(archivRepository, times(1))
                    .delete(archiv);
        }

        @Test
        void givenIdNotExists_thenThrowNotFoundException() {
            // Given
            final Long id = 1L;

            when(archivRepository.findById(id))
                    .thenReturn(Optional.empty());

            // When
            final Exception exception =
                    Assertions.assertThrows(
                            NotFoundException.class,
                            () -> unitUnderTest.deleteArchiv(id)
                    );

            // Then
            verify(archivRepository, times(1))
                    .findById(id);

            verify(archivRepository, never())
                    .delete(
                            org.mockito.ArgumentMatchers.any(Archiv.class)
                    );

            assertThat(exception.getMessage())
                    .isEqualTo(
                            String.format(
                                    "404 NOT_FOUND \"Could not find entity with ID %s\"",
                                    id
                            )
                    );
        }
    }

    @Nested
    class GetArchivFormContext {

        @Test
        void givenExistingProjekte_thenReturnCorrectFormContext() {
            // Given
            final List<Long> archivIds =
                    List.of(1L, 2L, 3L);

            // Projekte existieren bereits.
            // Für Archiv ist nur die Projektnummer relevant.
            final Projekt projekt1 =
                    new Projekt("1124101", null,null,null, null);

            final Projekt projekt2 =
                    new Projekt("1124102", null,null,null, null);

            final ProjektResponseDTO projektDto1 =
                    new ProjektResponseDTO(
                            "1124101",
                            null,null,null
                    );

            final ProjektResponseDTO projektDto2 =
                    new ProjektResponseDTO(
                            "1124102",
                            null,null,null
                    );

            when(archivRepository.findAllWithProjekt())
                    .thenReturn(archivIds);

            when(projektRepository.findAll())
                    .thenReturn(List.of(projekt1, projekt2));

            when(projektMapper.toDTO(projekt1))
                    .thenReturn(projektDto1);

            when(projektMapper.toDTO(projekt2))
                    .thenReturn(projektDto2);

            // When
            final ArchivFormContext formContext =
                    unitUnderTest.getArchivFormContext();

            // Then
            verify(archivRepository, times(1))
                    .findAllWithProjekt();

            verify(projektRepository, times(1))
                    .findAll();

            verify(projektMapper, times(1))
                    .toDTO(projekt1);

            verify(projektMapper, times(1))
                    .toDTO(projekt2);

            assertThat(formContext.archivId())
                    .isEqualTo(archivIds);

            assertThat(formContext.projekte())
                    .hasSize(2);

            assertThat(formContext.projekte().get(0))
                    .isEqualTo(projektDto1);

            assertThat(formContext.projekte().get(1))
                    .isEqualTo(projektDto2);
        }
    }
}