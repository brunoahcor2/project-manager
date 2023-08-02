package br.com.bahcor.projectmanager.resources;

import br.com.bahcor.projectmanager.mocks.PersonMock;
import br.com.bahcor.projectmanager.model.dto.PersonDTO;
import br.com.bahcor.projectmanager.model.dto.ProjectDTO;
import br.com.bahcor.projectmanager.service.PersonService;
import br.com.bahcor.projectmanager.utils.Util;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonResource.class)
class PersonResourceTest {

    private final ModelMapper mm = new ModelMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PersonService service;

    @Test
    void edit() throws Exception {
        Long personId = 1L;
        PersonDTO dto = mm.map(PersonMock.getPersonWithId(personId), PersonDTO.class);
        given(this.service.edit(anyLong(), any(PersonDTO.class))).willReturn(dto);

        this.mvc.perform(put("/api/v1/persons/{personId}",personId)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Util.asJsonString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf", equalTo(dto.getCpf())));
    }

    @Test
    void findBySearchCriteria() throws Exception {
        PersonDTO dto1 = mm.map(PersonMock.getPerson(1L), PersonDTO.class);
        PersonDTO dto2 = mm.map(PersonMock.getPerson(2L), PersonDTO.class);
        given(this.service.findBySearchCriteria(any(),any())).willReturn(Arrays.asList(dto1,dto2));

        this.mvc.perform(get("/api/v1/persons")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

}