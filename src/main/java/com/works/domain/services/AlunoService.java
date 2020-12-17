package com.works.domain.services;

import com.works.api.exceptionhandler.ObjectNotFoundException;
import com.works.domain.dto.AlunoDTO;
import com.works.domain.model.Aluno;
import com.works.domain.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    // exception caso não consiga ler o repository
    public AlunoService() throws Exception {
        try{
            if (alunoRepository == null){
                throw new Exception("Repository of Aluno can't be null");
            }
        }catch(Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public List<AlunoDTO> buscarTodos() {

        try {
            List<Aluno> alunos = alunoRepository.findAll();
            List<AlunoDTO> alunoReadDTOS = alunos.stream()
                    .map(aluno -> new AlunoDTO(
                            aluno.getId(),
                            aluno.getNome(),
                            aluno.getCurso(),
                            aluno.getUsuario(),
                            aluno.getSenha(),
                            aluno.getTurmas(),
                            aluno.getProjetos()))
                    .collect(Collectors.toList());

            return alunoReadDTOS;
        }catch(Exception e){
            throw new ObjectNotFoundException("Objeto não encontrado! " +e.getMessage());
        }
    }


    public Aluno buscarPorNomeContendo(String nome) {
        try {
            Optional<Aluno> obj = this.alunoRepository.getByUsuarioContaining(nome);
            return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!"));
        }catch(Exception e){
            throw new ObjectNotFoundException("Objeto não encontrado! " +e.getMessage());
        }
    }

    public ResponseEntity<Aluno> buscarPeloId(long id) {
        try{
            Optional<Aluno> obj = this.alunoRepository.findById(id);
            if (obj.isPresent()){
                return ResponseEntity.ok(obj.get());
            }
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }

    public Aluno salvar(Aluno aluno) {
        try{
            alunoRepository.save(aluno);
        }catch(Exception e){
            throw new ObjectNotFoundException("Não foi possível salvar o objeto " +e.getMessage());
        }
        return aluno;
    }

    public ResponseEntity<Aluno> atualiza(@Valid long alunoId, Aluno aluno) {
        try{
            if(!alunoRepository.existsById(alunoId)){
                return ResponseEntity.notFound().build();
            }
            aluno.setId(alunoId);
            aluno = alunoRepository.save(aluno);
            return ResponseEntity.ok(aluno).ok().build();

        }catch(Exception e){
            throw new ObjectNotFoundException("Não foi possível salvar o objeto " +e.getMessage());
        }
    }

    public ResponseEntity<Void> patch(long id, @Valid AlunoDTO alunoDTO) {
        try{
            Optional<Aluno> alunoOptional = alunoRepository.getById(id);
            if (!alunoOptional.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            Aluno aluno = alunoOptional.get();
            aluno = atualizaDto(alunoDTO,aluno);
            aluno = alunoRepository.save(aluno);
            return ResponseEntity.ok(aluno).ok().build();

        }catch(Exception e){
            throw new ObjectNotFoundException("Não foi possível salvar o objeto " +e.getMessage());
        }
    }

    public Aluno atualizaDto(AlunoDTO alunoDTO, Aluno aluno) {
        try{
            if (alunoDTO.getNome() != null) {
                aluno.setNome(alunoDTO.getNome());
            }
            if (alunoDTO.getCurso() != null) {
                aluno.setCurso(alunoDTO.getCurso());
            }
            if (alunoDTO.getUsuario() != null) {
                aluno.setUsuario(alunoDTO.getUsuario());
            }
            if (alunoDTO.getSenha() != null) {
                aluno.setSenha(alunoDTO.getSenha());
            }
            if (alunoDTO.getTurmas() != null) {
                aluno.setTurmas(alunoDTO.getTurmas());
            }
            return aluno;
        }catch(Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return aluno;
    }

    public ResponseEntity<Void> deleta(long alunoId) {
        try{
            if(!alunoRepository.existsById(alunoId)){
                return ResponseEntity.notFound().build();
            }
            alunoRepository.deleteById(alunoId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public AlunoDTO create(AlunoDTO expectedAlunoDTO) {
        return new AlunoDTO(1L,"Aluno Teste","Curso Teste","usuarioteste","senhateste");
    }
}
