package util;

import java.io.*;

public class ReconhecimentoVozWhisper {

    public String capturarETranscrever() {
        String caminhoScript = "C:/Users/Matheus/Documents/GitHub/Sprint-Java/transcreve_whisper.py";
        String caminhoTxt = "C:/Users/Matheus/Documents/GitHub/Sprint-Java/transcricao.txt";

        try {
            ProcessBuilder pb = new ProcessBuilder("python", caminhoScript);
            pb.redirectErrorStream(true);
            Process processo = pb.start();
            processo.waitFor();

            // Ler o conteúdo do arquivo com a transcrição
            BufferedReader reader = new BufferedReader(new FileReader(caminhoTxt));
            StringBuilder texto = new StringBuilder();
            String linha;
            while ((linha = reader.readLine()) != null) {
                texto.append(linha).append(" ");
            }
            reader.close();

            return texto.toString().trim();

        } catch (Exception e) {
            System.err.println("Erro ao executar o script Python ou ler a transcrição: " + e.getMessage());
        }

        return "";
    }
}
