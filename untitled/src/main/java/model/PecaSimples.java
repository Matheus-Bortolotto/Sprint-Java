package model;

public class PecaSimples extends PecaAnatomica {

    public PecaSimples(String descricao) {
        super(descricao);
    }

    @Override
    public String identificar() {
        return "Peça analisada: " + descricao;
    }
}
