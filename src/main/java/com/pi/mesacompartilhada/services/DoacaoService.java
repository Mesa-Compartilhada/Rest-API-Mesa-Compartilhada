package com.pi.mesacompartilhada.services;

import com.pi.mesacompartilhada.models.Doacao;
import com.pi.mesacompartilhada.models.Empresa;
import com.pi.mesacompartilhada.records.request.DoacaoRequestDto;
import com.pi.mesacompartilhada.records.response.DoacaoResponseDto;
import com.pi.mesacompartilhada.repositories.DoacaoRepository;
import com.pi.mesacompartilhada.repositories.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DoacaoService {
    private final DoacaoRepository doacaoRepository;
    private final EmpresaRepository empresaRepository;

    @Autowired
    public DoacaoService(DoacaoRepository doacaoRepository, EmpresaRepository empresaRepository) {
        this.doacaoRepository = doacaoRepository;
        this.empresaRepository = empresaRepository;
    }

    public List<DoacaoResponseDto> getAllDoacoes() {
        List<Doacao> doacoes = doacaoRepository.findAll();
        List<DoacaoResponseDto> doacoesDtos = new ArrayList<>();
        for(Doacao doacao : doacoes) {
            if(doacao != null) {
                doacoesDtos.add(Doacao.doacaoToDoacaoResponseDto(doacao));
            }
        }
        return doacoesDtos;
    }

    public Optional<DoacaoResponseDto> getDoacaoById(String id) {
        Optional<Doacao> doacao = doacaoRepository.findById(id);
        if(doacao.isPresent()) {
            return Optional.of(Doacao.doacaoToDoacaoResponseDto(doacao.get()));
        }
        return Optional.empty();
    }

    public Optional<DoacaoResponseDto> addDoacao(DoacaoRequestDto doacaoRequestDto) {
        Optional<Empresa> empresaDoadora = empresaRepository.findById(doacaoRequestDto.empresaDoadoraId());
        if(empresaDoadora.isPresent()) {
            Doacao doacao = new Doacao(
                    doacaoRequestDto.nome(),
                    doacaoRequestDto.descricao(),
                    doacaoRequestDto.status(),
                    doacaoRequestDto.observacao(),
                    doacaoRequestDto.dataPostada(),
                    doacaoRequestDto.dataEncerrada(),
                    empresaDoadora.get());
            doacaoRepository.save(doacao);

            // Atualizando lista de doações da empresa doadora
            // A anotação @DBRef no model mantém as referências sincronizadas com o documento
            // Porém, isso só ocorre com as atualizações
            // A operação de insert deve ser feita manualmente, como abaixo
            List<Doacao> doacoesDoadoraAtualizadas = empresaDoadora.get().getDoacoes();
            doacoesDoadoraAtualizadas.add(doacao);

            empresaDoadora.get().setDoacoes(doacoesDoadoraAtualizadas);

            empresaRepository.save(empresaDoadora.get());

            return Optional.of(Doacao.doacaoToDoacaoResponseDto(doacao));
        }
        return Optional.empty();
    }

    public Optional<DoacaoResponseDto> updateDoacao(String doacaoId, DoacaoRequestDto doacaoRequestDto) {
        Optional<Doacao> result = doacaoRepository.findById(doacaoId);
        // Retornando vazio caso a doação, a empresa doadora, ou a empresa recebedora da requisição não existam
        if(result.isEmpty()) {
            return Optional.empty();
        }
        Optional<Empresa> empresaDoadora = empresaRepository.findById(doacaoRequestDto.empresaDoadoraId());
        Optional<Empresa> empresaRecebedora = Optional.empty();
        if(empresaDoadora.isEmpty()) {
            return Optional.empty();
        }
        if(doacaoRequestDto.empresaRecebedoraId() != null) {
            empresaRecebedora = empresaRepository.findById(doacaoRequestDto.empresaRecebedoraId());
            if(empresaRecebedora.isEmpty()) {
                return Optional.empty();
            }
        }
        Doacao doacaoAtualizada = doacaoRepository.save(new Doacao(doacaoId,
                doacaoRequestDto.nome(),
                doacaoRequestDto.descricao(),
                doacaoRequestDto.status(),
                doacaoRequestDto.observacao(),
                doacaoRequestDto.dataPostada(),
                doacaoRequestDto.dataEncerrada(),
                empresaDoadora.get(),
                doacaoRequestDto.empresaRecebedoraId() != null ? empresaRecebedora.get() : null));

        if(doacaoRequestDto.empresaRecebedoraId() != null) {
            // Atualizando lista de doações da empresaRecebedora
            List<Doacao> doacoesRecebedoraAtualizada = empresaRecebedora.get().getDoacoes();
            doacoesRecebedoraAtualizada.add(doacaoAtualizada);
            empresaRecebedora.get().setDoacoes(doacoesRecebedoraAtualizada);
            empresaRepository.save(empresaRecebedora.get());
        }

        return Optional.of(Doacao.doacaoToDoacaoResponseDto(doacaoAtualizada));
    }

    public Optional<DoacaoResponseDto> deleteDoacao(String doacaoId) {
        Optional<DoacaoResponseDto> result = getDoacaoById(doacaoId);
        if(result.isEmpty()) {
            return Optional.empty();
        }
        doacaoRepository.deleteById(doacaoId);
        return result;
    }
}
