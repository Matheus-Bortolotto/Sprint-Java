package model;

/**
 * Representa o formulário de marcações prévias da peça anatômica.
 */
public class FormularioPadronizado {
    private PecaAnatomica peca;
    private boolean preenchido;
    private String checklistMarcado;

    public FormularioPadronizado(PecaAnatomica peca) {
        this.peca = peca;
        this.preenchido = false;
    }

    public void preencherChecklist(String checklist) {
        this.checklistMarcado = checklist;
        this.preenchido = true;
    }

    public String getChecklistMarcado() {
        return checklistMarcado;
    }

    public boolean isPreenchido() {
        return preenchido;
    }
}