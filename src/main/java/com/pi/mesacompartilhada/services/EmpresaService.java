package com.pi.mesacompartilhada.services;

import com.pi.mesacompartilhada.models.Empresa;
import com.pi.mesacompartilhada.models.Endereco;
import com.pi.mesacompartilhada.records.EmpresaRecordDto;
import com.pi.mesacompartilhada.repositories.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {
    private final EmpresaRepository empresaRepository;
    private final EnderecoService enderecoService;

    @Autowired
    public EmpresaService(EmpresaRepository empresaRepository, EnderecoService enderecoService) {
        this.empresaRepository = empresaRepository;
        this.enderecoService = enderecoService;
    }

    public List<Empresa> getAllEmpresas() {
        return empresaRepository.findAll();
    }

    public Optional<Empresa> getEmpresaById(String empresaId) {
        return empresaRepository.findById(empresaId);
    }

    public Optional<Empresa> addEmpresa(EmpresaRecordDto empresaRecordDto) {
        Optional<Endereco> endereco = enderecoService.getEnderecoById(empresaRecordDto.enderecoId());
        if(endereco.isPresent()) {
            Empresa empresa = new Empresa(empresaRecordDto.cnpj(),
                    empresaRecordDto.tipo(),
                    empresaRecordDto.nome(),
                    empresaRecordDto.email(),
                    empresaRecordDto.senha(),
                    empresaRecordDto.status(),
                    endereco.get()
            );
            return Optional.of(empresaRepository.save(empresa));
        }
        return Optional.empty();
    }

    public Optional<Empresa> updateEmpresa(String empresaId, EmpresaRecordDto empresaRecordDto) {
        Optional<Endereco> endereco = enderecoService.getEnderecoById(empresaRecordDto.enderecoId());
        if(endereco.isPresent()) {
            Empresa empresaAtualizada = empresaRepository.save(new Empresa(empresaId,
                    empresaRecordDto.cnpj(),
                    empresaRecordDto.tipo(),
                    empresaRecordDto.nome(),
                    empresaRecordDto.email(),
                    empresaRecordDto.senha(),
                    empresaRecordDto.status(),
                    endereco.get()
            ));
            return Optional.of(empresaAtualizada);
        }
        return Optional.empty();
    }

    public Optional<Empresa> deleteEmpresa(String empresaId) {
        Optional<Empresa> result = getEmpresaById(empresaId);
        if(result.isEmpty()) {
            return Optional.empty();
        }
        empresaRepository.deleteById(empresaId);
        return result;
    }
}
