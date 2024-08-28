package com.pi.mesacompartilhada.services;

import com.pi.mesacompartilhada.models.Doacao;
import com.pi.mesacompartilhada.records.DoacaoRecordDto;
import com.pi.mesacompartilhada.repositories.DoacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoacaoService {
    private final DoacaoRepository doacaoRepository;

    @Autowired
    public DoacaoService(DoacaoRepository doacaoRepository) {
        this.doacaoRepository = doacaoRepository;
    }

    public List<Doacao> getAllDoacoes() {
        return doacaoRepository.findAll();
    }

    public Optional<Doacao> getDoacaoById(String id) {
        return doacaoRepository.findById(id);
    }

    public Optional<Doacao> addDoacao(DoacaoRecordDto doacaoRecordDto) {
        Doacao doacao = new Doacao(doacaoRecordDto.nome(), doacaoRecordDto.descricao(), doacaoRecordDto.status(), doacaoRecordDto.observacao(), doacaoRecordDto.dataPostada(), doacaoRecordDto.dataEncerrada(), doacaoRecordDto.empresaDoadora(), doacaoRecordDto.empresaRecebedora());
        doacaoRepository.save(doacao);
        return Optional.of(doacao);
    }

    public Optional<Doacao> updateDoacao(String doacaoId, DoacaoRecordDto doacaoRecordDto) {
        Optional<Doacao> result = doacaoRepository.findById(doacaoId);
        if(result.isEmpty()) {
            return Optional.empty();
        }
        Doacao doacaoAtualizada = doacaoRepository.save(new Doacao(doacaoId,
                doacaoRecordDto.nome(),
                doacaoRecordDto.descricao(),
                doacaoRecordDto.status(),
                doacaoRecordDto.observacao(),
                doacaoRecordDto.dataPostada(),
                doacaoRecordDto.dataEncerrada(),
                doacaoRecordDto.empresaDoadora(),
                doacaoRecordDto.empresaRecebedora()));
        return Optional.of(doacaoAtualizada);
    }

    public Optional<Doacao> deleteDoacao(String doacaoId) {
        Optional<Doacao> result = getDoacaoById(doacaoId);
        if(result.isEmpty()) {
            return Optional.empty();
        }
        doacaoRepository.deleteById(doacaoId);
        return result;
    }
}
