package com.pi.mesacompartilhada.enums;

public enum TipoAlimento {

    CASEIRO(1),
    INDUSTRIALIZADO(2),
    PERECIVEL(3),
    NAOPERECIVEL(4),
    INNATURA(5);

    private final int codigo;

    TipoAlimento(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return this.codigo;
    }

    public static TipoAlimento valueOf(int codigo) {
        for(TipoAlimento value : TipoAlimento.values()) {
            if(value.getCodigo() == codigo) {
                return value;
            }
        }
        throw new IllegalArgumentException("Código TipoAlimento inválido");
    }

    public String toString() {
        return this.name();
    }

}
