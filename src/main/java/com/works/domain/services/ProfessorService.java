package com.works.domain.services;

import com.works.api.exceptionhandler.ObjectNotFoundException;
import com.works.domain.dto.ProfessorDTO;
import com.works.domain.model.Professor;
import com.works.domain.repository.ProfessorRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProfessorService {

    private ProfessorRepository professorRepository;

    // exception caso não consiga ler o repository
    public ProfessorService() throws Exception {
        try{
            if (professorRepository == null){
                throw new Exception("Repository of Professor can't be null");
            }
        }catch(Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public List<ProfessorDTO> buscarTodos() {

        try {
            List<Professor> professores = professorRepository.findAll();
            List<ProfessorDTO> professorDTOS = professores.stream()
                    .map(professor -> new ProfessorDTO(
                            professor.getId(),
                            professor.getNome(),
                            professor.getMatricula(),
                            professor.getAreaAtuacao(),
                            professor.getFormacao(),
                            professor.getUsuario(),
                            professor.getSenha(),
                            professor.getTurmas(),
                            professor.getProjetos()))
                    .collect(Collectors.toList());

            return professorDTOS;
        }catch(Exception e){
            throw new ObjectNotFoundException("Objeto não encontrado! " +e.getMessage());
        }
    }


    public Professor buscarPorNomeContendo(String nome) {
        try {
            Optional<Professor> obj = this.professorRepository.getByUsuarioContaining(nome);
            return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!"));
        }catch(Exception e){
            throw new ObjectNotFoundException("Objeto não encontrado! " +e.getMessage());
        }
    }

    public ResponseEntity<Professor> buscarPeloId(long id) {
        try{
            Optional<Professor> obj = this.professorRepository.findById(id);
            if (obj.isPresent()){
                return ResponseEntity.ok(obj.get());
            }
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }

    public Professor salvar(Professor professor) {
        try{
            professorRepository.save(professor);
        }catch(Exception e){
            throw new ObjectNotFoundException("Não foi possível salvar o objeto " +e.getMessage());
        }
        return professor;
    }

    public ResponseEntity<Professor> atualiza(@Valid long professorId, Professor professor) {
        try{
            if(!professorRepository.existsById(professorId)){
                return ResponseEntity.notFound().build();
            }
            professor.setId(professorId);
            professor = professorRepository.save(professor);
            return ResponseEntity.ok(professor).ok().build();

        }catch(Exception e){
            throw new ObjectNotFoundException("Não foi possível salvar o objeto " +e.getMessage());
        }
    }

    public ResponseEntity<Void> patch(long id, @Valid ProfessorDTO professorUpdateDTO) {
        try{
            Optional<Professor> professorOptional = professorRepository.getById(id);
            if (!professorOptional.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            Professor professor = professorOptional.get();
            professor = atualizaDto(professorUpdateDTO,professor);
            professor = professorRepository.save(professor);
            return ResponseEntity.ok(professor).ok().build();

        }catch(Exception e){
            throw new ObjectNotFoundException("Não foi possível salvar o objeto " +e.getMessage());
        }
    }

    public Professor atualizaDto(ProfessorDTO professorUpdateDTO, Professor professor) {
        try{
            if (professorUpdateDTO.getNome() != null) {
                professor.setNome(professorUpdateDTO.getNome());
            }
            if (professorUpdateDTO.getMatricula() != null) {
                professor.setMatricula(professorUpdateDTO.getMatricula());
            }
            if (professorUpdateDTO.getAreaAtuacao() != null) {
                professor.setAreaAtuacao(professorUpdateDTO.getAreaAtuacao());
            }
            if (professorUpdateDTO.getFormacao() != null) {
                professor.setFormacao(professorUpdateDTO.getFormacao());
            }
            if (professorUpdateDTO.getUsuario() != null) {
                professor.setUsuario(professorUpdateDTO.getUsuario());
            }
            if (professorUpdateDTO.getSenha() != null) {
                professor.setSenha(professorUpdateDTO.getSenha());
            }
            if (professorUpdateDTO.getTurmas() != null) {
                professor.setTurmas(professorUpdateDTO.getTurmas());
            }
            if (professorUpdateDTO.getProjetos() != null) {
                professor.setProjetos(professorUpdateDTO.getProjetos());
            }
            return professor;
        }catch(Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return professor;
    }

    public ResponseEntity<Void> deleta(long professorId) {
        try{
            if(!professorRepository.existsById(professorId)){
                return ResponseEntity.notFound().build();
            }
            professorRepository.deleteById(professorId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
