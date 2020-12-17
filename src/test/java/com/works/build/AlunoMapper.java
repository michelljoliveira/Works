package com.works.build;

import com.works.domain.dto.AlunoDTO;
import com.works.domain.model.Aluno;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AlunoMapper {
    public static Aluno toModel(AlunoDTO expectedAlunoDTO) {
        Aluno aluno = new Aluno();
        try{
            if (expectedAlunoDTO.getNome() != null) {
                aluno.setNome(expectedAlunoDTO.getNome());
            }
            if (expectedAlunoDTO.getCurso() != null) {
                aluno.setCurso(expectedAlunoDTO.getCurso());
            }
            if (expectedAlunoDTO.getUsuario() != null) {
                aluno.setUsuario(expectedAlunoDTO.getUsuario());
            }
            if (expectedAlunoDTO.getSenha() != null) {
                aluno.setSenha(expectedAlunoDTO.getSenha());
            }
            if (expectedAlunoDTO.getTurmas() != null) {
                aluno.setTurmas(expectedAlunoDTO.getTurmas());
            }
            return aluno;
        }catch(Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return aluno;
    }
}
