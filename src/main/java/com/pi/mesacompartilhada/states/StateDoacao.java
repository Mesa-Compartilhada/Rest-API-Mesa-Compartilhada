package com.pi.mesacompartilhada.states;

import com.pi.mesacompartilhada.models.Doacao;
import com.pi.mesacompartilhada.models.Empresa;

// Classe StateDoacao seguindo o padrão de projeto State:
// Uma doação pode ter 4 estados diferentes, e esse estado influencia em seu comportamento
// Está sendo utilizado para encapsular operações diversas sempre que ocorre um fluxo de troca de estado
// Quando uma doação é solicitada, é necessário referenciar sua empresa recebedora
// Quando a solicitação é cancelada, é necessário remover a referência a empresa recebedora
// Essas referências são feitas pela implementação dos métodos de cada subclasse do StateDoacao
// Facilitando a manutenção do fluxo de atualização de cada estado

// Além disso, é utilizado como um limitador de estados, o que ajuda na integridade do estado
// Além de evitar erros como uma doação em andamento que não tem nenhuma empresa recebedora referenciada
// Ou de uma doação já cancelada sendo solicitada
// Assim, algumas regras ficam encapsuladas:
// Uma doação disponivel não pode ter sua solicitação cancelada
// Uma doação solicitada não pode ser cancelada
// Uma doação cancelada não pode ser solicitada

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
