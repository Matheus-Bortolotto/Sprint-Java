package model;

import java.util.ArrayList;
import java.util.List;

public abstract class PecaAnatomica {
    protected String descricao;
    protected List<String> marcacoes = new ArrayList<>();

    public PecaAnatomica(String descricao) {
        this.descricao = descricao;
    }

    public void adicionarMarcacao(String marcacao) {
        marcacoes.add(marcacao);
    }

    public String getDescricao() {
        return descricao;
    }

    public List<String> getMarcacoes() {
        return marcacoes;
    }

    public abstract String identificar();
}
