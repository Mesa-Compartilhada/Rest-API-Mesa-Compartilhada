package com.pi.mesacompartilhada.enums;

public enum UnidadeMedida {

    MILILITRO(1),
    GRAMA(2);

    private final int codigo;

    UnidadeMedida(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return this.codigo;
    }

    public static UnidadeMedida valueOf(int codigo) {
        for(UnidadeMedida value : UnidadeMedida.values()) {
            if(value.getCodigo() == codigo) {
                return value;
            }
        }
        throw new IllegalArgumentException("Código UnidadeMedida inválido");
    }

    public String toString() {
        return this.name();
    }

}
