package com.works.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="aluno", uniqueConstraints = {})
@SecondaryTable(name = "turma", pkJoinColumns =
        { @PrimaryKeyJoinColumn(name = "id") })
public class Aluno implements Serializable {

    private static final long serialVersionUID = -9109414221418128481L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Size(max = 255)
    private String nome;

    @NotBlank
    @Email
    @Size(max = 255)
    private String curso;

    @NotBlank
    @Column(unique = true)
    private String usuario;

    @NotBlank
    private String senha;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="aluno_has_turma",
            joinColumns={@JoinColumn(name="aluno_id")},
            inverseJoinColumns={@JoinColumn(name="turma_id")})
    private List<Turma> turmas = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="aluno_has_projeto",
            joinColumns={@JoinColumn(name="professor_id")},
            inverseJoinColumns={@JoinColumn(name="projeto_id")})
    private List<Projeto> projetos = new ArrayList<>();

    public void setSenha(String senha) {
        String salt = "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6";
        senha = BCrypt.hashpw(senha, salt);
        this.senha = senha;
    }
    @ManyToMany
    @JoinTable(name="aluno_has_turma",
            joinColumns=
            @JoinColumn(name="id", referencedColumnName="aluno_id"),
            inverseJoinColumns=
            @JoinColumn(name="id", referencedColumnName="turma_id")
    )
    public List<Turma> getTurmas() {
        return turmas;
    }

    public void addProjeto(Projeto projeto){
        projetos.add(projeto);
    }


}
