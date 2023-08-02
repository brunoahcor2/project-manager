package br.com.bahcor.projectmanager.resources;

import br.com.bahcor.projectmanager.model.dto.PersonDTO;
import br.com.bahcor.projectmanager.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("api/v1/persons")
public class PersonResource {

    @Autowired
    private PersonService service;

    @PutMapping(value = "/{personId}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> edit(@Validated @RequestBody PersonDTO request, @PathVariable("personId") Long personId) {
        return ResponseEntity.ok(service.edit(personId, request));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listAll(Optional<Boolean> employee, Optional<Boolean> active) {
        return ResponseEntity.ok(service.findBySearchCriteria(employee, active));
    }

}
