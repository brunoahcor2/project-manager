package br.com.bahcor.projectmanager.model.enums;

public enum ProjectStatusEnum {
    EM_ANALISE("EM ANALISE"),
    ANALISE_REALIZADA("ANALISE REALIZADA"),
    ANALISE_APROVADA("ANALISE APROVADA"),
    INICIADO("INICIADO"),
    PLANEJADO("PLANEJADO"),
    EM_ANDAMENTO("EM ANDAMENTO"),
    ENCERRADO("ENCERRADO"),
    CANCELADO("CANCELADO");

    private String status;

    ProjectStatusEnum(String status) {
        this.status = status;
    }

    public String status() {
        return status;
    }

}
