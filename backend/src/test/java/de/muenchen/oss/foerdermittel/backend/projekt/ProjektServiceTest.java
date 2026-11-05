package de.muenchen.oss.foerdermittel.backend.projekt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.common.NotFoundException;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektCreateDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektMapper;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektUpdateDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ReportAuswertungProjektFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ReportBewilligungAuszahlungProjektFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ReportProjektuebersichtFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.tabs.allgemein.ProjektAllgemeinService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
class ProjektServiceTest {

    @Mock
    private ProjektRepository projektRepository;

    @Mock
    private ProjektMapper projektMapper;

    @Mock
    private ProjektAllgemeinService projektAllgemeinService;

    @InjectMocks
    private ProjektService unitUnderTest;

    @Nested
    class GetProjekt {

        @Test
        void givenIdExists_thenReturnEntity() {
            final String id = "1234567";
            final Projekt expectedEntity = mock(Projekt.class);

            when(projektRepository.findById(id)).thenReturn(Optional.of(expectedEntity));

            final Projekt result = unitUnderTest.getProjekt(id);

            verify(projektRepository, times(1)).findById(id);
            assertThat(result).isSameAs(expectedEntity);
        }

        @Test
        void givenIdNotExists_thenThrowNotFoundException() {
            final String id = "MISSING";

            when(projektRepository.findById(id)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> unitUnderTest.getProjekt(id))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("The Projekt with ID MISSING was not found.");

            verify(projektRepository, times(1)).findById(id);
        }
    }

    @Nested
    class GetProjekte {

        @Test
        void givenFilterAndPageable_thenReturnPageOfEntities() {
            final ProjektFilter filter = mock(ProjektFilter.class);
            final Pageable pageable = PageRequest.of(0, 10);
            final List<Projekt> entities = List.of(mock(Projekt.class));
            final Page<Projekt> expectedPage = new PageImpl<>(entities, pageable, entities.size());

            when(projektRepository.findAll(
                    Mockito.<Specification<Projekt>>any(), Mockito.eq(pageable)))
                    .thenReturn(expectedPage);

            final Page<Projekt> result = unitUnderTest.getProjekte(filter, pageable);

            verify(projektRepository, times(1)).findAll(
                    Mockito.<Specification<Projekt>>any(), Mockito.eq(pageable));
            assertThat(result).isEqualTo(expectedPage);
        }
    }

    @Nested
    class GetProjektFormContextDTOs {

        @Test
        void givenProjects_thenReturnFormContextDTOs() {
            final List<ProjektFormContextDTO> expected = List.of(mock(ProjektFormContextDTO.class));

            when(projektAllgemeinService.getProjektFormContextDTOs()).thenReturn(expected);

            final List<ProjektFormContextDTO> result = unitUnderTest.getProjektFormContextDTOs();

            verify(projektAllgemeinService).getProjektFormContextDTOs();
            assertThat(result).isEqualTo(expected);
        }
    }

    @Nested
    class GetProjektFormContext {

        @Test
        void givenFormContext_thenReturnCorrectFormContext() {
            final ProjektFormContext expected = mock(ProjektFormContext.class);

            when(projektAllgemeinService.getProjektFormContext()).thenReturn(expected);

            final ProjektFormContext result = unitUnderTest.getProjektFormContext();

            verify(projektAllgemeinService).getProjektFormContext();
            assertThat(result).isSameAs(expected);
        }
    }

    @Nested
    class GetReportProjektuebersichtFormContextDTOs {

        @Test
        void givenProjects_thenReturnReportFormContextDTOs() {
            final List<ReportProjektuebersichtFormContextDTO> expected = List.of(mock(ReportProjektuebersichtFormContextDTO.class));

            when(projektRepository.findAllAsBasic()).thenReturn(List.of());
            when(projektMapper.toReportProjektuebersichtFormContext(List.of()))
                    .thenReturn(expected);

            final List<ReportProjektuebersichtFormContextDTO> result = unitUnderTest.getReportProjektuebersichtFormContextDTOs();

            verify(projektRepository, times(1)).findAllAsBasic();
            verify(projektMapper, times(1))
                    .toReportProjektuebersichtFormContext(List.of());
            assertThat(result).isEqualTo(expected);
        }
    }

    @Nested
    class CreateProjekt {

        @Test
        void givenValidCreateDTO_thenCreatesProjekt() {
            final ProjektCreateDTO dto = new ProjektCreateDTO(
                    "1234567", 1, null, null, "ABC", "UA", null, 26, 0, 1,
                    "Projektname", "Musterstraße 1", "Neubau", null, null,
                    null, null, null, null, null, null, null);
            final Projekt projekt = new Projekt();

            when(projektAllgemeinService.createProjekt(dto)).thenReturn(projekt);
            when(projektRepository.insert(projekt)).thenReturn(projekt);

            final Projekt result = unitUnderTest.createProjekt(dto);

            verify(projektAllgemeinService).createProjekt(dto);
            verify(projektRepository).insert(projekt);
            assertThat(result).isSameAs(projekt);
        }
    }

    @Nested
    class UpdateProjekt {

