package de.muenchen.oss.foerdermittel.backend.domain.krankenhaus;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.muenchen.oss.foerdermittel.backend.common.NotFoundException;
import de.muenchen.oss.foerdermittel.backend.configuration.JsonSchemaConfig;
import de.muenchen.oss.foerdermittel.backend.configuration.security.NoSecurityConfiguration;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(KrankenhausController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import({ JsonSchemaConfig.class, NoSecurityConfiguration.class })
@ActiveProfiles("no-security")
class KrankenhausControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private KrankenhausService krankenhausService;

    @Test
    void createSchemaReturnsGeneratedSchema() throws Exception {
        mockMvc.perform(get("/krankenhaeuser/schema/create"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("object"))
                .andExpect(jsonPath("$.properties.krhname.maxLength").value(1))
                .andExpect(jsonPath("$.properties.bezeichnung.maxLength").value(200));
    }

    @Test
    void updateSchemaReturnsGeneratedSchema() throws Exception {
        mockMvc.perform(get("/krankenhaeuser/schema/update"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("object"))
                .andExpect(jsonPath("$.properties.krhname").doesNotExist())
                .andExpect(jsonPath("$.properties.bezeichnung.maxLength").value(200));
    }

    @Test
    void indexReturnsKrankenhaeuser() throws Exception {
        when(krankenhausService.index()).thenReturn(List.of(
                new KrankenhausReadDto("B", "Krankenhaus B"),
                new KrankenhausReadDto("R", "Krankenhaus R")
        ));

        mockMvc.perform(get("/krankenhaeuser/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].krhname").value("B"))
                .andExpect(jsonPath("$[1].krhname").value("R"));
    }

    @Test
    void getReturnsUpdateForm() throws Exception {
        final KrankenhausUpdateDto updateForm = new KrankenhausUpdateDto();
        updateForm.setBezeichnung("Krankenhaus B");

        when(krankenhausService.get("B")).thenReturn(updateForm);

        mockMvc.perform(get("/krankenhaeuser/B"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bezeichnung").value("Krankenhaus B"));
    }

    @Test
    void getReturnsNotFoundForUnknownKrankenhaus() throws Exception {
        when(krankenhausService.get("Z")).thenThrow(new NotFoundException("Krankenhaus not found: Z"));

        mockMvc.perform(get("/krankenhaeuser/Z"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createReturnsNoContent() throws Exception {
        final KrankenhausCreateDto createForm = new KrankenhausCreateDto();
        createForm.setKrhname("B");
        createForm.setBezeichnung("Krankenhaus B");

        mockMvc.perform(post("/krankenhaeuser/")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createForm)))
                .andExpect(status().isNoContent());

        final ArgumentCaptor<KrankenhausCreateDto> createFormCaptor = ArgumentCaptor.forClass(KrankenhausCreateDto.class);
        verify(krankenhausService).create(createFormCaptor.capture());
        assertThat(createFormCaptor.getValue().getKrhname()).isEqualTo("B");
        assertThat(createFormCaptor.getValue().getBezeichnung()).isEqualTo("Krankenhaus B");
    }

    @Test
    void updateUsesPathIdAndBodyWithoutPrimaryKey() throws Exception {
        final KrankenhausUpdateDto updateForm = new KrankenhausUpdateDto();
        updateForm.setBezeichnung("Neues Krankenhaus");

        mockMvc.perform(put("/krankenhaeuser/B")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateForm)))
                .andExpect(status().isNoContent());

        final ArgumentCaptor<KrankenhausUpdateDto> updateFormCaptor = ArgumentCaptor.forClass(KrankenhausUpdateDto.class);
        verify(krankenhausService).update(org.mockito.ArgumentMatchers.eq("B"), updateFormCaptor.capture());
        assertThat(updateFormCaptor.getValue().getBezeichnung()).isEqualTo("Neues Krankenhaus");
    }

    @Test
    void deleteReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/krankenhaeuser/B"))
                .andExpect(status().isNoContent());

        verify(krankenhausService).delete("B");
    }

    @Test
    void deleteReturnsNotFoundForUnknownKrankenhaus() throws Exception {
        doThrow(new NotFoundException("Krankenhaus not found: Z"))
                .when(krankenhausService)
                .delete("Z");

        mockMvc.perform(delete("/krankenhaeuser/Z"))
                .andExpect(status().isNotFound());
    }
}
