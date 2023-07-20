package br.com.bahcor.projectmanager.converter;

import br.com.bahcor.projectmanager.model.dto.PersonDTO;
import br.com.bahcor.projectmanager.model.dto.ProjectDTO;
import br.com.bahcor.projectmanager.model.entity.Person;
import br.com.bahcor.projectmanager.model.entity.Project;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonConverter {

    @Autowired
    private ModelMapper mm;

    public PersonDTO toDTO(Person entity) {
        return mm.map(entity, PersonDTO.class);
    }

    public Person toEntity(PersonDTO dto) {
        return mm.map(dto, Person.class);
    }

    public List<PersonDTO> toListDTO(List<Person> listEntities) {
        return listEntities.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<Person> toListEntities(List<PersonDTO> listDtos) {
        return listDtos.stream().map(this::toEntity).collect(Collectors.toList());
    }

}
