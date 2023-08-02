package br.com.bahcor.projectmanager.service;

import br.com.bahcor.projectmanager.converter.PersonConverter;
import br.com.bahcor.projectmanager.exceptions.ResourceNotFoundException;
import br.com.bahcor.projectmanager.mocks.PersonMock;
import br.com.bahcor.projectmanager.model.dto.PersonDTO;
import br.com.bahcor.projectmanager.model.entity.Person;
import br.com.bahcor.projectmanager.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PersonServiceMockTest {

    private final ModelMapper mm = new ModelMapper();

    @Mock
    private PersonConverter converter;

    @Mock
    private PersonRepository repository;

    @InjectMocks
    private PersonService service;

    @Test
    void whenEdit_thenReturnException() {
        Long personId = 1L;
        Person entity = PersonMock.getPersonWithId(personId);
        PersonDTO dto = mm.map(entity, PersonDTO.class);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.edit(personId, dto);
        });

        assertEquals("Person not found with id = 1", exception.getMessage());
    }

}