package com.pi.mesacompartilhada.states;

import com.pi.mesacompartilhada.exception.DoacaoStatusIllegalArgumentException;
import com.pi.mesacompartilhada.exception.DoacaoStatusOperationNotSupportedException;
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
        return "DISPONIVEL";
    }

    @Override
    public void solicitar(Empresa empresaRecebedora) throws DoacaoStatusOperationNotSupportedException, DoacaoStatusIllegalArgumentException {
        if(empresaRecebedora == null) {
            throw new DoacaoStatusIllegalArgumentException("A empresa recebedora não pode ser nula");
        }
        // tornar doacao solicitada/em andamento
        super.doacao.setEmpresaRecebedora(empresaRecebedora);
        List<Doacao> doacoesAtualizadas = empresaRecebedora.getDoacoes();
        doacoesAtualizadas.add(doacao);
        empresaRecebedora.setDoacoes(doacoesAtualizadas);
        doacao.setStatus(new Andamento(doacao).getStateName());
    }

    @Override
    public void concluir() throws DoacaoStatusOperationNotSupportedException {
        throw new DoacaoStatusOperationNotSupportedException("Uma doação disponível não pode ser concluída");
    }

    @Override
    public void cancelarSolicitacao(Empresa empresaRecebedora) throws DoacaoStatusOperationNotSupportedException {
        throw new DoacaoStatusOperationNotSupportedException("Não é possível cancelar a solicitação de uma doação ainda disponível");
    }

    @Override
    public void cancelar() throws DoacaoStatusOperationNotSupportedException {
        // cancela doacao
        super.doacao.setStatus(new Cancelada(doacao).getStateName());
    }
}
