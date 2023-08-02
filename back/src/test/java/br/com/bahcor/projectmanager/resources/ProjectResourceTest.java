package br.com.bahcor.projectmanager.resources;

import br.com.bahcor.projectmanager.mocks.ProjectMock;
import br.com.bahcor.projectmanager.model.dto.ProjectDTO;
import br.com.bahcor.projectmanager.service.ProjectService;
import br.com.bahcor.projectmanager.utils.Util;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectResource.class)
class ProjectResourceTest {

    private final ModelMapper mm = new ModelMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProjectService service;

    @Test
    void whenRegisterAndValidObject_thenReturnOk() throws Exception {
        ProjectDTO dto = mm.map(ProjectMock.getProject(1L), ProjectDTO.class);
        when(this.service.save(any(ProjectDTO.class))).thenReturn(dto);

        this.mvc.perform(post("/api/v1/projects/registry")
                        .content( Util.asJsonString(dto) )
                        .contentType( MediaType.APPLICATION_JSON ))
                .andExpect( status().isOk() )
                .andExpect(jsonPath("$.name").value(dto.getName()))
                .andExpect(jsonPath("$.description").value(dto.getDescription()));
    }

    @Test
    void whenRegisterAndInvalidObject_thenReturnException() throws Exception {

        //Exception exception = assertThrows(NullPointerException.class, () -> {

            ProjectDTO dto = mm.map(ProjectMock.getProject(2L), ProjectDTO.class);
            dto.setName(null);
            when(this.service.save(any(ProjectDTO.class))).thenReturn(dto);

            this.mvc.perform(post("/api/v1/projects/registry")
                    .content( Util.asJsonString(dto) )
                    .contentType( MediaType.APPLICATION_JSON ))
                    .andExpect( status().isBadRequest() );

        //});

        //assertEquals("name is marked non-null but is null", exception.getMessage());
    }

    @Test
    void whenEditAndValidObject_thenReturnOk() throws Exception {
        Long projectId = 3L;
        ProjectDTO dto = mm.map(ProjectMock.getProjectWithId(projectId), ProjectDTO.class);
        when(this.service.edit(anyLong(), any(ProjectDTO.class))).thenReturn(dto);

        this.mvc.perform( put("/api/v1/projects/{projectId}",projectId)
                        .content( Util.asJsonString(dto) )
                        .contentType( MediaType.APPLICATION_JSON ))
                .andExpect( status().isOk() )
                .andExpect(jsonPath("$.name").value(dto.getName()))
                .andExpect(jsonPath("$.description").value(dto.getDescription()));
    }

    @Test
    void whenlistAndHaveProjects_thenReturnOk() throws Exception {
        ProjectDTO dto = mm.map(ProjectMock.getProject(4L), ProjectDTO.class);
        Set<ProjectDTO> projects = new HashSet<>();
        projects.add(dto);
        when(this.service.listAll()).thenReturn(projects);

        this.mvc.perform(get("/api/v1/projects")
                        .accept( MediaType.APPLICATION_JSON ))
                .andExpect( status().isOk() )
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void whenlistAndNotHaveProjects_thenReturnOk() throws Exception {
        when(this.service.listAll()).thenReturn(new HashSet<>());

        this.mvc.perform(get("/api/v1/projects").contentType( MediaType.APPLICATION_JSON ))
                .andExpect( status().isOk() )
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void whenFindByIdWithValidID_thenReturnOk() throws Exception {
        Long projectId = 5L;
        ProjectDTO dto = mm.map(ProjectMock.getProject(projectId), ProjectDTO.class);
        when(this.service.findById(anyLong())).thenReturn(dto);

        this.mvc.perform(get("/api/v1/projects/{projectId}",projectId)
                        .contentType( MediaType.APPLICATION_JSON ))
                .andExpect( status().isOk() )
                .andExpect(jsonPath("$.name").value(dto.getName()));
    }

    @Test
    void whenFindByIdWithInvalidID_thenReturnBadRequest() throws Exception {
        Long projectId = 999L;
        when(this.service.findById(anyLong())).thenReturn(null);

        this.mvc.perform(get("/api/v1/projects/{projectId}",projectId)
                        .contentType( MediaType.APPLICATION_JSON ))
                .andExpect( status().isNoContent() );
    }

    @Test
    void whenDeletByIdWithValidID_thenReturnOk() throws Exception {
        Long projectId = 1L;
        when(this.service.deleteById(anyLong())).thenReturn(true);

        this.mvc.perform(delete("/api/v1/projects/{projectId}",projectId)
                        .contentType( MediaType.APPLICATION_JSON ))
                .andExpect( status().isOk() );
    }

    @Test
    void whenDeletByIdWithInvalidID_thenReturnOk() throws Exception {
        Long projectId = 0L;
        when(this.service.deleteById(anyLong())).thenReturn(false);

        this.mvc.perform(delete("/api/v1/projects/{projectId}",projectId)
                        .contentType( MediaType.APPLICATION_JSON ))
                .andExpect( status().isBadRequest() )
                .andExpect(content().string(containsStringIgnoringCase("Project not found or ineligible for exclusion")));
    }

}