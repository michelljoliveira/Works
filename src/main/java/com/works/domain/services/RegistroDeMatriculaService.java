package com.works.domain.services;

import com.works.domain.model.Aluno;
import com.works.domain.model.Turma;
import com.works.domain.model.register.RegistroDeTurma;
import com.works.domain.repository.AlunoRepository;
import com.works.domain.repository.TurmaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class RegistroDeMatriculaService {

    @Autowired
    AlunoRepository alunoRepository;

    @Autowired
    TurmaRepository turmaRepository;

    public ResponseEntity<Turma> adicionar(RegistroDeTurma matricula){
        try{
            Optional<Aluno> alunoOptional = alunoRepository.getById(matricula.getIdAluno());
            Optional<Turma> turmaOptional = turmaRepository.getById(matricula.getIdTurma());

            if(!turmaOptional.isPresent() || !alunoOptional.isPresent()){
                return ResponseEntity.badRequest().build();
            }

            Aluno aluno = alunoOptional.get();
            Turma turma = turmaOptional.get();

            turma.realizaMatricula(aluno);
            aluno.getTurmas().add(turma);

            turmaRepository.save(turma);
            alunoRepository.save(aluno);


            return ResponseEntity.ok(matricula).ok().build();

        } catch (Exception ex) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
