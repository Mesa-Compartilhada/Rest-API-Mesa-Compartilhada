package com.pi.mesacompartilhada.enums;

public enum CategoriaEstabelecimento {

    RESTAURANTE(1),
    HORTIFRUTTI(2),
    MERCADO(3),
    PADARIA(4),
    FASTFOOD(5),
    ADEGA(6);

    private final int codigo;

    CategoriaEstabelecimento(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static CategoriaEstabelecimento valueOf(int codigo) {
        for(CategoriaEstabelecimento value : CategoriaEstabelecimento.values()) {
            if(value.getCodigo() == codigo) {
                return value;
            }
        }
        throw new IllegalArgumentException("Código TipoEstabelecimento inválido");
    }

    public String toString() {
        return this.name();
    }

}
