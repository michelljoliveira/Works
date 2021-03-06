package com.works.domain.repository;//package com.works.domain.repository;

import com.works.domain.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto,Long> {

    List<Projeto> findByNome(String nome);
    List<Projeto> findByNomeContaining(String nome);

    Optional<Projeto> getById(long id);
}
