package br.com.bahcor.projectmanager.service;

import br.com.bahcor.projectmanager.converter.ProjectConverter;
import br.com.bahcor.projectmanager.mocks.PersonMock;
import br.com.bahcor.projectmanager.mocks.ProjectMock;
import br.com.bahcor.projectmanager.model.dto.ProjectDTO;
import br.com.bahcor.projectmanager.model.entity.Person;
import br.com.bahcor.projectmanager.model.entity.Project;
import br.com.bahcor.projectmanager.repository.PersonRepository;
import br.com.bahcor.projectmanager.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@Sql(scripts = {"/scripts/person_inserts.sql", "/scripts/project_inserts.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"/scripts/clear_tables.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ActiveProfiles("test")
@SpringBootTest
class ProjectServiceIntegrationTest {

    @Autowired
    private ProjectService service;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectConverter converter;

    @BeforeAll
    static void setup() {
        System.out.println("### BeforeAll ..");
    }

    @Test
    void save() {
        Project project = ProjectMock.getProject(1L);
        project.setPersons(
                personRepository.findAll().stream()
                        .filter(person -> person.getId() < 4)
                        .collect(Collectors.toSet())
        );
        service.save(converter.toDTO(project));
    }

    @Test
    void edit() {
        Optional<Project> entity = projectRepository.findAll().stream().findFirst();

        Optional<ProjectDTO> response = entity
                .map(prj -> {
                    Person person = personRepository.findAll().stream().findFirst().orElse(PersonMock.getPersonWithId(99L));
                    prj.addPersons(person);
                    return prj;
                })
                .map(converter::toDTO)
                .map(dto -> service.edit(dto.getId(), dto));

        assertEquals(entity.get().getId(), response.get().getId());
    }

    @Test
    void listAll() {
        Set<ProjectDTO> dtos = service.listAll();
        assertTrue(!dtos.isEmpty());
    }

    @Test
    void findById() {
        ProjectDTO dto = service.findById(999L);
        assertNull(dto);
    }

    @Test
    void deleteById() {
        Optional<Project> entity = projectRepository.findAll().stream().findFirst();
        assertTrue(service.deleteById(entity.get().getId()));
    }

}