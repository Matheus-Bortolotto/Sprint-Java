package view;

import controller.RelatorioAnalise;
import model.*;
import util.ReconhecimentoVozWhisper; // Nova classe de integração

public class Principal {
    public static void main(String[] args) {

        // Informações do médico e da peça
        Medico m1 = new Medico("Dra. Ana", "98765-SP", "Patologista");
        PecaAnatomica braco = new Braco("Direita");
        braco.adicionarMarcacao("Lesão exposta");

        // Preenchimento manual do formulário
        FormularioPadronizado form = new FormularioPadronizado(braco);
        form.preencherChecklist("Lesão aberta próxima ao cotovelo.");

        // Nova chamada: transcrição via Python (Whisper)
        ReconhecimentoVozWhisper voz = new ReconhecimentoVozWhisper();
        String textoFalado = voz.capturarETranscrever();

        // Geração do relatório
        RelatorioAnalise relatorio = new RelatorioAnalise(m1, braco, textoFalado);
        relatorio.gerarRelatorio();
        relatorio.salvarEmArquivo(); // salva no relatorio.txt

        System.out.println("Relatório salvo com sucesso!");
    }
}
