package com.works.domain.services;

import com.works.api.exceptionhandler.ObjectNotFoundException;
import com.works.domain.dto.ProjetoDTO;
import com.works.domain.model.Projeto;
import com.works.domain.repository.AlunoRepository;
import com.works.domain.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjetoService {

    @Autowired
    AlunoRepository alunoRepository;

    @Autowired
    ProjetoRepository projetoRepository;

    // exception caso não consiga ler o repository
    public ProjetoService() throws Exception {
        try{
            if (projetoRepository == null|| alunoRepository == null){
                throw new Exception("Repository of Aluno and Projeto can't be null");
            }
        }catch(Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public List<ProjetoDTO> buscarTodos() {

        try {
            List<Projeto> projetos = projetoRepository.findAll();
            List<ProjetoDTO> projetoDTOS = projetos.stream()
                    .map(projeto -> new ProjetoDTO(
                            projeto.getId(),
                            projeto.getNome(),
                            projeto.getDescricao(),
                            projeto.getProfessor(),
                            projeto.getalunosMatriculados()))
                    .collect(Collectors.toList());

            return projetoDTOS;
        }catch(Exception e){
            throw new ObjectNotFoundException("Objeto não encontrado! " +e.getMessage());
        }
    }

    public ResponseEntity<Projeto> buscarPeloId(long projetoId) {
        try{
            Optional<Projeto> projeto = projetoRepository.findById(projetoId);
            if (projeto.isPresent()){
                return ResponseEntity.ok(projeto.get());
            }

        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }

    public Projeto salvar(Projeto projeto) {
        try{
            projetoRepository.save(projeto);
        }catch(Exception e){
            throw new ObjectNotFoundException("Não foi possível salvar o objeto " +e.getMessage());
        }
        return projeto;
    }

    public ResponseEntity<Projeto> atualiza(@Valid long projetoId, Projeto projeto) {
        try{
            if(!projetoRepository.existsById(projetoId)){
                return ResponseEntity.notFound().build();
            }
            projeto.setId(projetoId);
            projeto = projetoRepository.save(projeto);
            return ResponseEntity.ok(projeto).ok().build();

        }catch(Exception e){
            throw new ObjectNotFoundException("Não foi possível salvar o objeto " +e.getMessage());
        }
    }

    public ResponseEntity<Void> patch(long id, @Valid ProjetoDTO projetoUpdateDTO) {
        Optional<Projeto> projetoOptional = projetoRepository.getById(id);
        if (!projetoOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Projeto projeto = projetoOptional.get();
        atualizaDto(projetoUpdateDTO, projeto);

        projetoRepository.save(projeto);

        return ResponseEntity.noContent().build();
    }

    private void atualizaDto(ProjetoDTO projetoUpdateDTO, Projeto projeto) {
        try{
            if (projetoUpdateDTO.getNome() != null) {
                projeto.setNome(projetoUpdateDTO.getNome());
            }
            if (projetoUpdateDTO.getDescricao() != null) {
                projeto.setDescricao(projetoUpdateDTO.getDescricao());
            }
            if (projetoUpdateDTO.getAlunosMatriculados() != null) {
                projeto.setAlunosMatriculados(projetoUpdateDTO.getAlunosMatriculados());
            }
            if (projetoUpdateDTO.getProfessor() != null) {
                projeto.setProfessor(projetoUpdateDTO.getProfessor());
            }

        }catch(Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Void> deleta(long projetoId) {
        try{
            if(!projetoRepository.existsById(projetoId)){
                return ResponseEntity.notFound().build();
            }
            projetoRepository.deleteById(projetoId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
