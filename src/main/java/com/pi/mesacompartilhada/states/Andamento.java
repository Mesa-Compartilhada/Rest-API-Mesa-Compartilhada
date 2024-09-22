package com.pi.mesacompartilhada.states;

import com.pi.mesacompartilhada.models.Doacao;
import com.pi.mesacompartilhada.models.Empresa;

public class Andamento extends StateDoacao {

    public Andamento(Doacao doacao) {
        super(doacao);
    }

    @Override
    public String getStateName() {
        return "Andamento";
    }

    @Override
    public void solicitar(Empresa empresaRecebedora) {
        throw new UnsupportedOperationException("Essa doação já foi solicitada");
    }

    @Override
    public void concluir() {
        super.doacao.setStatus(new Concluida(super.doacao).getStateName());
    }

    @Override
    public void cancelarSolicitacao(Empresa empresaRecebedora) {
        if(empresaRecebedora == null) {
            throw new IllegalArgumentException("A empresa recebedora não pode ser nula");
        }
        // retira empresaRecebedora da doacao
        super.doacao.setEmpresaRecebedora(null);
        for(Doacao doacao : empresaRecebedora.getDoacoes()) {
            if(doacao.getId().equals(super.doacao.getId())) {
                empresaRecebedora.getDoacoes().remove(doacao);
                break;
            }
        }
        super.doacao.setStatus(new Disponivel(super.doacao).getStateName());
    }

    @Override
    public void cancelar() {
        // não faz nada
        throw new UnsupportedOperationException("Uma doação em andamento não pode ser cancelada");
    }
}
