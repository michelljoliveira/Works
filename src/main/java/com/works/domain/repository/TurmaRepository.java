package com.works.domain.repository;//package com.works.domain.repository;

import com.works.domain.model.Aluno;
import com.works.domain.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TurmaRepository extends JpaRepository<Turma,Long> {

    List<Aluno> findByNome(String nome);
    List<Aluno> findByNomeContaining(String nome);

    Optional<Turma> getById(long id);
}
