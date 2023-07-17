package br.com.bahcor.projectmanager.service;

import br.com.bahcor.projectmanager.model.dto.PersonDTO;
import br.com.bahcor.projectmanager.model.entity.Person;
import br.com.bahcor.projectmanager.repository.PersonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PersonRepository repository;

    public PersonDTO save(PersonDTO request){
        Person person = mapper.map(request, Person.class);
        person = repository.save(person);
        return mapper.map(person, PersonDTO.class);
    }

    public List<PersonDTO> listAll(){
        return repository.findByEmployee(true)
                .stream()
                .map(p -> mapper.map(p, PersonDTO.class))
                .collect(Collectors.toList());
    }

}
