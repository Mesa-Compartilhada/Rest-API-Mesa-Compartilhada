package com.pi.mesacompartilhada.services;

import com.pi.mesacompartilhada.mapper.DoacaoMapper;
import com.pi.mesacompartilhada.models.Doacao;
import com.pi.mesacompartilhada.models.Empresa;
import com.pi.mesacompartilhada.records.doacao.*;
import com.pi.mesacompartilhada.repositories.DoacaoRepository;
import com.pi.mesacompartilhada.repositories.EmpresaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DoacaoServiceTest {

    @InjectMocks
    private DoacaoService doacaoService;

    @Mock
    private DoacaoRepository doacaoRepository;

    @Mock
    private EmpresaRepository empresaRepository;

    @Mock
    private DoacaoMapper doacaoMapper;

    @Mock
    private MediaStorageService mediaStorageService;

    private Doacao doacao;
    private Empresa empresa;
    private DoacaoRequestDto requestDto;
    private DoacaoResponseDto responseDto;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        empresa = new Empresa();
        empresa.setId("empresa1");

        doacao = new Doacao();
        doacao.setId("1");
        doacao.setNome("Arroz");

        requestDto = new DoacaoRequestDto(
                "Arroz",
                "Desc",
                "Obs",
                LocalDate.now(),
                LocalDate.now().plusDays(5),
                LocalDate.now(),
                LocalDate.now().plusDays(2),
                LocalTime.of(8, 0),
                LocalTime.of(18, 0),
                1,
                1,
                "empresa1",
                10.0,
                1,
                null
        );

        responseDto = new DoacaoResponseDto(
                "1",
                "Arroz",
                "Desc",
                "DISPONIVEL",
                "Obs",
                LocalDate.now(),
                LocalDate.now(),
                LocalDate.now(),
                null,
                LocalDate.now(),
                LocalTime.of(8, 0),
                LocalTime.of(18, 0),
                1,
                1,
                null,
                null,
                false,
                false,
                10.0,
                1,
                null
        );
    }

    // =========================
    // GET ALL
    // =========================
    @Test
    void shouldReturnAllDoacoes() {
        when(doacaoRepository.findAll()).thenReturn(List.of(doacao));
        when(doacaoMapper.doacaoToDoacaoDto(doacao)).thenReturn(responseDto);

        List<DoacaoResponseDto> result = doacaoService.getAllDoacoes();

        assertEquals(1, result.size());
        verify(doacaoRepository).findAll();
    }

    // =========================
    // GET BY ID
    // =========================
    @Test
    void shouldReturnDoacaoById() {
        when(doacaoRepository.findById("1")).thenReturn(Optional.of(doacao));
        when(doacaoMapper.doacaoToDoacaoDto(doacao)).thenReturn(responseDto);

        Optional<DoacaoResponseDto> result = doacaoService.getDoacaoById("1");

        assertTrue(result.isPresent());
    }

    @Test
    void shouldReturnEmptyWhenDoacaoNotFound() {
        when(doacaoRepository.findById("1")).thenReturn(Optional.empty());

        Optional<DoacaoResponseDto> result = doacaoService.getDoacaoById("1");

        assertTrue(result.isEmpty());
    }

    // =========================
    // ADD
    // =========================
    @Test
    void shouldAddDoacaoSuccessfully() {
        when(empresaRepository.findById("empresa1"))
                .thenReturn(Optional.of(empresa));

        when(doacaoRepository.save(any())).thenReturn(doacao);
        when(doacaoMapper.doacaoToDoacaoDto(any()))
                .thenReturn(responseDto);

        Optional<DoacaoResponseDto> result =
                doacaoService.addDoacao(requestDto);

        assertTrue(result.isPresent());
        verify(doacaoRepository).save(any());
    }

    @Test
    void shouldReturnEmptyWhenEmpresaNotFound() {
        when(empresaRepository.findById("empresa1"))
                .thenReturn(Optional.empty());

        Optional<DoacaoResponseDto> result =
                doacaoService.addDoacao(requestDto);

        assertTrue(result.isEmpty());
    }

    // =========================
    // UPDATE
    // =========================
    @Test
    void shouldUpdateDoacaoSuccessfully() {
        when(doacaoRepository.findById("1"))
                .thenReturn(Optional.of(doacao));

        when(empresaRepository.findById("empresa1"))
                .thenReturn(Optional.of(empresa));

        when(doacaoRepository.save(any())).thenReturn(doacao);
        when(doacaoMapper.doacaoToDoacaoDto(any()))
                .thenReturn(responseDto);

        Optional<DoacaoResponseDto> result =
                doacaoService.updateDoacao("1", requestDto);

        assertTrue(result.isPresent());
    }

    @Test
    void shouldReturnEmptyWhenUpdateFails() {
        when(doacaoRepository.findById("1"))
                .thenReturn(Optional.empty());

        Optional<DoacaoResponseDto> result =
                doacaoService.updateDoacao("1", requestDto);

        assertTrue(result.isEmpty());
    }

    // =========================
    // DELETE
    // =========================
    @Test
    void shouldDeleteDoacaoSuccessfully() {
        when(doacaoRepository.findById("1"))
                .thenReturn(Optional.of(doacao));

        when(doacaoMapper.doacaoToDoacaoDto(doacao))
                .thenReturn(responseDto);

        Optional<DoacaoResponseDto> result =
                doacaoService.deleteDoacao("1");

        assertTrue(result.isPresent());
        verify(doacaoRepository).deleteById("1");
    }

    @Test
    void shouldReturnEmptyWhenDeleteFails() {
        when(doacaoRepository.findById("1"))
                .thenReturn(Optional.empty());

        Optional<DoacaoResponseDto> result =
                doacaoService.deleteDoacao("1");

        assertTrue(result.isEmpty());
    }

    // =========================
    // FILTER
    // =========================
    @Test
    void shouldReturnFilteredDoacoes() {
        DoacaoFilter filter = new DoacaoFilter(
                List.of("DISPONIVEL"),
                null, null, null, null,
                null, null, null, null,
                null, null,
                null, null,
                null, null
        );

        when(doacaoRepository.findByFilter(filter))
                .thenReturn(List.of(doacao));

        when(doacaoMapper.doacaoToDoacaoDto(doacao))
                .thenReturn(responseDto);

        List<DoacaoResponseDto> result =
                doacaoService.getDoacoesByFilter(filter);

        assertEquals(1, result.size());
    }

}