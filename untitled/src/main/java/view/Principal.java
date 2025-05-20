package view;

import controller.RelatorioAnalise;
import model.*;
import util.ReconhecimentoVozWhisper;

public class Principal {
    public static void main(String[] args) {
        Medico m1 = new Medico("Dra. Ana", "98765-SP", "Patologista");
        PecaAnatomica braco = new Braco("Direita");
        braco.adicionarMarcacao("Lesão exposta");

        FormularioPadronizado form = new FormularioPadronizado(braco);
        form.preencherChecklist("Lesão aberta próxima ao cotovelo.");

        ReconhecimentoVozWhisper voz = new ReconhecimentoVozWhisper();
        String textoFalado = voz.capturarETranscrever();

        System.out.println("Texto capturado do Python:");
        System.out.println(textoFalado);

        RelatorioAnalise relatorio = new RelatorioAnalise(m1, braco, textoFalado);
        relatorio.gerarRelatorio();
        relatorio.salvarEmArquivo();
    }
}
