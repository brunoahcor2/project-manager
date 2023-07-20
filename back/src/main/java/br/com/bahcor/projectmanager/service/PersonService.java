package br.com.bahcor.projectmanager.service;

import br.com.bahcor.projectmanager.converter.PersonConverter;
import br.com.bahcor.projectmanager.model.dto.PersonDTO;
import br.com.bahcor.projectmanager.model.entity.Person;
import br.com.bahcor.projectmanager.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    private PersonConverter converter;

    @Autowired
    private PersonRepository repository;

    public PersonDTO save(PersonDTO request){
        Person person = converter.toEntity(request);
        repository.save(person);
        return converter.toDTO(person);
    }

    public List<PersonDTO> listAll(){
        return repository.findByEmployee(true)
                .stream()
                .map(person -> converter.toDTO(person))
                .collect(Collectors.toList());
    }

}
