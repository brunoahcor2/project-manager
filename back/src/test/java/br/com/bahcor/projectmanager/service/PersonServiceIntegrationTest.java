package br.com.bahcor.projectmanager.service;

import br.com.bahcor.projectmanager.converter.PersonConverter;
import br.com.bahcor.projectmanager.converter.ProjectConverter;
import br.com.bahcor.projectmanager.mocks.PersonMock;
import br.com.bahcor.projectmanager.mocks.ProjectMock;
import br.com.bahcor.projectmanager.model.dto.PersonDTO;
import br.com.bahcor.projectmanager.model.dto.ProjectDTO;
import br.com.bahcor.projectmanager.model.entity.Person;
import br.com.bahcor.projectmanager.model.entity.Project;
import br.com.bahcor.projectmanager.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Sql(scripts = {"/scripts/person_inserts.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"/scripts/clear_tables.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ActiveProfiles("test")
@SpringBootTest
class PersonServiceIntegrationTest {

    @Autowired
    private PersonService service;

    @Autowired
    private PersonRepository repository;

    @Autowired
    private PersonConverter converter;

    @Test
    void save_success() {
        Person person = PersonMock.getPerson(1L);
        PersonDTO dto = service.save(converter.toDTO(person));
        assertEquals(person.getCpf(), dto.getCpf());
    }

    @Test
    void edit_success() {
        Optional<Person> entity = repository.findAll().stream().findFirst();

        Optional<PersonDTO> response = entity
                .map(converter::toDTO)
                .map(dto -> service.edit(dto.getId(), dto));

        assertEquals(entity.get().getId(), response.get().getId());
    }

    @Test
    void listAll() {
        Set<PersonDTO> dtos = service.listAll();
        assertTrue(!dtos.isEmpty());
    }

    @Test
    void findBySearchCriteria_success() {
        Boolean employee = true;
        Boolean active = true;
        List<PersonDTO> list = service.findBySearchCriteria(Optional.ofNullable(employee), Optional.ofNullable(active));
        assertTrue(list.stream().count() > 0);
    }

}