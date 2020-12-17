package com.works.service;

import com.works.api.exceptionhandler.AlunoAlreadyRegisteredException;
import com.works.build.AlunoDTOBuilder;
import com.works.build.AlunoMapper;
import com.works.domain.dto.AlunoDTO;
import com.works.domain.model.Aluno;
import com.works.domain.repository.AlunoRepository;
import com.works.domain.services.AlunoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import static org.hamcrest.Matchers.*;

public class AlunoServiceTest {
    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private AlunoService alunoService;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void whenAlunoInformedThenItShouldBeCreated() throws AlunoAlreadyRegisteredException{
        // given
        AlunoDTO expectedAlunoDTO = new AlunoDTOBuilder().builder().build().toAlunoDTO();
        Aluno expectedSavedAluno = AlunoMapper.toModel(expectedAlunoDTO);
        // when

        //Optional<Aluno> op= Optional.empty();
        when(alunoRepository.findByNome(expectedAlunoDTO.getNome())).thenReturn(Optional.empty());
        when(alunoRepository.save(expectedSavedAluno)).thenReturn(expectedSavedAluno);
        // then
        AlunoDTO createdAlunoDTO = alunoService.create(expectedAlunoDTO);

        assertThat(createdAlunoDTO.getId(),is(equalTo(expectedAlunoDTO.getId())));
        assertThat(createdAlunoDTO.getNome(),is(equalTo(expectedAlunoDTO.getNome())));
    }

    @Test
    void whenAlreadyRegistededAnExceptionShouldBeThrown() throws AlunoAlreadyRegisteredException{
        // given
        AlunoDTO expectedAlunoDTO = new AlunoDTOBuilder().builder().build().toAlunoDTO();
        Aluno duplicatedAluno = AlunoMapper.toModel(expectedAlunoDTO);
        // when
        when(alunoRepository.findByNome(expectedAlunoDTO.getNome())).thenReturn(Optional.of(duplicatedAluno));

        assertThrows(AlunoAlreadyRegisteredException.class,() -> alunoService.create(expectedAlunoDTO));
    }

}
