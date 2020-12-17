package com.works.api.exceptionhandler;

import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.LocalDateTime;

@ControllerAdvice
public class AlunoAlreadyRegisteredException extends Exception {


    @Override
    public String getMessage(){
        Problema problema = new Problema();
        problema.setTitulo("Aluno já registrado.");
        problema.setDataHora(LocalDateTime.now());
        return problema.getTitulo();
    }

}
