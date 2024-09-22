package com.pi.mesacompartilhada.states;

import com.pi.mesacompartilhada.models.Doacao;
import com.pi.mesacompartilhada.models.Empresa;

public class Cancelada extends StateDoacao {

    public Cancelada(Doacao doacao) {
        super(doacao);
    }

    @Override
    public String getStateName() {
        return "Cancelada";
    }

    @Override
    public void solicitar(Empresa empresaRecebedora) {
        throw new UnsupportedOperationException("Uma doação cancelada não pode ser solicitada");
    }

    @Override
    public void concluir() {
        throw new UnsupportedOperationException("Uma doação cancelada não pode ser concluída");
    }

    @Override
    public void cancelarSolicitacao(Empresa empresaRecebedora) {
        throw new UnsupportedOperationException("Uma doação cancelada não pode ter sua solicitação cancelada");
    }

    @Override
    public void cancelar() {
        throw new UnsupportedOperationException("Essa doação já foi cancelada");
    }
}
