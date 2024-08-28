package com.pi.mesacompartilhada.services;

import com.pi.mesacompartilhada.models.Endereco;
import com.pi.mesacompartilhada.records.EnderecoRecordDto;
import com.pi.mesacompartilhada.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    @Autowired
    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    public List<Endereco> getAllEnderecos() {
        return enderecoRepository.findAll();
    }

    public Optional<Endereco> getEnderecoById(String id) {
        return enderecoRepository.findById(id);
    }

    public Optional<Endereco> getEnderecoByCep(String cep) {
        return enderecoRepository.findByCep(cep);
    }

    public Optional<Endereco> addEndereco(EnderecoRecordDto enderecoRecordDto) {
        Endereco endereco = new Endereco(enderecoRecordDto.cep(), enderecoRecordDto.numero(), enderecoRecordDto.logradouro(), enderecoRecordDto.bairro(), enderecoRecordDto.cidade(), enderecoRecordDto.estado(), enderecoRecordDto.pais(), enderecoRecordDto.complemento());
        return Optional.of(enderecoRepository.save(endereco));
    }

    public Optional<Endereco> updateEndereco(String enderecoId, EnderecoRecordDto enderecoRecordDto) {
        Optional<Endereco> result = enderecoRepository.findById(enderecoId);
        if(result.isEmpty()) {
            return Optional.empty();
        }
        Endereco enderecoAtualizado = enderecoRepository.save(new Endereco(enderecoId,
                enderecoRecordDto.cep(),
                enderecoRecordDto.numero(),
                enderecoRecordDto.logradouro(),
                enderecoRecordDto.bairro(),
                enderecoRecordDto.cidade(),
                enderecoRecordDto.estado(),
                enderecoRecordDto.pais(),
                enderecoRecordDto.complemento()));
        return Optional.of(enderecoAtualizado);
    }

    public Optional<Endereco> deleteEndereco(String enderecoId) {
        Optional<Endereco> result = getEnderecoById(enderecoId);
        if(result.isEmpty()) {
            return Optional.empty();
        }
        enderecoRepository.deleteById(enderecoId);
        return result;
    }

}
