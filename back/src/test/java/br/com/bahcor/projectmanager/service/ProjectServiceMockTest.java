package br.com.bahcor.projectmanager.service;

import br.com.bahcor.projectmanager.converter.ProjectConverter;
import br.com.bahcor.projectmanager.model.entity.Project;
import br.com.bahcor.projectmanager.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceMockTest {

    @Spy
    private ModelMapper mm;

    @Mock
    private ProjectConverter converter;

    @Mock
    private ProjectRepository repository;

    @InjectMocks
    private ProjectService service;

    @Test
    void listAll() {
        when(repository.findAll()).thenReturn(Arrays.asList(getProject(1),getProject(2)));
        assertEquals(2, service.listAll().stream().count());
    }

    private Project getProject(Integer input){
        return Project.builder()
                .id(input.longValue())
                .name("Project ".concat(input.toString()))
                .description("Description ".concat(input.toString()))
                .dateStart(LocalDate.now())
                .dateEstimatedEnd(LocalDate.now().plusMonths(6))
                .dateEnd(LocalDate.now().plusMonths(6))
                .budget(BigDecimal.valueOf(input))
                .build();
    }

}