package br.com.bahcor.projectmanager.repository;

import br.com.bahcor.projectmanager.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByEmployee(boolean funcionario);

}
