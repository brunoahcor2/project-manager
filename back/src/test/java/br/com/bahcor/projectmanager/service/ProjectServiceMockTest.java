package br.com.bahcor.projectmanager.service;

import br.com.bahcor.projectmanager.converter.ProjectConverter;
import br.com.bahcor.projectmanager.mocks.ProjectMock;
import br.com.bahcor.projectmanager.model.dto.ProjectDTO;
import br.com.bahcor.projectmanager.model.entity.Project;
import br.com.bahcor.projectmanager.model.enums.ProjectStatusEnum;
import br.com.bahcor.projectmanager.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceMockTest {

    private final ModelMapper mm = new ModelMapper();

    @Mock
    private ProjectConverter converter;

    @Mock
    private ProjectRepository repository;

    @InjectMocks
    private ProjectService service;

    @Test
    void whenRegister_thenReturnSuccess() {
        Long projectId = 1L;
        Project entity = ProjectMock.getProject(projectId);
        ProjectDTO dto = mm.map(entity, ProjectDTO.class);
        when(repository.save(any())).thenReturn(entity);
        when(converter.toEntity(dto)).thenReturn(entity);
        when(converter.toDTO(entity)).thenReturn(dto);

        dto = service.save(dto);
        assertEquals(dto.getName(),entity.getName());
    }

    @Test
    void whenList_thenReturnSuccess() {
        Project prj1 = ProjectMock.getProject(1L);
        Project prj2 = ProjectMock.getProject(2L);
        when(repository.findAll()).thenReturn(Arrays.asList(prj1, prj2));
        when(converter.toDTO(prj1)).thenReturn(mm.map(prj1,ProjectDTO.class));
        when(converter.toDTO(prj2)).thenReturn(mm.map(prj2,ProjectDTO.class));

        Set<ProjectDTO> listProjects = service.listAll();
        assertNotNull(listProjects);
        assertEquals(2, listProjects.stream().count());
    }

    @Test
    void whenListIsEmpty_thenReturnNull() {
        when(repository.findAll()).thenReturn(Arrays.asList());

        Set<ProjectDTO> listProjects = service.listAll();
        assertTrue(listProjects.isEmpty());
    }

    @Test
    void whenFindById_thenReturnSuccess() {
        Project entity = ProjectMock.getProjectWithId(3L);
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(converter.toDTO(entity)).thenReturn(mm.map(entity,ProjectDTO.class));

        ProjectDTO dto = service.findById(entity.getId());
        assertNotNull(dto);
        assertEquals(entity.getName(), dto.getName());
    }

    @Test
    void whenFindByIdWithInvalidID_thenReturnNull() {
        Long projectId = 4L;
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

        ProjectDTO dto = service.findById(projectId);
        assertNull(dto);
    }

    @Test
    void whenDeleteById_thenReturnSuccess() {
        Long projectId = 5L;
        Project entity = ProjectMock.getProjectWithId(projectId);
        when(repository.findById(anyLong())).thenReturn(Optional.of(entity));
        doNothing().when(repository).deleteById(anyLong());
        assertTrue(service.deleteById(projectId));
    }

    @Test
    void whenDeleteById_thenReturnFalse() {
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        assertFalse(service.deleteById(1L));
    }

    @Test
    void whenDeleteById_thenReturnFalse2() {
        Long projectId = 6L;
        Project entity = ProjectMock.getProject(projectId);
        entity.setStatus(ProjectStatusEnum.ENCERRADO.status());
        Optional<Project> optionalProject = Optional.of(entity);
        when(repository.findById(anyLong())).thenReturn(optionalProject);
        assertFalse(service.deleteById(projectId));
    }

}