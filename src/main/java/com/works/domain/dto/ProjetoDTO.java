package com.works.domain.dto;

import com.works.domain.model.Aluno;
import com.works.domain.model.Professor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjetoDTO implements Serializable {

    private long id;
    private String nome;
    private String descricao;
    private Professor professor;
    private List<Aluno> alunosMatriculados = new ArrayList<>();
}
