package model;

public class OutraPeca extends PecaAnatomica {

    private String tipo;

    public OutraPeca(String tipo, String lateralidade) {
        super(lateralidade);
        this.tipo = tipo;
    }

    @Override
    public String identificar() {
        return tipo + " " + lateralidade;
    }
}
