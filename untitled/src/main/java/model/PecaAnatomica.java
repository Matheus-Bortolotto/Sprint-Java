package model;

import java.util.ArrayList;
import java.util.List;


/** Método público */
public abstract class PecaAnatomica {
    protected String descricao;
    protected List<String> marcacoes = new ArrayList<>();


    /** Método público */
    public PecaAnatomica(String descricao) {
        this.descricao = descricao;
    }


    /** Método público */
    public void adicionarMarcacao(String marcacao) {
        marcacoes.add(marcacao);
    }


    /** Método público */
    public String getDescricao() {
        return descricao;
    }


    /** Método público */
    public List<String> getMarcacoes() {
        return marcacoes;
    }


    /** Método público */
    public abstract String identificar();
}
