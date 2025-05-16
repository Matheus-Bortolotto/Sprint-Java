package util;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

public class ReconhecimentoVozSphinx {

    public String capturarETranscrever() {
        StringBuilder resultado = new StringBuilder();

        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL dictURL = classLoader.getResource("models/ptbr/pt-br.dict");
            URL lmURL = classLoader.getResource("models/ptbr/pt-br.lm");
            URL acousticModelURL = classLoader.getResource("models/ptbr/pt-br.cd_cont_200");

            if (dictURL == null || lmURL == null || acousticModelURL == null) {
                throw new FileNotFoundException("Modelos de voz não encontrados no classpath.");
            }

            Configuration config = new Configuration();
            config.setAcousticModelPath("file:" + new File(acousticModelURL.toURI()).getAbsolutePath());
            config.setDictionaryPath("file:" + new File(dictURL.toURI()).getAbsolutePath());
            config.setLanguageModelPath("file:" + new File(lmURL.toURI()).getAbsolutePath());

            LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(config);
            recognizer.startRecognition(true);

            System.out.println("Fale algo (diga 'parar' para encerrar):");

            SpeechResult result;
            while ((result = recognizer.getResult()) != null) {
                String texto = result.getHypothesis();
                if ("parar".equalsIgnoreCase(texto)) break;
                System.out.println("Você disse: " + texto);
                resultado.append(texto).append(" ");
            }

            recognizer.stopRecognition();

        } catch (Exception e) {
            System.err.println("Erro ao capturar voz: " + e.getMessage());
        }

        return resultado.toString().trim();
    }
}