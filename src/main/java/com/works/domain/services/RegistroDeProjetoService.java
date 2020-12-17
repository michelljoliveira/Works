package com.works.domain.services;

import com.works.domain.model.Aluno;
import com.works.domain.model.Professor;
import com.works.domain.model.Projeto;
import com.works.domain.model.register.RegistroDeProjeto;
import com.works.domain.repository.AlunoRepository;
import com.works.domain.repository.ProfessorRepository;
import com.works.domain.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class RegistroDeProjetoService {
    @Autowired
    AlunoRepository alunoRepository;

    @Autowired
    ProfessorRepository professorRepository;

    @Autowired
    ProjetoRepository projetoRepository;

    public ResponseEntity<Projeto> adicionar(RegistroDeProjeto matricula){
        try{
            Optional<Aluno> alunoOptional = alunoRepository.getById(matricula.getIdAluno());
            Optional<Professor> professorOptional = professorRepository.getById(matricula.getIdProfessor());
            Optional<Projeto> projetoOptional = projetoRepository.getById(matricula.getIdProjeto());

            if(!professorOptional.isPresent() || !alunoOptional.isPresent()|| !projetoOptional.isPresent()){
                return ResponseEntity.badRequest().build();
            }

            Aluno aluno = alunoOptional.get();
            Professor professor = professorOptional.get();
            Projeto projeto = projetoOptional.get();

            professor.addProjeto(projeto);
            aluno.addProjeto(projeto);
            projeto.setProfessor(professor);


            professorRepository.save(professor);
            alunoRepository.save(aluno);
            projetoRepository.save(projeto);


            return ResponseEntity.ok(matricula).ok().build();

        } catch (Exception ex) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
