package com.pi.mesacompartilhada.states;

import com.pi.mesacompartilhada.exception.DoacaoStateOperationNotSupportedException;
import com.pi.mesacompartilhada.models.Doacao;
import com.pi.mesacompartilhada.models.Empresa;

// Estado de uma doação solicitada que já foi entregue
// Não permite nenhum dos métodos, lançando erros descritivos

public class Concluida extends StateDoacao {

    public Concluida(Doacao doacao) {
        super(doacao);
    }

    @Override
    public String getStateName() {
        return "Concluida";
    }

    @Override
    public void solicitar(Empresa empresaRecebedora) throws DoacaoStateOperationNotSupportedException {
        throw new DoacaoStateOperationNotSupportedException("Uma doação concluída não pode ser solicitada");
    }

    @Override
    public void concluir() throws DoacaoStateOperationNotSupportedException {
        throw new DoacaoStateOperationNotSupportedException("Essa doação já foi concluída");
    }

    @Override
    public void cancelarSolicitacao(Empresa empresaRecebedora) throws DoacaoStateOperationNotSupportedException {
        throw new DoacaoStateOperationNotSupportedException("Uma doação concluída não pode ter sua solicitação cancelada");
    }

    @Override
    public void cancelar() throws DoacaoStateOperationNotSupportedException {
        throw new DoacaoStateOperationNotSupportedException("Uma doação concluída não pode ser concluída");
    }
}
