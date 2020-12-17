package com.works.domain.services;

import com.works.api.exceptionhandler.ObjectNotFoundException;
import com.works.domain.dto.TurmaDTO;
import com.works.domain.model.Turma;
import com.works.domain.repository.AlunoRepository;
import com.works.domain.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TurmaService {

    @Autowired
    AlunoRepository alunoRepository;

    @Autowired
    TurmaRepository turmaRepository;

    // exception caso não consiga ler o repository
    public TurmaService() throws Exception {
        try{
            if (turmaRepository == null|| alunoRepository == null){
                throw new Exception("Repository of Aluno and Turma can't be null");
            }
        }catch(Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public List<TurmaDTO> buscarTodos() {

        try {
            List<Turma> turmas = turmaRepository.findAll();
            List<TurmaDTO> turmaDTOS = turmas.stream()
                    .map(turma -> new TurmaDTO(
                            turma.getId(),
                            turma.getNome(),
                            turma.getTurno(),
                            turma.getalunosMatriculados()))
                    .collect(Collectors.toList());

            return turmaDTOS;
        }catch(Exception e){
            throw new ObjectNotFoundException("Objeto não encontrado! " +e.getMessage());
        }
    }

    public ResponseEntity<Turma> buscarPeloId(long turmaId) {
        try{
            Optional<Turma> turma = turmaRepository.findById(turmaId);
            if (turma.isPresent()){
                return ResponseEntity.ok(turma.get());
            }

        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }

    public Turma salvar(Turma turma) {
        try{
            turmaRepository.save(turma);
        }catch(Exception e){
            throw new ObjectNotFoundException("Não foi possível salvar o objeto " +e.getMessage());
        }
        return turma;
    }

    public ResponseEntity<Turma> atualiza(@Valid long turmaId, Turma turma) {
        try{
            if(!turmaRepository.existsById(turmaId)){
                return ResponseEntity.notFound().build();
            }
            turma.setId(turmaId);
            turma = turmaRepository.save(turma);
            return ResponseEntity.ok(turma).ok().build();

        }catch(Exception e){
            throw new ObjectNotFoundException("Não foi possível salvar o objeto " +e.getMessage());
        }
    }

    public ResponseEntity<Void> patch(long id, @Valid TurmaDTO turmaDTO) {
        Optional<Turma> turmaOptional = turmaRepository.getById(id);
        if (!turmaOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Turma turma = turmaOptional.get();
        atualizaDto(turmaDTO, turma);

        turmaRepository.save(turma);

        return ResponseEntity.noContent().build();
    }

    private void atualizaDto(TurmaDTO turmaUpdateDTO, Turma turma) {
        try{
            if (turmaUpdateDTO.getNome() != null) {
                turma.setNome(turmaUpdateDTO.getNome());
            }
            if (turmaUpdateDTO.getTurno() != null) {
                turma.setTurno(turmaUpdateDTO.getTurno());
            }
            if (turmaUpdateDTO.getTurmas() != null) {
                turma.setAlunosMatriculados(turmaUpdateDTO.getTurmas());
            }

        }catch(Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Void> deleta(long turmaId) {
        try{
            if(!turmaRepository.existsById(turmaId)){
                return ResponseEntity.notFound().build();
            }
            turmaRepository.deleteById(turmaId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
