package com.works.build;

import com.works.domain.dto.AlunoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlunoDTOBuilder {

    @Builder.Default
    private long id = 1L;

    @Builder.Default
    private String nome = "Aluno Teste";
    @Builder.Default
    private String curso = "Curso Teste";
    @Builder.Default
    private String usuario = "usuarioteste";
    @Builder.Default
    private String senha = "senhateste";

    public AlunoDTO toAlunoDTO(){
        return new AlunoDTO(id,nome,curso,usuario,senha);
    }
}





