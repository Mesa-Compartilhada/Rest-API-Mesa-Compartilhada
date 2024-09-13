package com.pi.mesacompartilhada.enums;

public enum TipoEmpresa {

    DOADORA(1),
    RECEBEDORA(2);

    private final int codigo;

    TipoEmpresa(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static TipoEmpresa valueOf(int codigo) {
        for(TipoEmpresa value : TipoEmpresa.values()) {
            if(value.getCodigo() == codigo) {
                return value;
            }
        }
        throw new IllegalArgumentException("Código TipoEmpresa inválido");
    }

}
