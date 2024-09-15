package com.pi.mesacompartilhada.enums;

public enum CategoriaInstituicao {

    ONG(1),
    RELIGIOSA(2),
    UBS(3),
    OUTRO(4);

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

}
