package br.com.dio.model;

public enum GameStatusEnum {

    NON_STARTED("não iniciado"), // Propriedade label para cada enum
    INCOMPLETE("incompleto"),
    COMPLETE("completo");

    private String label; // Propriedade rótulo (label)

    GameStatusEnum(final String label){ // Construtor
        this.label = label;
    }

    public String getLabel() { // Getter para o label
        return label;
    }
}
