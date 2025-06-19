package com.pi.mesacompartilhada.services;

import com.pi.mesacompartilhada.models.Empresa;
import com.pi.mesacompartilhada.models.UserPrincipal;
import com.pi.mesacompartilhada.repositories.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private EmpresaRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public UserDetails loadUserById(String id) throws UsernameNotFoundException {
        Optional<Empresa> user = repo.findById(id);
        if(user.isEmpty()) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        return new UserPrincipal(user.get());
    }
}
