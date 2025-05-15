package view;

import controller.RelatorioAnalise;
import model.*;
import util.ReconhecimentoVoz;

/**
 * Classe principal que executa o sistema.
 */
public class Principal {
    public static void main(String[] args) {
        Medico m1 = new Medico("Dra. Ana", "98765-SP", "Patologista");
        PecaAnatomica braco = new Braco("Direita");
        braco.adicionarMarcacao("Lesão exposta");

        FormularioPadronizado form = new FormularioPadronizado(braco);
        form.preencherChecklist("Lesão aberta próxima ao cotovelo.");

        ReconhecimentoVoz voz = new ReconhecimentoVoz();
        voz.capturarAudio("Presença de fratura visível e edema.");
        voz.transcreverAudio();

        RelatorioAnalise relatorio = new RelatorioAnalise(m1, braco, voz.getTextoTranscrito());
        relatorio.gerarRelatorio();
        relatorio.salvarEmArquivo(); // salva no relatorio.txt
    }
}