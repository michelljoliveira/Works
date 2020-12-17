package com.works.api.controller.model;

import com.works.domain.dto.ProfessorDTO;
import com.works.domain.model.Professor;
import com.works.domain.services.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/professores")
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProfessorController {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private ProfessorService professorService;

    @GetMapping
    public List<ProfessorDTO> listar(){
        try{
            return professorService.buscarTodos();

        }catch (Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return null;
    }

    @GetMapping("/{professorId}")
    public ResponseEntity buscar(@PathVariable long professorId){
        try{
            return professorService.buscarPeloId(professorId);

        }catch(Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Professor adicionar(@Valid @RequestBody Professor professor){
        try{
            return professorService.salvar(professor);
        }catch(Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return professor;
    }

    @PutMapping("/{professorId}")
    public ResponseEntity<Professor> atualizar(@Valid @PathVariable long professorId, @Valid  @RequestBody Professor professor){
        try{
            return professorService.atualiza(professorId, professor);

        }catch(Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }

    @PatchMapping("{id}")
    public ResponseEntity<Void> updatePerson(@PathVariable("id") long id,
                                                 @Valid @RequestBody ProfessorDTO professorUpdateDTO) {
        try{
            return professorService.patch(id, professorUpdateDTO);

        }catch(Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @DeleteMapping("/{professorId}")
    public ResponseEntity<Void> deletar(@PathVariable long professorId){
        try{
            return professorService.deleta(professorId);

        }catch(Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.noContent().build();
    }

}