package com.works.api.controller.model;

import com.works.domain.dto.AlunoDTO;
import com.works.domain.model.Aluno;
import com.works.domain.services.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/alunos")
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AlunoController {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private AlunoService alunoService;

    @GetMapping
    public List<AlunoDTO> listar(){
        try{
            return alunoService.buscarTodos();

        }catch (Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return null;
    }

    @GetMapping("/{alunoId}")
    public ResponseEntity buscar(@PathVariable long alunoId){
        try{
            return alunoService.buscarPeloId(alunoId);

        }catch(Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Aluno adicionar(@Valid @RequestBody Aluno aluno){
        try{
            return alunoService.salvar(aluno);
        }catch(Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return aluno;
    }

    @PutMapping("/{alunoId}")
    public ResponseEntity<Aluno> atualizar(@Valid @PathVariable long alunoId, @Valid  @RequestBody Aluno aluno){
        try{
            return alunoService.atualiza(alunoId, aluno);

        }catch(Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePerson(@PathVariable("id") long id,
                                                 @Valid @RequestBody AlunoDTO alunoUpdateDTO) {
        try{
            return alunoService.patch(id, alunoUpdateDTO);

        }catch(Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @DeleteMapping("/{alunoId}")
    public ResponseEntity<Void> deletar(@PathVariable long alunoId){
        try{
            return alunoService.deleta(alunoId);

        }catch(Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.noContent().build();
    }

}