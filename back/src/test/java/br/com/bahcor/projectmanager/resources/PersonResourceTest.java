package br.com.bahcor.projectmanager.resources;

import br.com.bahcor.projectmanager.mocks.PersonMock;
import br.com.bahcor.projectmanager.model.dto.PersonDTO;
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
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    void registry() throws Exception {
        PersonDTO dto = mm.map(PersonMock.getPerson(1L), PersonDTO.class);
        given(this.service.save(any(PersonDTO.class))).willReturn(dto);

        this.mvc.perform(post("/api/v1/persons/registry")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(Util.asJsonString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf", equalTo(dto.getCpf())));
    }

    @Test
    void listAll() throws Exception {
        PersonDTO dto1 = mm.map(PersonMock.getPerson(1L), PersonDTO.class);
        PersonDTO dto2 = mm.map(PersonMock.getPerson(2L), PersonDTO.class);
        given(this.service.listAll()).willReturn(Arrays.asList(dto1,dto2));

        this.mvc.perform(get("/api/v1/persons").accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

}