package com.works.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.*;
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
@Table(name="professor", uniqueConstraints = {})
@SecondaryTable(name = "projeto", pkJoinColumns =
        { @PrimaryKeyJoinColumn(name = "id") })
public class Professor implements Serializable {

    private static final long serialVersionUID = -910945815128481L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Size(max = 255)
    private String nome;

    @NotBlank
    @Size(max = 255)
    @Column(unique = true)
    private String matricula;

    @NotBlank
    @Size(max = 255)
    private String areaAtuacao;

    @NotBlank
    @Size(max = 255)
    private String formacao;

    @NotBlank
    @Column(unique = true)
    private String usuario;

    @NotBlank
    private String senha;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="professor_has_turma",
            joinColumns={@JoinColumn(name="professor_id")},
            inverseJoinColumns={@JoinColumn(name="turma_id")})
    private List<Turma> turmas = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="professor_has_projeto",
            joinColumns={@JoinColumn(name="professor_id")},
            inverseJoinColumns={@JoinColumn(name="projeto_id")})
    private List<Projeto> projetos = new ArrayList<>();

    public void setSenha(String senha) {
        String salt = "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6";
        senha = BCrypt.hashpw(senha, salt);
        this.senha = senha;
    }
    @ManyToMany
    @JoinTable(name="professor_has_projeto",
            joinColumns=
            @JoinColumn(name="id", referencedColumnName="professor_id"),
            inverseJoinColumns=
            @JoinColumn(name="id", referencedColumnName="projeto_id")
    )
    public List<Projeto> getProjetos() {
        return projetos;
    }

    public void addProjeto(Projeto projeto){
        projetos.add(projeto);
    }


}
