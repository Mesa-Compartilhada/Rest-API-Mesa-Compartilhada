package com.pi.mesacompartilhada.services;

import com.pi.mesacompartilhada.models.Endereco;
import com.pi.mesacompartilhada.records.request.EnderecoRequestDto;
import com.pi.mesacompartilhada.records.response.EnderecoResponseDto;
import com.pi.mesacompartilhada.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    @Autowired
    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    public List<EnderecoResponseDto> getAllEnderecos() {
        List<EnderecoResponseDto> enderecos = new ArrayList<>();
        for(Endereco endereco : enderecoRepository.findAll()) {
            enderecos.add(Endereco.enderecoToEnderecoResponseDto(endereco));
        }
        return enderecos;
    }

    public Optional<EnderecoResponseDto> getEnderecoById(String id) {
        Optional<Endereco> endereco = enderecoRepository.findById(id);
        if(endereco.isPresent()) {
            return Optional.of(Endereco.enderecoToEnderecoResponseDto(endereco.get()));
        }
        return Optional.empty();
    }

    public Optional<EnderecoResponseDto> getEnderecoByCep(String cep) {
        Optional<Endereco> endereco = enderecoRepository.findByCep(cep);
        if(endereco.isPresent()) {
            return Optional.of(Endereco.enderecoToEnderecoResponseDto(endereco.get()));
        }
        return Optional.empty();
    }

    public Optional<EnderecoResponseDto> addEndereco(EnderecoRequestDto enderecoRequestDto) {
        Endereco endereco = new Endereco(enderecoRequestDto.cep(), enderecoRequestDto.numero(), enderecoRequestDto.logradouro(), enderecoRequestDto.bairro(), enderecoRequestDto.cidade(), enderecoRequestDto.estado(), enderecoRequestDto.pais(), enderecoRequestDto.complemento(), enderecoRequestDto.latitude(), enderecoRequestDto.longitude());
        return Optional.of(Endereco.enderecoToEnderecoResponseDto(enderecoRepository.save(endereco)));
    }

    public Optional<EnderecoResponseDto> updateEndereco(String enderecoId, EnderecoRequestDto enderecoRequestDto) {
        Optional<Endereco> result = enderecoRepository.findById(enderecoId);
        if(result.isEmpty()) {
            return Optional.empty();
        }
        Endereco enderecoAtualizado = enderecoRepository.save(new Endereco(enderecoId,
                enderecoRequestDto.cep(),
                enderecoRequestDto.numero(),
                enderecoRequestDto.logradouro(),
                enderecoRequestDto.bairro(),
                enderecoRequestDto.cidade(),
                enderecoRequestDto.estado(),
                enderecoRequestDto.pais(),
                enderecoRequestDto.complemento(),
                enderecoRequestDto.latitude(),
                enderecoRequestDto.longitude()));
        return Optional.of(Endereco.enderecoToEnderecoResponseDto(enderecoAtualizado));
    }

    public Optional<EnderecoResponseDto> deleteEndereco(String enderecoId) {
        Optional<EnderecoResponseDto> result = getEnderecoById(enderecoId);
        if(result.isEmpty()) {
            return Optional.empty();
        }
        enderecoRepository.deleteById(enderecoId);
        return result;
    }

}
