package com.pi.mesacompartilhada.enums;

public enum StatusEmpresa {

    ATIVA(1),
    SUSPENSA(2),
    INATIVA(3);

    private final int codigo;

    StatusEmpresa(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return this.codigo;
    }

    public static StatusEmpresa valueOf(int codigo) {
        for(StatusEmpresa value : StatusEmpresa.values()) {
            if(value.getCodigo() == codigo) {
                return value;
            }
        }
        throw new IllegalArgumentException("Código StatusEmpresa inválido");
    }

    public String toString() {
        return this.name();
    }

}
