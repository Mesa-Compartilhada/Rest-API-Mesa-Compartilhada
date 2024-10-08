package com.pi.mesacompartilhada.states;

import com.pi.mesacompartilhada.exception.DoacaoStatusIllegalArgumentException;
import com.pi.mesacompartilhada.exception.DoacaoStatusOperationNotSupportedException;
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
        return "ANDAMENTO";
    }

    @Override
    public void solicitar(Empresa empresaRecebedora) throws DoacaoStatusOperationNotSupportedException {
        throw new DoacaoStatusOperationNotSupportedException("Essa doação já foi solicitada");
    }

    @Override
    public void concluir() throws DoacaoStatusOperationNotSupportedException {
        super.doacao.setStatus(new Concluida(super.doacao).getStateName());
    }

    @Override
    public void cancelarSolicitacao(Empresa empresaRecebedora) throws DoacaoStatusOperationNotSupportedException, DoacaoStatusIllegalArgumentException {
        if(empresaRecebedora == null) {
            throw new DoacaoStatusIllegalArgumentException("A empresa recebedora não pode ser nula");
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
    public void cancelar() throws DoacaoStatusOperationNotSupportedException {
        // não faz nada
        throw new DoacaoStatusOperationNotSupportedException("Uma doação em andamento não pode ser cancelada");
    }
}
