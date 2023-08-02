package br.com.bahcor.projectmanager.service;

import br.com.bahcor.projectmanager.converter.PersonConverter;
import br.com.bahcor.projectmanager.exceptions.ResourceNotFoundException;
import br.com.bahcor.projectmanager.model.dto.PersonDTO;
import br.com.bahcor.projectmanager.model.dto.ProjectDTO;
import br.com.bahcor.projectmanager.model.entity.Person;
import br.com.bahcor.projectmanager.model.entity.Project;
import br.com.bahcor.projectmanager.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    private PersonConverter converter;

    @Autowired
    private PersonRepository repository;

    public PersonDTO save(PersonDTO request){ return persist(request); }

    public PersonDTO edit(Long personId, PersonDTO request) {
        return repository.findById(personId)
                .map(person -> persist(request))
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id = "+personId));
    }

    public Set<PersonDTO> listAll(){
        return repository.findAll().stream()
                .map(converter::toDTO)
                .collect(Collectors.toSet());
    }

    public List<PersonDTO> findBySearchCriteria(Optional<Boolean> employee, Optional<Boolean> active){

        return repository.findAll((Specification<Person>) (root, query, criteriaBuilder) -> {
                    List<Predicate> predicates = new ArrayList<>();

                    employee.ifPresent(s ->
                            predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("employee"), s))));
                    active.ifPresent(s ->
                            predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("active"), s))));

                    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                }).stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
    }

    private PersonDTO persist(PersonDTO request) {
        Person person = converter.toEntity(request);
        repository.save(person);
        return converter.toDTO(person);
    }

}
