package com.works.domain.security;

import com.works.domain.model.Aluno;
import com.works.domain.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    AlunoRepository alunoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("springuser".equals(username) || alunoRepository.getByUsuarioContaining(username).isPresent()) {
            if (alunoRepository.getByUsuarioContaining(username).isPresent()){

                Aluno aluno = alunoRepository.getByUsuarioContaining(username).get();
                return new User(aluno.getUsuario(), aluno.getSenha(),
                        new ArrayList<>());
            }
            return new User("springuser", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}