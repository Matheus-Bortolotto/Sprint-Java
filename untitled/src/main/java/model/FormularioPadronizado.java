package model;


/** Método público */
public class FormularioPadronizado {

    /** Atributo privado */
    private PecaAnatomica peca;

    /** Atributo privado */
    private String checklist;


    /** Método público */
    public FormularioPadronizado(PecaAnatomica peca) {
        this.peca = peca;
    }


    /** Método público */
    public void preencherChecklist(String texto) {
        this.checklist = texto;
    }


    /** Método público */
    public String getChecklist() {
        return checklist;
    }


    /** Método público */
    public PecaAnatomica getPeca() {
        return peca;
    }
}
