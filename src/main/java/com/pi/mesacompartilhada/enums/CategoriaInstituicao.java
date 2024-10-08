package com.pi.mesacompartilhada.enums;

public enum CategoriaInstituicao {

    ONG(1),
    OSC(2),
    RELIGIOSA(3),
    BANCODEALIMENTOS(4);

    private final int codigo;

    CategoriaInstituicao(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static CategoriaInstituicao valueOf(int codigo) {
        for(CategoriaInstituicao value : CategoriaInstituicao.values()) {
            if(value.getCodigo() == codigo) {
                return value;
            }
        }
        throw new IllegalArgumentException("Código TipoInstituicao inválido");
    }

    public String toString() {
        return this.name();
    }

}
