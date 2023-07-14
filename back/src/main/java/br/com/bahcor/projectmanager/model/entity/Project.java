package br.com.bahcor.projectmanager.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate dateStart;
    private LocalDate dateEstimatedEnd;
    private LocalDate dateEnd;
    private String description;
    private String status;
    private BigDecimal budget;
    private String risk;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "members",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "person_id") )
    private Set<Person> persons;

    public void addPersons(Person person) {
        if(persons == null){
            persons = new HashSet<>();
        }
        this.persons.add(person);
    }

}
