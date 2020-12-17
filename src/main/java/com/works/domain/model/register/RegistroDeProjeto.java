package com.works.domain.model.register;

import com.works.domain.model.Aluno;
import lombok.Data;

import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
public class RegistroDeProjeto {

    private long idProfessor;
    private long idAluno;
    private long idProjeto;

    @OneToMany(mappedBy = "projetos")
    private List<Aluno> alunos = new ArrayList<>();
}
