package br.com.bahcor.projectmanager.mocks;

import br.com.bahcor.projectmanager.model.entity.Project;
import br.com.bahcor.projectmanager.model.enums.ProjectRiskEnum;
import br.com.bahcor.projectmanager.model.enums.ProjectStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ProjectMock {

    public static Project getProject(Long input) {
        LocalDate currentDate = LocalDate.now();
        return Project.builder()
                .name("Project Test "+input)
                .description("Description Test "+input)
                .dateStart(currentDate)
                .dateEstimatedEnd(currentDate.plusMonths(6))
                .dateEnd(currentDate.plusMonths(6))
                .status(ProjectStatusEnum.EM_ANALISE.status())
                .budget(BigDecimal.valueOf(1000000.0))
                .risk(ProjectRiskEnum.BAIXO.risco())
                .build();
    }

    public static Project getProjectWithId(Long projectId) {
        LocalDate currentDate = LocalDate.now();
        return Project.builder()
                .id(projectId)
                .name("Project Test "+projectId)
                .description("Description Test "+projectId)
                .dateStart(currentDate)
                .dateEstimatedEnd(currentDate.plusMonths(6))
                .dateEnd(currentDate.plusMonths(6))
                .status(ProjectStatusEnum.EM_ANALISE.status())
                .budget(BigDecimal.valueOf(1000000.0))
                .risk(ProjectRiskEnum.BAIXO.risco())
                .build();
    }

}
