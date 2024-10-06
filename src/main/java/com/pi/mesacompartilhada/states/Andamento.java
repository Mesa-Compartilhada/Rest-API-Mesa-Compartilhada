package com.pi.mesacompartilhada.states;

import com.pi.mesacompartilhada.exception.DoacaoStateIllegalArgumentException;
import com.pi.mesacompartilhada.exception.DoacaoStateOperationNotSupportedException;
import com.pi.mesacompartilhada.models.Doacao;
import com.pi.mesacompartilhada.models.Empresa;

// Estado de uma doação que foi solicitado pela empresa recebedora
// Permite os métodos cancelarSolicitação() e concluir()
// Lança um erro nos métodos solicitar() e cancelar()

public class Andamento extends StateDoacao {

    public Andamento(Doacao doacao) {
        super(doacao);
    }

    @Override
    public String getStateName() {
        return "Andamento";
    }

    @Override
    public void solicitar(Empresa empresaRecebedora) throws DoacaoStateOperationNotSupportedException {
        throw new DoacaoStateOperationNotSupportedException("Essa doação já foi solicitada");
    }

    @Override
    public void concluir() throws DoacaoStateOperationNotSupportedException {
        super.doacao.setStatus(new Concluida(super.doacao).getStateName());
    }

    @Override
    public void cancelarSolicitacao(Empresa empresaRecebedora) throws DoacaoStateOperationNotSupportedException, DoacaoStateIllegalArgumentException {
        if(empresaRecebedora == null) {
            throw new DoacaoStateIllegalArgumentException("A empresa recebedora não pode ser nula");
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
    public void cancelar() throws DoacaoStateOperationNotSupportedException {
        // não faz nada
        throw new DoacaoStateOperationNotSupportedException("Uma doação em andamento não pode ser cancelada");
    }
}
