package com.pi.mesacompartilhada.services;

import com.pi.mesacompartilhada.enums.TipoAlimento;
import com.pi.mesacompartilhada.enums.TipoArmazenamento;
import com.pi.mesacompartilhada.enums.UnidadeMedida;
import com.pi.mesacompartilhada.exception.DoacaoStatusIllegalArgumentException;
import com.pi.mesacompartilhada.exception.DoacaoStatusOperationNotSupportedException;
import com.pi.mesacompartilhada.mapper.DoacaoMapper;
import com.pi.mesacompartilhada.models.Doacao;
import com.pi.mesacompartilhada.models.Empresa;
import com.pi.mesacompartilhada.records.doacao.DoacaoFilter;
import com.pi.mesacompartilhada.records.doacao.DoacaoRequestDto;
import com.pi.mesacompartilhada.records.doacao.DoacaoStateRequestDto;
import com.pi.mesacompartilhada.records.doacao.DoacaoResponseDto;
import com.pi.mesacompartilhada.repositories.DoacaoRepository;
import com.pi.mesacompartilhada.repositories.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DoacaoService {
    private final DoacaoRepository doacaoRepository;
    private final EmpresaRepository empresaRepository;
    private final DoacaoMapper doacaoMapper;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public DoacaoService(DoacaoRepository doacaoRepository, EmpresaRepository empresaRepository, DoacaoMapper doacaoMapper, CloudinaryService cloudinaryService) {
        this.doacaoRepository = doacaoRepository;
        this.empresaRepository = empresaRepository;
        this.doacaoMapper = doacaoMapper;
        this.cloudinaryService = cloudinaryService;
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

    public List<DoacaoResponseDto> getDoacoesByEmpresaDoadoraId(String id) {
        List<Doacao> doacoes = doacaoRepository.findByEmpresaDoadoraId(id);
        List<DoacaoResponseDto> doacoesDtos = new ArrayList<>();
        for(Doacao doacao : doacoes) {
            doacoesDtos.add(Doacao.doacaoToDoacaoResponseDto(doacao));
        }
        return doacoesDtos;
    }

    public List<DoacaoResponseDto> getDoacoesByEmpresaRecebedoraId(String id) {
        List<Doacao> doacoes = doacaoRepository.findByEmpresaRecebedoraId(id);
        List<DoacaoResponseDto> doacoesDtos = new ArrayList<>();
        for(Doacao doacao : doacoes) {
            doacoesDtos.add(Doacao.doacaoToDoacaoResponseDto(doacao));
        }
        return doacoesDtos;
    }

    public List<DoacaoResponseDto> getDoacoesByStatus(String status) {
        List<Doacao> doacoes = doacaoRepository.findByStatus(status.toUpperCase());
        List<DoacaoResponseDto> doacoesDtos = new ArrayList<>();
        for(Doacao doacao : doacoes) {
            doacoesDtos.add(Doacao.doacaoToDoacaoResponseDto(doacao));
        }
        return doacoesDtos;
    }

    public List<DoacaoResponseDto> getDoacoesByStatusAndEmpresaDoadoraId(String status, String empresaDoadoraId) {
        List<Doacao> doacoes = doacaoRepository.findByStatusAndEmpresaDoadoraId(status.toUpperCase(), empresaDoadoraId);
        List<DoacaoResponseDto> doacoesDtos = new ArrayList<>();
        for(Doacao doacao : doacoes) {
            doacoesDtos.add(Doacao.doacaoToDoacaoResponseDto(doacao));
        }
        return doacoesDtos;
    }

    public List<DoacaoResponseDto> getDoacoesByStatusAndEmpresaRecebedoraId(String status, String empresaRecebedoraId) {
        List<Doacao> doacoes = doacaoRepository.findByStatusAndEmpresaRecebedoraId(status.toUpperCase(), empresaRecebedoraId);
        List<DoacaoResponseDto> doacoesDtos = new ArrayList<>();
        for(Doacao doacao : doacoes) {
            doacoesDtos.add(Doacao.doacaoToDoacaoResponseDto(doacao));
        }
        return doacoesDtos;
    }

    public List<DoacaoResponseDto> getDoacoesByFilter(DoacaoFilter filter) {
        List<Doacao> doacoesFiltradas = doacaoRepository.findByFilter(filter);
        List<DoacaoResponseDto> doacoesDtos = new ArrayList<>();
        for(Doacao doacao : doacoesFiltradas) {
            doacoesDtos.add((doacaoMapper.doacaoToDoacaoDto(doacao)));
        }
        return doacoesDtos;
    }

    public Optional<DoacaoResponseDto> addDoacao(DoacaoRequestDto doacaoRequestDto) {
        Optional<Empresa> empresaDoadora = empresaRepository.findById(doacaoRequestDto.empresaDoadoraId());
        byte[] imagemCapa = Base64.getDecoder().decode(doacaoRequestDto.imagemCapa());
        Map imagemCapaMap = cloudinaryService.uploadFile(imagemCapa);
        if(empresaDoadora.isPresent()) {
            Doacao doacao = new Doacao(
                    doacaoRequestDto.nome(),
                    doacaoRequestDto.descricao(),
                    doacaoRequestDto.observacao(),
                    doacaoRequestDto.dataFabricacao(),
                    doacaoRequestDto.dataValidade(),
                    doacaoRequestDto.dataCriada(),
                    doacaoRequestDto.dataMaxRetirada(),
                    doacaoRequestDto.horarioMin(),
                    doacaoRequestDto.horarioMax(),
                    TipoAlimento.valueOf(doacaoRequestDto.tipoAlimento()),
                    TipoArmazenamento.valueOf(doacaoRequestDto.tipoArmazenamento()),
                    empresaDoadora.get(),
                    doacaoRequestDto.quantidade(),
                    UnidadeMedida.valueOf(doacaoRequestDto.unidadeMedida()),
                    imagemCapaMap.get("secure_url").toString()
            );
            doacaoRepository.save(doacao);

            // Atualizando lista de doações da empresa doadora
            // A anotação @DBRef no model mantém as referências sincronizadas com o documento
            // Porém, isso só ocorre com as atualizações
            // A operação de insert deve ser feita manualmente, como abaixo
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
        if(empresaDoadora.isEmpty()) {
            return Optional.empty();
        }
        Doacao doacaoAtualizada = doacaoRepository.save(new Doacao(doacaoId,
                doacaoRequestDto.nome(),
                doacaoRequestDto.descricao(),
                doacaoRequestDto.observacao(),
                doacaoRequestDto.dataFabricacao(),
                doacaoRequestDto.dataValidade(),
                doacaoRequestDto.dataCriada(),
                doacaoRequestDto.dataMaxRetirada(),
                doacaoRequestDto.horarioMin(),
                doacaoRequestDto.horarioMax(),
                TipoAlimento.valueOf(doacaoRequestDto.tipoAlimento()),
                TipoArmazenamento.valueOf(doacaoRequestDto.tipoArmazenamento()),
                empresaDoadora.get(),
                result.get().getEmpresaRecebedora(),
                doacaoRequestDto.quantidade(),
                UnidadeMedida.valueOf(doacaoRequestDto.unidadeMedida()),
                result.get().getImagemCapa())
        );

        return Optional.of(Doacao.doacaoToDoacaoResponseDto(doacaoAtualizada));
    }

    public Optional<DoacaoResponseDto> updateDoacaoState(String doacaoId, DoacaoStateRequestDto doacaoStateRequestDto) throws DoacaoStatusOperationNotSupportedException, DoacaoStatusIllegalArgumentException {
        Optional<Doacao> doacao = doacaoRepository.findById(doacaoId);
        Empresa empresaRecebedora = null;
        Empresa empresaSolicitante = null;
        if(doacaoStateRequestDto.empresaRecebedoraId() != null) {
            Optional<Empresa> result = empresaRepository.findById(doacaoStateRequestDto.empresaRecebedoraId());
            if(result.isPresent()) empresaRecebedora = result.get();
        }
        if(doacaoStateRequestDto.empresaSolicitanteId() != null) {
            Optional<Empresa> result = empresaRepository.findById(doacaoStateRequestDto.empresaSolicitanteId());
            if(result.isPresent()) empresaSolicitante = result.get();
        }
        if(doacao.isPresent()) {
            Doacao doacaoAtualizada = doacao.get();
            switch(doacaoStateRequestDto.status().toUpperCase()) {
                case "DISPONIVEL":
                    doacaoAtualizada.getStatus().cancelarSolicitacao(empresaRecebedora);
                    if(empresaRecebedora != null) {
                        empresaRepository.save(empresaRecebedora);
                    }
                    break;
                case "ANDAMENTO":
                    doacaoAtualizada.getStatus().solicitar(empresaRecebedora);
                    if(empresaRecebedora != null) {
                        empresaRepository.save(empresaRecebedora);
                    }
                    break;
                case "CONCLUIDA":
                    doacaoAtualizada.getStatus().concluir(empresaSolicitante);
                    break;
                case "CANCELADA":
                    doacaoAtualizada.getStatus().cancelar();
                    break;
                default:
                    throw new DoacaoStatusOperationNotSupportedException("Status de doação inválido");
            }

            doacaoRepository.save(doacaoAtualizada);

            return Optional.of(Doacao.doacaoToDoacaoResponseDto(doacao.get()));
        }
        return Optional.empty();
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
