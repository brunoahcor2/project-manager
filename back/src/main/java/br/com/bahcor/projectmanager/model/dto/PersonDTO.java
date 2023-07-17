package br.com.bahcor.projectmanager.model.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonDTO implements Serializable {

    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String cpf;
    @NonNull
    private boolean funcionario;
    private LocalDate dateBirth;
    private boolean employee;
    private String position;

}
