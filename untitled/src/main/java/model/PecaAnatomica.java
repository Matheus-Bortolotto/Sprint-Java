package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstrata para peças anatômicas.
 */
public abstract class PecaAnatomica {
    protected String lateralidade;
    protected List<String> marcacoes = new ArrayList<>();

    public PecaAnatomica(String lateralidade) {
        if (!lateralidade.equalsIgnoreCase("Esquerda") && !lateralidade.equalsIgnoreCase("Direita"))
            throw new IllegalArgumentException("Lateralidade deve ser 'Esquerda' ou 'Direita'");
        this.lateralidade = lateralidade;
    }

    public void adicionarMarcacao(String marcacao) {
        marcacoes.add(marcacao);
    }

    public abstract String identificar();

    public List<String> getMarcacoes() {
        return marcacoes;
    }

    public String getLateralidade() {
        return lateralidade;
    }
}