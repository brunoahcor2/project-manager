package br.com.bahcor.projectmanager.model.enums;

public enum ProjectRiskEnum {
    BAIXO("BAIXO"),
    MEDIO("MEDIO"),
    ALTO("ALTO");

    private String risco;

    ProjectRiskEnum(String risco) {
        this.risco = risco;
    }

    public String risco() {
        return risco;
    }

}
