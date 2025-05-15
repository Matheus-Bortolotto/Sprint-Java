package model;

/**
 * Representa uma perna anatômica.
 */
public class Perna extends PecaAnatomica {
    public Perna(String lateralidade) {
        super(lateralidade);
    }

    @Override
    public String identificar() {
        return "Perna " + lateralidade;
    }
}