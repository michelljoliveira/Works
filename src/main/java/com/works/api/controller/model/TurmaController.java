package com.works.api.controller.model;//package com.works.api.controller;

import com.works.api.exceptionhandler.ObjectNotFoundException;
import com.works.domain.dto.TurmaDTO;
import com.works.domain.model.Turma;
import com.works.domain.services.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/turmas")
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TurmaController {

    @PersistenceContext
    private EntityManager manager;
    @Autowired
    private TurmaService turmaService;

    @GetMapping
    public List<TurmaDTO> listar(){
        try{
            return turmaService.buscarTodos();

        }catch (Exception e){
            throw new ObjectNotFoundException("lista não encontrada! " +e.getMessage());
        }
    }

    @GetMapping("/{turmaId}")
    public ResponseEntity buscar(@Valid @PathVariable long turmaId){
        try{
            return turmaService.buscarPeloId(turmaId);

        }catch(Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Turma adicionar(@Valid @RequestBody Turma turma){
        try{
            return turmaService.salvar(turma);

        }catch(Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return turma;
    }

    @PutMapping("/{turmaId}")
    public ResponseEntity<Turma> atualizar(@Valid @PathVariable long turmaId,
                                           @Valid  @RequestBody Turma turma){
        try{
            return turmaService.atualiza(turmaId, turma);

        }catch(Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return null;
    }

    @PatchMapping("{id}")
    public ResponseEntity<Void> updatePerson(@PathVariable("id") long id,
                                             @Valid @RequestBody TurmaDTO turmaUpdateDTO) {
        try{
            return turmaService.patch(id, turmaUpdateDTO);

        }catch(Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @DeleteMapping("/{clienteId}")
    public ResponseEntity<Void> deletar(@PathVariable long turmaId){
        try{
            return turmaService.deleta(turmaId);

        }catch(Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.noContent().build();
    }


}