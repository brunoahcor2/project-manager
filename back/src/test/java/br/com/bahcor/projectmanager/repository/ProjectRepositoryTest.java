package br.com.bahcor.projectmanager.repository;

import static org.junit.jupiter.api.Assertions.*;

import br.com.bahcor.projectmanager.exceptions.ResourceNotFoundException;
import br.com.bahcor.projectmanager.model.entity.Person;
import br.com.bahcor.projectmanager.model.entity.Project;
import br.com.bahcor.projectmanager.model.enums.ProjectRiskEnum;
import br.com.bahcor.projectmanager.model.enums.ProjectStatusEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDate;

@Sql( scripts = "/scripts/person_inserts.sql",
      executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD )
@TestPropertySource(locations="classpath:application-test.yml")
@DataJpaTest
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PersonRepository personRepository;

    @Test
    void whenSaveWithValidParams_thenSuccess() {
        Project project = getProject();
        project.addPersons(getPerson(1L));
        Project projectDB = projectRepository.save(project);
        assertNotNull(projectDB);
        assertEquals(project.getName(), projectDB.getName());
        assertTrue(projectDB.getPersons().size() == 1);
    }

    @Test
    void whenSaveWithManyPerson_thenSuccess() {
        Project project = getProject();
        project.addPersons(getPerson(1L));
        project.addPersons(getPerson(2L));
        project.addPersons(getPerson(3L));
        Project projectDB = projectRepository.save(project);
        assertNotNull(projectDB);
        assertTrue(projectDB.getPersons().size() == 3);
    }

    @Test
    void whenInvalidPersonId_thenSuccess() {
        Long personId = 999L;
        Exception exception = assertThrowsExactly(ResourceNotFoundException.class, () -> {
            getPerson(personId);
        });
        assertEquals("Person not found with id = "+personId, exception.getMessage());
    }

    private Project getProject() {
        LocalDate currentDate = LocalDate.now();
        return Project.builder()
                .name("Project Test")
                .description("Description Test")
                .dateStart(currentDate)
                .dateEstimatedEnd(currentDate.plusMonths(6))
                .dateEnd(currentDate.plusMonths(6))
                .status(ProjectStatusEnum.EM_ANDAMENTO.status())
                .budget(BigDecimal.valueOf(1000000.0))
                .risk(ProjectRiskEnum.BAIXO.risco())
                .build();
    }

    private Person getPerson(Long personId){
        return personRepository.findById(personId).orElseThrow(() -> new ResourceNotFoundException("Person not found with id = "+personId));
    }

}