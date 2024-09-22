package com.pi.mesacompartilhada.states;

import com.pi.mesacompartilhada.models.Doacao;
import com.pi.mesacompartilhada.models.Empresa;

import java.util.List;

public class Disponivel extends StateDoacao {

    public Disponivel(Doacao doacao) {
        super(doacao);
    }

    @Override
    public String getStateName() {
        return "Disponivel";
    }

    @Override
    public void solicitar(Empresa empresaRecebedora) {
        if(empresaRecebedora == null) {
            throw new IllegalArgumentException("A empresa recebedora não pode ser nula");
        }
        // tornar doacao solicitada/em andamento
        super.doacao.setEmpresaRecebedora(empresaRecebedora);
        List<Doacao> doacoesAtualizadas = empresaRecebedora.getDoacoes();
        doacoesAtualizadas.add(doacao);
        empresaRecebedora.setDoacoes(doacoesAtualizadas);
        doacao.setStatus(new Andamento(doacao).getStateName());
    }

    @Override
    public void concluir() {
        throw new UnsupportedOperationException("Uma doação disponível não pode ser concluída");
    }

    @Override
    public void cancelarSolicitacao(Empresa empresaRecebedora) {
        throw new UnsupportedOperationException("Não é possível cancelar a solicitação de uma doação ainda disponível");
    }

    @Override
    public void cancelar() {
        // cancela doacao
        super.doacao.setStatus(new Cancelada(doacao).getStateName());
    }
}
