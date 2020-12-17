package com.works.domain.repository;

import com.works.domain.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno,Long> {

    List<Aluno> findByNome(String nome);
    List<Aluno> findByNomeContaining(String nome);

    Optional<Aluno> getById(long id);
    Optional<Aluno> getByUsuarioContaining(String usuario);
}
