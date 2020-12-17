package com.works.domain.model.register;

import com.works.domain.model.Turma;
import lombok.Data;

import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
public class RegistroDeTurma {

    private long idAluno;
    private long idTurma;

    @OneToMany(mappedBy = "aluno")
    private List<Turma> turmas = new ArrayList<>();
}
