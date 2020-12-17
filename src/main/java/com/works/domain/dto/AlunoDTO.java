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
public class AlunoDTO implements Serializable {

    private long id;
    private String nome;
    private String curso;
    private String usuario;
    private String senha;
    private List<Turma> turmas = new ArrayList<>();
    private List<Projeto> projetos = new ArrayList<>();


    public AlunoDTO(long id, String nome, String curso, String usuario, String senha) {
        this.nome = nome;
        this.curso = curso;
        this.usuario = usuario;
        this.senha = senha;
    }

}
