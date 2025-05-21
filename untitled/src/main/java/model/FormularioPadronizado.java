package model;

public class FormularioPadronizado {
    private PecaAnatomica peca;
    private String checklist;

    public FormularioPadronizado(PecaAnatomica peca) {
        this.peca = peca;
    }

    public void preencherChecklist(String texto) {
        this.checklist = texto;
    }

    public String getChecklist() {
        return checklist;
    }

    public PecaAnatomica getPeca() {
        return peca;
    }
}
