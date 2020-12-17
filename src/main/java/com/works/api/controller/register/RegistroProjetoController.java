package com.works.api.controller.register;


import com.works.api.exceptionhandler.ObjectNotFoundException;
import com.works.domain.dto.ProfessorDTO;
import com.works.domain.model.Projeto;
import com.works.domain.model.register.RegistroDeProjeto;
import com.works.domain.services.AlunoService;
import com.works.domain.services.ProfessorService;
import com.works.domain.services.RegistroDeProjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cadastrodeprojetos")
public class RegistroProjetoController {

    @Autowired
    AlunoService alunoService;

    @Autowired
    ProfessorService professorService;

    @Autowired
    RegistroDeProjetoService registroDeProjetoService;

    @GetMapping
    public List<ProfessorDTO> listar(){
        try{
            return professorService.buscarTodos();
        }catch (Exception ex) {
            throw new ObjectNotFoundException("Objetos não encontrados!");
        }
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Projeto> adicionar(@Valid @RequestBody RegistroDeProjeto matricula){
        try{
            return registroDeProjetoService.adicionar(matricula);

        } catch (Exception ex) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}


