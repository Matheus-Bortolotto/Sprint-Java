package model;

/**
 * Representa um braço anatômico.
 */
public class Braco extends PecaAnatomica {
    public Braco(String lateralidade) {
        super(lateralidade);
    }

    @Override
    public String identificar() {
        return "Braço " + lateralidade;
    }
}