        @Test
        void givenUpdateDTO_thenUpdatesProjekt() {
            final ProjektUpdateDTO dto = new ProjektUpdateDTO(
                    "Neuer Projektname", null, null, null, null, null, null,
                    null, null, null, null, null, null, null, null, null, null);
            final Projekt projekt = new Projekt();

            when(projektRepository.findById("1234567")).thenReturn(Optional.of(projekt));
            when(projektAllgemeinService.updateProjekt(projekt, dto)).thenReturn(projekt);
            when(projektRepository.update(projekt)).thenReturn(projekt);

            final Projekt result = unitUnderTest.updateProjekt(dto, "1234567");

            verify(projektRepository).findById("1234567");
            verify(projektAllgemeinService).updateProjekt(projekt, dto);
            verify(projektRepository).update(projekt);
            assertThat(result).isSameAs(projekt);
        }

        @Test
        void givenProjektNotExists_thenThrowNotFoundException() {
            final String id = "MISSING";
            final ProjektUpdateDTO dto = new ProjektUpdateDTO(
                    null, null, null, null, null, null, null,
                    null, null, null, null, null, null, null, null, null, null);

            when(projektRepository.findById(id)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> unitUnderTest.updateProjekt(dto, id))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("The Projekt with ID MISSING was not found.");

            verify(projektRepository).findById(id);
            verify(projektAllgemeinService, never()).updateProjekt(Mockito.any(), Mockito.any());
            verify(projektRepository, never()).update(Mockito.any(Projekt.class));
        }
    }

    @Nested
    class DeleteProjekt {

        @Test
        void givenIdExists_thenDeleteProjekt() {
            final String id = "1234567";

            when(projektRepository.findById(id)).thenReturn(Optional.of(new Projekt()));

            unitUnderTest.deleteProjekt(id);

            verify(projektRepository, times(1)).findById(id);
            verify(projektRepository, times(1)).deleteById(id);
        }

        @Test
        void givenIdNotExists_thenThrowNotFoundException() {
            final String id = "MISSING";

            when(projektRepository.findById(id)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> unitUnderTest.deleteProjekt(id))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage("The Projekt with ID MISSING was not found.");

            verify(projektRepository, times(1)).findById(id);
            verify(projektRepository, never()).deleteById(id);
        }
    }

    @Nested
    class GetReportAuswertungProjektFormContextDTOs {

        @Test
        void givenProjects_thenReturnReportFormContextDTOs() {
            final List<ReportAuswertungProjektFormContextDTO> expected = List.of(mock(ReportAuswertungProjektFormContextDTO.class));

            when(projektRepository.findAllAuswertungProjekte()).thenReturn(List.of());
            when(projektMapper.toReportAuswertungProjektFormContext(List.of()))
                    .thenReturn(expected);

            final List<ReportAuswertungProjektFormContextDTO> result = unitUnderTest.getReportAuswertungProjektFormContextDTOs();

            verify(projektRepository, times(1)).findAllAuswertungProjekte();
            verify(projektMapper, times(1))
                    .toReportAuswertungProjektFormContext(List.of());
            assertThat(result).isEqualTo(expected);
        }
    }

    @Nested
    class GetReportBewilligungAuszahlungProjektFormContextDTOs {

        @Test
        void givenProjects_thenReturnReportFormContextDTOs() {
            final List<ReportBewilligungAuszahlungProjektFormContextDTO> expected = List.of(mock(ReportBewilligungAuszahlungProjektFormContextDTO.class));

            when(projektRepository.findAllAsBasic()).thenReturn(List.of());
            when(projektMapper.toReportBewilligungAuszahlungProjektFormContext(List.of()))
                    .thenReturn(expected);

            final List<ReportBewilligungAuszahlungProjektFormContextDTO> result = unitUnderTest.getReportBewilligungAuszahlungProjektFormContextDTOs();

            verify(projektRepository, times(1)).findAllAsBasic();
            verify(projektMapper, times(1))
                    .toReportBewilligungAuszahlungProjektFormContext(List.of());
            assertThat(result).isEqualTo(expected);
        }
    }

    @Test
    void givenProjects_thenMapsTheirReportFormContext() {
        final List<ReportProjektuebersichtFormContextDTO> expected = List.of(mock(ReportProjektuebersichtFormContextDTO.class));

        when(projektRepository.findAllAsBasic()).thenReturn(List.of());
        when(projektMapper.toReportProjektuebersichtFormContext(List.of())).thenReturn(expected);

        final var result = unitUnderTest.getReportProjektuebersichtFormContextDTOs();

        assertThat(result).isEqualTo(expected);
        verify(projektRepository).findAllAsBasic();
        verify(projektMapper).toReportProjektuebersichtFormContext(List.of());
    }

    @Test
    void givenExistingProjnr_thenReturnsProjekt() {
        final String projnr = "1234567";
        final Projekt projekt = mock(Projekt.class);

        when(projektRepository.findById(projnr)).thenReturn(Optional.of(projekt));

        final Projekt result = unitUnderTest.getProjekt(projnr);

        assertThat(result).isSameAs(projekt);
        verify(projektRepository).findById(projnr);
    }

    @Test
    void givenMissingProjnr_thenThrowsNotFoundException() {
        final String projnr = "MISSING";

        when(projektRepository.findById(projnr)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> unitUnderTest.getProjekt(projnr))
                .hasMessage("The Projekt with ID MISSING was not found.");

        verify(projektRepository).findById(projnr);
    }
}
