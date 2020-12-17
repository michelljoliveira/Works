package com.works.api.controller.register;


import com.works.api.exceptionhandler.ObjectNotFoundException;
import com.works.domain.dto.TurmaDTO;
import com.works.domain.model.Turma;
import com.works.domain.model.register.RegistroDeTurma;
import com.works.domain.services.AlunoService;
import com.works.domain.services.RegistroDeMatriculaService;
import com.works.domain.services.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/matricula")
public class RegistroMatriculaController {

    @Autowired
    AlunoService alunoService;

    @Autowired
    TurmaService turmaService;

    @Autowired
    RegistroDeMatriculaService registroDeMatriculaService ;

    @GetMapping
    public List<TurmaDTO> listar(){
        try{
            return turmaService.buscarTodos();
        }catch (Exception ex) {
            throw new ObjectNotFoundException("Objetos não encontrados!");
        }

    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Turma> adicionar(@Valid @RequestBody RegistroDeTurma matricula){
        try{
            return registroDeMatriculaService.adicionar(matricula);

        } catch (Exception ex) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}


