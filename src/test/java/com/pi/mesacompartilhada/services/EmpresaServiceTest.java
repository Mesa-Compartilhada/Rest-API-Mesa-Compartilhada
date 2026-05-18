package com.pi.mesacompartilhada.services;

import com.pi.mesacompartilhada.mapper.EmpresaMapper;
import com.pi.mesacompartilhada.models.Empresa;
import com.pi.mesacompartilhada.models.Endereco;
import com.pi.mesacompartilhada.records.empresa.EmpresaRequestDto;
import com.pi.mesacompartilhada.records.empresa.EmpresaResponseDto;
import com.pi.mesacompartilhada.repositories.EmpresaRepository;
import com.pi.mesacompartilhada.repositories.EnderecoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class EmpresaServiceTest {

    @Mock
    private EmpresaRepository empresaRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private TokenService tokenService;

    @Mock
    private MediaStorageService mediaStorageService;

    @Mock
    private EmpresaMapper empresaMapper;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTService jwtService;

    @InjectMocks
    private EmpresaService empresaService;

    @Test
    void shouldReturnAllEmpresas() {
        Empresa empresa = new Empresa();
        List<Empresa> empresas = List.of(empresa);

        when(empresaRepository.findAll()).thenReturn(empresas);
        when(empresaMapper.empresaToEmpresaDto(any())).thenReturn(mock(EmpresaResponseDto.class));

        List<EmpresaResponseDto> result = empresaService.getAllEmpresas();

        assertEquals(1, result.size());
        verify(empresaRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnEmpresaById() {
        Empresa empresa = new Empresa();
        empresa.setId("1");

        when(empresaRepository.findById("1")).thenReturn(Optional.of(empresa));
        when(empresaMapper.empresaToEmpresaDto(empresa)).thenReturn(mock(EmpresaResponseDto.class));

        Optional<EmpresaResponseDto> result = empresaService.getEmpresaById("1");

        assertTrue(result.isPresent());
    }

    @Test
    void shouldReturnEmptyWhenEmpresaNotFound() {
        when(empresaRepository.findById("1")).thenReturn(Optional.empty());

        Optional<EmpresaResponseDto> result = empresaService.getEmpresaById("1");

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldAddEmpresaSuccessfully() {
        Endereco endereco = new Endereco();

        EmpresaRequestDto dto = new EmpresaRequestDto(
                "12345678000195",
                1,
                1,
                "Empresa Teste",
                "teste@email.com",
                "12345678",
                "enderecoId",
                null
        );

        when(enderecoRepository.findById("enderecoId")).thenReturn(Optional.of(endereco));
        when(empresaRepository.save(any())).thenReturn(new Empresa());
        when(empresaMapper.empresaToEmpresaDto(any())).thenReturn(mock(EmpresaResponseDto.class));

        Optional<EmpresaResponseDto> result = empresaService.addEmpresa(dto);

        assertTrue(result.isPresent());
        verify(empresaRepository, times(1)).save(any());
    }

    @Test
    void shouldReturnEmptyWhenEnderecoNotFound() {
        EmpresaRequestDto dto = new EmpresaRequestDto(
                "12345678000195",
                1,
                1,
                "Empresa Teste",
                "teste@email.com",
                "12345678",
                "enderecoId",
                null
        );

        when(enderecoRepository.findById("enderecoId")).thenReturn(Optional.empty());

        Optional<EmpresaResponseDto> result = empresaService.addEmpresa(dto);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldDeleteEmpresa() {
        Empresa empresa = new Empresa();
        empresa.setId("1");

        when(empresaRepository.findById("1")).thenReturn(Optional.of(empresa));
        when(empresaMapper.empresaToEmpresaDto(empresa)).thenReturn(mock(EmpresaResponseDto.class));

        Optional<EmpresaResponseDto> result = empresaService.deleteEmpresa("1");

        assertTrue(result.isPresent());
        verify(empresaRepository).deleteById("1");
    }

    @Test
    void shouldReturnFalseWhenUpdatingPasswordAndUserNotFound() {
        when(empresaRepository.findById("1")).thenReturn(Optional.empty());

        boolean result = empresaService.updatePassword("1", "123", "456");

        assertFalse(result);
    }

    @Test
    void shouldConvertBase64ToFile() {
        String base64 = Base64.getEncoder().encodeToString("teste".getBytes());

        byte[] result = empresaService.convertB64ToFile(base64);

        assertNotNull(result);
        assertTrue(result.length > 0);
    }
}