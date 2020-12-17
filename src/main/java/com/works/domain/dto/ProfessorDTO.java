package com.works.domain.dto;

import com.works.domain.model.Projeto;
import com.works.domain.model.Turma;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorDTO implements Serializable {

    private long id;
    private String nome;
    private String matricula;
    private String areaAtuacao;
    private String formacao;
    private String usuario;
    private String senha;
    private List<Turma> turmas = new ArrayList<>();
    private List<Projeto> projetos = new ArrayList<>();

}
