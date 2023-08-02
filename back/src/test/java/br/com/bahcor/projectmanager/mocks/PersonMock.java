package br.com.bahcor.projectmanager.mocks;

import br.com.bahcor.projectmanager.model.entity.Person;
import br.com.bahcor.projectmanager.utils.Util;

import java.time.LocalDate;

public class PersonMock {

    public static Person getPerson(Long input) {
        return Person.builder()
                .name("Person ".concat(input.toString()))
                .cpf(Util.padLeftZeros("123".concat(input.toString()), 11))
                .dateBirth(LocalDate.now())
                .employee(true)
                .position("MANAGER")
                .active(true)
                .build();
    }

    public static Person getPersonWithId(Long personId) {
        return Person.builder()
                .id(personId)
                .name("Person ".concat(personId.toString()))
                .cpf(Util.padLeftZeros("123".concat(personId.toString()), 11))
                .dateBirth(LocalDate.now())
                .employee(true)
                .position("MANAGER")
                .active(true)
                .build();
    }

}
