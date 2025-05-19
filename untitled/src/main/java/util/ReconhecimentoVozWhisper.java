package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ReconhecimentoVozWhisper {

    public String capturarETranscrever() {
        StringBuilder resultado = new StringBuilder();
        try {
            ProcessBuilder pb = new ProcessBuilder("python", "transcreve_whisper.py");
            pb.redirectErrorStream(true);
            Process processo = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(processo.getInputStream()));
            String linha;
            boolean transcricao = false;

            while ((linha = reader.readLine()) != null) {
                if (linha.trim().equals("Transcrição:")) {
                    transcricao = true;
                    continue;
                }
                if (transcricao) {
                    resultado.append(linha).append(" ");
                }
            }

            processo.waitFor();

        } catch (Exception e) {
            System.err.println("Erro ao executar script Python: " + e.getMessage());
        }
        return resultado.toString().trim();
    }
}
