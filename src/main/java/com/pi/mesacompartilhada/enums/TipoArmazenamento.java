package com.pi.mesacompartilhada.enums;

public enum TipoArmazenamento {

    LOCALSECO(1),
    PRONTOCONSUMO(2),
    REFRIGERACAO(3),
    CONGELAMENTO(4);

    private final int codigo;

    TipoArmazenamento(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return this.codigo;
    }

    public static TipoArmazenamento valueOf(int codigo) {
        for(TipoArmazenamento value : TipoArmazenamento.values()) {
            if(value.getCodigo() == codigo) {
                return value;
            }
        }
        throw new IllegalArgumentException("Código TipoArmazenamento inválido");
    }

    public String toString() {
        return this.name();
    }

}
