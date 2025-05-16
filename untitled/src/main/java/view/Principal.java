package view;

import controller.RelatorioAnalise;
import model.*;
import util.ReconhecimentoVozSphinx;

import java.net.URL;

public class Principal {
    public static void main(String[] args) {
        // Verificação dos caminhos dos modelos no classpath
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        URL dictURL = classLoader.getResource("models/ptbr/pt-br.dict");
        URL lmURL = classLoader.getResource("models/ptbr/pt-br.lm");
        URL acousticModelURL = classLoader.getResource("models/ptbr/pt-br.cd_cont_200");

        System.out.println("Dict: " + dictURL);
        System.out.println("LM: " + lmURL);
        System.out.println("Acoustic Model: " + acousticModelURL);

        // Teste normal do sistema
        Medico m1 = new Medico("Dra. Ana", "98765-SP", "Patologista");
        PecaAnatomica braco = new Braco("Direita");
        braco.adicionarMarcacao("Lesão exposta");

        FormularioPadronizado form = new FormularioPadronizado(braco);
        form.preencherChecklist("Lesão aberta próxima ao cotovelo.");

        ReconhecimentoVozSphinx voz = new ReconhecimentoVozSphinx();
        String textoFalado = voz.capturarETranscrever();

        RelatorioAnalise relatorio = new RelatorioAnalise(m1, braco, textoFalado);
        relatorio.gerarRelatorio();
        relatorio.salvarEmArquivo(); // salva no relatorio.txt
    }
}
