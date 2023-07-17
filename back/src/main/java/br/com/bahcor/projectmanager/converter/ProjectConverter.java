package br.com.bahcor.projectmanager.converter;

import br.com.bahcor.projectmanager.model.dto.ProjectDTO;
import br.com.bahcor.projectmanager.model.entity.Project;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectConverter {

    @Autowired
    private ModelMapper mm;

    public ProjectDTO toDTO(Project entity) {
        return mm.map(entity, ProjectDTO.class);
    }

    public Project toEntity(ProjectDTO dto) {
        return mm.map(dto, Project.class);
    }

    public List<ProjectDTO> toListDTO(List<Project> listEntities) {
        return listEntities.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<Project> toListEntities(List<ProjectDTO> listDtos) {
        return listDtos.stream().map(this::toEntity).collect(Collectors.toList());
    }



}
