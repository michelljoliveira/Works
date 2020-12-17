package com.works.domain.repository;

import com.works.domain.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor,Long> {

    List<Professor> findByNome(String nome);
    List<Professor> findByNomeContaining(String nome);

    Optional<Professor> getById(long id);
    Optional<Professor> getByUsuarioContaining(String usuario);
}
