package com.pi.mesacompartilhada.states;

import com.pi.mesacompartilhada.models.Doacao;
import com.pi.mesacompartilhada.models.Empresa;

public abstract class StateDoacao {

    protected Doacao doacao;

    public StateDoacao(Doacao doacao) {
        this.doacao = doacao;
    }

    public abstract String getStateName();

    public abstract void solicitar(Empresa empresaRecebedora);

    public abstract void concluir();

    public abstract void cancelarSolicitacao(Empresa empresaRecebedora);

    public abstract void cancelar();

}
