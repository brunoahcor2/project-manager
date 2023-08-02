package br.com.bahcor.projectmanager.service;

import br.com.bahcor.projectmanager.converter.ProjectConverter;
import br.com.bahcor.projectmanager.exceptions.ResourceNotFoundException;
import br.com.bahcor.projectmanager.model.dto.PersonDTO;
import br.com.bahcor.projectmanager.model.dto.ProjectDTO;
import br.com.bahcor.projectmanager.model.entity.Person;
import br.com.bahcor.projectmanager.model.entity.Project;
import br.com.bahcor.projectmanager.model.enums.ProjectStatusEnum;
import br.com.bahcor.projectmanager.repository.PersonRepository;
import br.com.bahcor.projectmanager.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectConverter converter;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PersonRepository personRepository;

    public ProjectDTO save(ProjectDTO request) {
        return persist(request);
    }

    public ProjectDTO edit(Long projectId, ProjectDTO request) {
        return projectRepository.findById(projectId)
                .map(prj -> {
                    List<Person> p = personRepository.findAllById(request.getPersons().stream().map(PersonDTO::getId).collect(Collectors.toList()));
                    return persist(request);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id = "+projectId));
    }

    public Set<ProjectDTO> listAll(){
        return projectRepository.findAll().stream()
                .map(converter::toDTO)
                .collect(Collectors.toSet());
    }

    public ProjectDTO findById(Long projectId){
        return projectRepository.findById(projectId)
                .map(converter::toDTO)
                .orElse(null);
    }

    public boolean deleteById(Long projectId){
        return projectRepository.findById(projectId)
                .filter(prj -> elegivelParaExclusao(prj.getStatus()))
                .map(prj -> {
                    projectRepository.deleteById(projectId);
                    return true;
                })
                .orElse(false);
    }

    private ProjectDTO persist(ProjectDTO request) {
        Project project = converter.toEntity(request);
        projectRepository.save(project);
        return converter.toDTO(project);
    }

    private boolean elegivelParaExclusao(String status){
        return !ProjectStatusEnum.INICIADO.status().equals(status)
                && !ProjectStatusEnum.EM_ANDAMENTO.status().equals(status)
                && !ProjectStatusEnum.ENCERRADO.status().equals(status);
    }

}
