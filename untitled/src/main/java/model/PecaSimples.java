package model;


/** Método público */
public class PecaSimples extends PecaAnatomica {


    /** Método público */
    public PecaSimples(String descricao) {
        super(descricao);
    }

    @Override

    /** Método público */
    public String identificar() {
        return "Peça analisada: " + descricao;
    }
}
