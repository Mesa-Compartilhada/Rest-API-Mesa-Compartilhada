package com.pi.mesacompartilhada.states;

import com.pi.mesacompartilhada.exception.DoacaoStateOperationNotSupportedException;
import com.pi.mesacompartilhada.models.Doacao;
import com.pi.mesacompartilhada.models.Empresa;

// Estado de uma doação que estava disponivel e foi cancelada
// Não permite nenhum dos métodos, lançando erros descritivos

public class Cancelada extends StateDoacao {

    public Cancelada(Doacao doacao) {
        super(doacao);
    }

    @Override
    public String getStateName() {
        return "Cancelada";
    }

    @Override
    public void solicitar(Empresa empresaRecebedora) throws DoacaoStateOperationNotSupportedException {
        throw new DoacaoStateOperationNotSupportedException("Uma doação cancelada não pode ser solicitada");
    }

    @Override
    public void concluir() throws DoacaoStateOperationNotSupportedException {
        throw new DoacaoStateOperationNotSupportedException("Uma doação cancelada não pode ser concluída");
    }

    @Override
    public void cancelarSolicitacao(Empresa empresaRecebedora) throws DoacaoStateOperationNotSupportedException {
        throw new DoacaoStateOperationNotSupportedException("Uma doação cancelada não pode ter sua solicitação cancelada");
    }

    @Override
    public void cancelar() throws DoacaoStateOperationNotSupportedException {
        throw new DoacaoStateOperationNotSupportedException("Essa doação já foi cancelada");
    }
}
