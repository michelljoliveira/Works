package com.works.domain.model;//package com.works.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="projeto", uniqueConstraints = {})
public class Projeto implements Serializable {

    private static final long serialVersionUID = 10818694454246794L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;

    @JsonIgnore
    @ManyToMany(mappedBy="projetos", cascade = CascadeType.ALL)
    private List<Aluno> alunosMatriculados = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;


    @ManyToMany
    @JoinTable(name="aluno_has_projeto",
            joinColumns=
            @JoinColumn(name="id", referencedColumnName="projeto_id"),
            inverseJoinColumns=
            @JoinColumn(name="id", referencedColumnName="aluno_id")
    )
    public List<Aluno> getalunosMatriculados() { //retorna todos os alunos do projeto
        return alunosMatriculados;
    }

    public void addAlunoNoProjeto(Aluno aluno){
        alunosMatriculados.add(aluno);
    }

    public Aluno getAlunoDaProjeto(int id) { //retorna apenas 1 aluno especifico do projeto
        return alunosMatriculados.get(id);
    }

    public void realizaMatricula(Aluno aluno){
        alunosMatriculados.add(aluno);
    }

}
