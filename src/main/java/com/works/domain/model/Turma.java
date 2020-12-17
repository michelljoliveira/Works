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
@Table(name="turma", uniqueConstraints = {})
public class Turma implements Serializable {

    private static final long serialVersionUID = 1081869386060246794L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String turno;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @ManyToMany(mappedBy="turmas", cascade = CascadeType.ALL)
    private List<Aluno> alunosMatriculados = new ArrayList<>();


    @JsonIgnore
    @ManyToMany
    @JoinTable(name="aluno_has_turma",
            joinColumns=
            @JoinColumn(name="id", referencedColumnName="turma_id"),
            inverseJoinColumns=
            @JoinColumn(name="id", referencedColumnName="aluno_id")
    )
    public List<Aluno> getalunosMatriculados() { //retorna todos os alunos da turma
        return alunosMatriculados;
    }

    public Aluno getAlunoDaTurma(int id) { //retorna apenas 1 aluno especifico da turma
        return alunosMatriculados.get(id);
    }

    public void realizaMatricula(Aluno aluno){
        alunosMatriculados.add(aluno);
    }

}
