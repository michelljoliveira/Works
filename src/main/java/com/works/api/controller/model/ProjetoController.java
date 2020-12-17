package com.works.api.controller.model;//package com.works.api.controller;

import com.works.api.exceptionhandler.ObjectNotFoundException;
import com.works.domain.dto.ProjetoDTO;
import com.works.domain.model.Projeto;
import com.works.domain.services.ProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/projetos")
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProjetoController {

    @PersistenceContext
    private EntityManager manager;
    @Autowired
    private ProjetoService projetoService;

    @GetMapping
    public List<ProjetoDTO> listar(){
        try{
            return projetoService.buscarTodos();

        }catch (Exception e){
            throw new ObjectNotFoundException("lista não encontrada! " +e.getMessage());
        }
    }

    @GetMapping("/{projetoId}")
    public ResponseEntity buscar(@Valid @PathVariable long projetoId){
        try{
            return projetoService.buscarPeloId(projetoId);

        }catch(Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Projeto adicionar(@Valid @RequestBody Projeto projeto){
        try{
            return projetoService.salvar(projeto);

        }catch(Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return projeto;
    }

    @PutMapping("/{projetoId}")
    public ResponseEntity<Projeto> atualizar(@Valid @PathVariable long projetoId,
                                           @Valid  @RequestBody Projeto projeto){
        try{
            return projetoService.atualiza(projetoId, projeto);

        }catch(Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return null;
    }

    @PatchMapping("{id}")
    public ResponseEntity<Void> updatePerson(@PathVariable("id") long id,
                                             @Valid @RequestBody ProjetoDTO projetoUpdateDTO) {
        try{
            return projetoService.patch(id, projetoUpdateDTO);

        }catch(Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @DeleteMapping("/{clienteId}")
    public ResponseEntity<Void> deletar(@PathVariable long projetoId){
        try{
            return projetoService.deleta(projetoId);

        }catch(Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.noContent().build();
    }

}