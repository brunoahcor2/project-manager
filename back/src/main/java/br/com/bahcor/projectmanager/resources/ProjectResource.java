package br.com.bahcor.projectmanager.resources;

import br.com.bahcor.projectmanager.model.dto.ProjectDTO;
import br.com.bahcor.projectmanager.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("api/v1/projects")
public class ProjectResource {

    @Autowired
    private ProjectService service;

    @PostMapping(value = "/registry",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registry(@Validated @RequestBody ProjectDTO request) {
        return ResponseEntity.ok(service.save(request));
    }

    @PutMapping(value = "/{projectId}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editar(@Validated @RequestBody ProjectDTO request, @PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok(service.edit(projectId, request));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping(value = "/{projectId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> consultar(@PathVariable Long projectId) {
        ProjectDTO response = service.findById(projectId);
        if(response == null){
            log.info("Project not found with the ID entered = "+projectId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{idProjeto}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deletarPorId(@PathVariable Long idProjeto) {
        if(!service.deleteById(idProjeto)){
            return ResponseEntity.badRequest().body("Project not found or ineligible for exclusion");
        }
        return ResponseEntity.ok().build();
    }

}
