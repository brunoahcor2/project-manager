package br.com.bahcor.projectmanager.repository;

import br.com.bahcor.projectmanager.exceptions.ResourceNotFoundException;
import br.com.bahcor.projectmanager.mocks.ProjectMock;
import br.com.bahcor.projectmanager.model.entity.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@Sql( scripts = "/scripts/person_inserts.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD )
@Sql(scripts = {"/scripts/clear_tables.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ActiveProfiles("test")
@DataJpaTest
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PersonRepository personRepository;

    @Test
    void whenSaveWithValidParams_thenSuccess() {
        Project project = ProjectMock.getProject(1L);
        project.addPersons(personRepository.findAll().stream().findFirst().orElse(null));
        Project projectDB = projectRepository.save(project);
        assertNotNull(projectDB);
        assertEquals(project.getName(), projectDB.getName());
        assertEquals(1, projectDB.getPersons().size());
    }

    @Test
    void whenSaveWithManyPerson_thenSuccess() {
        Project project = ProjectMock.getProject(1L);
        personRepository.findAll().stream().limit(3).forEach(project::addPersons);
        Project projectDB = projectRepository.save(project);
        assertNotNull(projectDB);
        assertEquals(3, projectDB.getPersons().size());
    }

    @Test
    void whenInvalidPersonId_thenResourceNotFoundException() {
        Long personId = 999L;
        Exception exception = assertThrowsExactly(ResourceNotFoundException.class, () -> {
            personRepository.findById(personId).orElseThrow(() -> new ResourceNotFoundException("Person not found with id = "+personId));
        });
        assertEquals("Person not found with id = "+personId, exception.getMessage());
    }

}