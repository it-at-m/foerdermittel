package de.muenchen.oss.foerdermittel.backend.projekt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.muenchen.oss.foerdermittel.backend.common.NotFoundException;
import de.muenchen.oss.foerdermittel.backend.projekt.dao.BasicProjektDAO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektMapper;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ReportProjektuebersichtFormContextDTO;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProjektServiceTest {

    @Mock
    private ProjektRepository projektRepository;

    @Mock
    private ProjektMapper projektMapper;

    @InjectMocks
    private ProjektService projektService;

    @Test
    void givenProjects_thenMapsTheirReportFormContext() {
        // Given
        final List<BasicProjektDAO> projekte = List.of(
                new BasicProjektDAO("P-123", "Projektname", "Projektstraße 1"));
        final List<ReportProjektuebersichtFormContextDTO> expected = List.of(
                new ReportProjektuebersichtFormContextDTO("P-123", "Projektname", "Projektstraße 1"));
        when(projektRepository.findAllAsBasic()).thenReturn(projekte);
        when(projektMapper.toReportFormContext(projekte)).thenReturn(expected);

        // When
        final List<ReportProjektuebersichtFormContextDTO> result = projektService.getReportProjektuebersichtFormContextDTOs();

        // Then
        assertThat(result).isEqualTo(expected);
        verify(projektRepository, times(1)).findAllAsBasic();
        verify(projektMapper, times(1)).toReportFormContext(projekte);
    }

    @Test
    void givenExistingProjnr_thenReturnsProjekt() {
        // Given
        final Projekt projekt = mock(Projekt.class);
        when(projektRepository.findById("P-123")).thenReturn(Optional.of(projekt));

        // When
        final Projekt result = projektService.getProjekt("P-123");

        // Then
        assertThat(result).isSameAs(projekt);
    }

    @Test
    void givenMissingProjnr_thenThrowsNotFoundException() {
        // Given
        when(projektRepository.findById("MISSING")).thenReturn(Optional.empty());

        // When / Then
        assertThatThrownBy(() -> projektService.getProjekt("MISSING"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("The Projekt with ID MISSING was not found.");
    }
}
