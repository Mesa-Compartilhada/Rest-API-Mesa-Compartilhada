package com.pi.mesacompartilhada.states;

import com.pi.mesacompartilhada.exception.DoacaoStateIllegalArgumentException;
import com.pi.mesacompartilhada.exception.DoacaoStateOperationNotSupportedException;
import com.pi.mesacompartilhada.models.Doacao;
import com.pi.mesacompartilhada.models.Empresa;

import java.util.List;

// Estado inicial de cada doação
// Permite os métodos solicitar() e cancelar()
// Lança um erro nos métodos cancelarSolicitação() e concluir()

public class Disponivel extends StateDoacao {

    public Disponivel(Doacao doacao) {
        super(doacao);
    }

    @Override
    public String getStateName() {
        return "Disponivel";
    }

    @Override
    public void solicitar(Empresa empresaRecebedora) throws DoacaoStateOperationNotSupportedException, DoacaoStateIllegalArgumentException {
        if(empresaRecebedora == null) {
            throw new DoacaoStateIllegalArgumentException("A empresa recebedora não pode ser nula");
        }
        // tornar doacao solicitada/em andamento
        super.doacao.setEmpresaRecebedora(empresaRecebedora);
        List<Doacao> doacoesAtualizadas = empresaRecebedora.getDoacoes();
        doacoesAtualizadas.add(doacao);
        empresaRecebedora.setDoacoes(doacoesAtualizadas);
        doacao.setStatus(new Andamento(doacao).getStateName());
    }

    @Override
    public void concluir() throws DoacaoStateOperationNotSupportedException {
        throw new DoacaoStateOperationNotSupportedException("Uma doação disponível não pode ser concluída");
    }

    @Override
    public void cancelarSolicitacao(Empresa empresaRecebedora) throws DoacaoStateOperationNotSupportedException {
        throw new DoacaoStateOperationNotSupportedException("Não é possível cancelar a solicitação de uma doação ainda disponível");
    }

    @Override
    public void cancelar() throws DoacaoStateOperationNotSupportedException {
        // cancela doacao
        super.doacao.setStatus(new Cancelada(doacao).getStateName());
    }
}
