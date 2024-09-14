package com.pi.mesacompartilhada.services;

import com.pi.mesacompartilhada.models.Doacao;
import com.pi.mesacompartilhada.models.Empresa;
import com.pi.mesacompartilhada.records.DoacaoRecordDto;
import com.pi.mesacompartilhada.records.EmpresaRecordDto;
import com.pi.mesacompartilhada.repositories.DoacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoacaoService {
    private final DoacaoRepository doacaoRepository;
    private final EmpresaService empresaService;

    @Autowired
    public DoacaoService(DoacaoRepository doacaoRepository, EmpresaService empresaService) {
        this.doacaoRepository = doacaoRepository;
        this.empresaService = empresaService;
    }

    public List<Doacao> getAllDoacoes() {
        return doacaoRepository.findAll();
    }

    public Optional<Doacao> getDoacaoById(String id) {
        return doacaoRepository.findById(id);
    }

    public Optional<Doacao> addDoacao(DoacaoRecordDto doacaoRecordDto) {
        Optional<Empresa> empresaDoadora = empresaService.getEmpresaById(doacaoRecordDto.empresaDoadoraId());
        if(empresaDoadora.isPresent()) {
            Doacao doacao = new Doacao(doacaoRecordDto.nome(), doacaoRecordDto.descricao(), doacaoRecordDto.status(), doacaoRecordDto.observacao(), doacaoRecordDto.dataPostada(), doacaoRecordDto.dataEncerrada(), empresaDoadora.get());
            doacaoRepository.save(doacao);

            // Atualizando lista de doações da empresa doadora
            // A anotação @DBRef no model mantém as referências sincronizadas com o documento
            // Porém, isso só ocorre com as atualizações
            // A operação de insert deve ser feita manualmente, como abaixo
            List<Doacao> doacoesDoadoraAtualizadas = empresaDoadora.get().getDoacoes();
            doacoesDoadoraAtualizadas.add(doacao);

            EmpresaRecordDto empresaDoadoraAtualizada = new EmpresaRecordDto(
                    empresaDoadora.get().getCnpj(),
                    empresaDoadora.get().getTipo(),
                    empresaDoadora.get().getCategoria(),
                    empresaDoadora.get().getNome(),
                    empresaDoadora.get().getEmail(),
                    empresaDoadora.get().getSenha(),
                    empresaDoadora.get().getStatus(),
                    empresaDoadora.get().getEndereco().getId(),
                    doacoesDoadoraAtualizadas
            );
            empresaService.updateEmpresa(empresaDoadora.get().getId(), empresaDoadoraAtualizada);

            return Optional.of(doacao);
        }
        return Optional.empty();
    }

    public Optional<Doacao> updateDoacao(String doacaoId, DoacaoRecordDto doacaoRecordDto) {
        Optional<Doacao> result = doacaoRepository.findById(doacaoId);
        // Retornando vazio caso a doação, a empresa doadora, ou a empresa recebedora da requisição não existam
        if(result.isEmpty()) {
            return Optional.empty();
        }
        Optional<Empresa> empresaDoadora = empresaService.getEmpresaById(doacaoRecordDto.empresaDoadoraId());
        Optional<Empresa> empresaRecebedora = Optional.empty();
        if(empresaDoadora.isEmpty()) {
            return Optional.empty();
        }
        if(doacaoRecordDto.empresaRecebedoraId() != null) {
            empresaRecebedora = empresaService.getEmpresaById(doacaoRecordDto.empresaRecebedoraId());
            if(empresaRecebedora.isEmpty()) {
                return Optional.empty();
            }
        }
        Doacao doacaoAtualizada = doacaoRepository.save(new Doacao(doacaoId,
                doacaoRecordDto.nome(),
                doacaoRecordDto.descricao(),
                doacaoRecordDto.status(),
                doacaoRecordDto.observacao(),
                doacaoRecordDto.dataPostada(),
                doacaoRecordDto.dataEncerrada(),
                empresaDoadora.get(),
                doacaoRecordDto.empresaRecebedoraId() != null ? empresaRecebedora.get() : null));

        if(doacaoRecordDto.empresaRecebedoraId() != null) {
            // Atualizando lista de doações da empresaRecebedora
            List<Doacao> doacoesRecebedoraAtualizada = empresaRecebedora.get().getDoacoes();
            doacoesRecebedoraAtualizada.add(doacaoAtualizada);
            EmpresaRecordDto empresaRecebedoraAtualizada = new EmpresaRecordDto(
                    empresaRecebedora.get().getCnpj(),
                    empresaRecebedora.get().getTipo(),
                    empresaRecebedora.get().getCategoria(),
                    empresaRecebedora.get().getNome(),
                    empresaRecebedora.get().getEmail(),
                    empresaRecebedora.get().getSenha(),
                    empresaRecebedora.get().getStatus(),
                    empresaRecebedora.get().getEndereco().getId(),
                    doacoesRecebedoraAtualizada
            );
            empresaService.updateEmpresa(empresaRecebedora.get().getId(), empresaRecebedoraAtualizada);
        }

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
