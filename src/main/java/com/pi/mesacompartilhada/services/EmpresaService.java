package com.pi.mesacompartilhada.services;

import com.pi.mesacompartilhada.enums.TipoEmpresa;
import com.pi.mesacompartilhada.models.Empresa;
import com.pi.mesacompartilhada.models.Endereco;
import com.pi.mesacompartilhada.models.PasswordToken;
import com.pi.mesacompartilhada.records.empresa.EmpresaLoginRequestDto;
import com.pi.mesacompartilhada.records.empresa.EmpresaRequestDto;
import com.pi.mesacompartilhada.records.empresa.EmpresaResetPasswordDto;
import com.pi.mesacompartilhada.records.empresa.EmpresaResponseDto;
import com.pi.mesacompartilhada.repositories.EmpresaRepository;
import com.pi.mesacompartilhada.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {
    private final EmpresaRepository empresaRepository;
    private final EnderecoRepository enderecoRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JWTService jwtService;

    @Autowired
    public EmpresaService(EmpresaRepository empresaRepository, EnderecoRepository enderecoRepository, TokenService tokenService) {
        this.empresaRepository = empresaRepository;
        this.enderecoRepository = enderecoRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(12);
        this.tokenService = tokenService;
    }

    public List<EmpresaResponseDto> getAllEmpresas() {
        List<Empresa> empresas = empresaRepository.findAll();
        List<EmpresaResponseDto> empresasDtos = new ArrayList<>();
        for(Empresa empresa : empresas) {
            if(empresa != null) {
                empresasDtos.add(Empresa.empresaToEmpresaResponseDto(empresa));
            }
        }
        return empresasDtos;
    }

    public Optional<EmpresaResponseDto> getEmpresaById(String empresaId) {
        Optional<Empresa> empresa = empresaRepository.findById(empresaId);
        if(empresa.isPresent()) {
            return Optional.of(Empresa.empresaToEmpresaResponseDto(empresa.get()));
        }
        return Optional.empty();
    }

    public Optional<EmpresaResponseDto> getEmpresaByEmail(String email) {
        Optional<Empresa> empresa = empresaRepository.findByEmail(email);
        if(empresa.isPresent()) {
            return Optional.of(Empresa.empresaToEmpresaResponseDto(empresa.get()));
        }
        return Optional.empty();
    }

    public Optional<EmpresaResponseDto> addEmpresa(EmpresaRequestDto empresaRequestDto) {
        Optional<Endereco> endereco = enderecoRepository.findById(empresaRequestDto.enderecoId());
        if(endereco.isPresent()) {
            Empresa empresa = new Empresa(empresaRequestDto.cnpj(),
                    TipoEmpresa.valueOf(empresaRequestDto.tipo()),
                    empresaRequestDto.categoria(),
                    empresaRequestDto.nome(),
                    empresaRequestDto.email(),
                    passwordEncoder.encode(empresaRequestDto.senha()),
                    endereco.get()
            );
            return Optional.of(Empresa.empresaToEmpresaResponseDto(empresaRepository.save(empresa)));
        }
        return Optional.empty();
    }

    public Optional<EmpresaResponseDto> updateEmpresa(String empresaId, EmpresaRequestDto empresaRequestDto) {
        Optional<Empresa> empresa = empresaRepository.findById(empresaId);
        Optional<Endereco> endereco = enderecoRepository.findById(empresaRequestDto.enderecoId());
        if(empresa.isPresent() && endereco.isPresent()) {
            Empresa empresaAtualizada = empresaRepository.save(new Empresa(empresaId,
                    empresaRequestDto.cnpj(),
                    TipoEmpresa.valueOf(empresaRequestDto.tipo()),
                    empresaRequestDto.categoria(),
                    empresaRequestDto.nome(),
                    empresaRequestDto.email(),
                    passwordEncoder.encode(empresaRequestDto.senha()),
                    endereco.get()
            ));
            return Optional.of(Empresa.empresaToEmpresaResponseDto(empresaAtualizada));
        }
        return Optional.empty();
    }

    public Optional<EmpresaResponseDto> deleteEmpresa(String empresaId) {
        Optional<Empresa> result = empresaRepository.findById(empresaId);
        if(result.isEmpty()) {
            return Optional.empty();
        }
        empresaRepository.deleteById(empresaId);
        return Optional.of(Empresa.empresaToEmpresaResponseDto(result.get()));
    }

    public Optional<String> login(EmpresaLoginRequestDto user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.email(), user.senha()));
        if(authentication.isAuthenticated()) {
            Optional<Empresa> authenticatedUser = empresaRepository.findByEmail(user.email());
            return Optional.of(jwtService.generateToken(authenticatedUser.get().getId()));
        }
        return Optional.empty();
    }

    public boolean resetPassword(EmpresaResetPasswordDto empresaResetPasswordDto) {
        String token = empresaResetPasswordDto.token();
        // verifica token
        if(tokenService.verificarToken(token)) {
            Optional<PasswordToken> passwordToken = tokenService.getToken(token);
            if(passwordToken.isPresent() && passwordToken.get().isStatus()) {
                // puxa o usuario do token
                Optional<Empresa> empresa = empresaRepository.findByEmail(passwordToken.get().getUserEmail());
                if(empresa.isPresent()) {
                    // atualiza senha
                    empresa.get().setSenha(passwordEncoder.encode(empresaResetPasswordDto.senha()));
                    empresaRepository.save(empresa.get());
                    tokenService.invalidarToken(passwordToken.get());
                    return true;
                }
            }
            tokenService.invalidarToken(passwordToken.get());
        }
        return false;
    }
}
