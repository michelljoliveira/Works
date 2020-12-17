package com.works.domain.dto;

import com.works.domain.model.Aluno;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TurmaDTO implements Serializable {

    private long id;
    private String nome;
    private String turno;
    private List<Aluno> turmas = new ArrayList<>();


}
