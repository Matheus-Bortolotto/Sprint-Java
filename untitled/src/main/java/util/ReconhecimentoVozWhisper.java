package util;

import java.io.*;

public class ReconhecimentoVozWhisper {

    public String capturarETranscrever() {
        String texto = "";

        try {
            String caminhoScript = "C:/Users/Matheus/Documents/GitHub/Sprint-Java/transcreve_whisper.py";

            ProcessBuilder pb = new ProcessBuilder("python", "-u", caminhoScript);
            pb.environment().put("PYTHONIOENCODING", "utf-8");
            pb.redirectErrorStream(true);
            pb.redirectErrorStream(true); // junta stdout e stderr

            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
            StringBuilder output = new StringBuilder();
            String linha;
            while ((linha = reader.readLine()) != null) {
                System.out.println(linha);
                output.append(linha).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("Script Python finalizou com erro, código: " + exitCode);
            }

            File arquivo = new File("C:/Users/Matheus/Documents/GitHub/Sprint-Java/transcricao.txt");
            if (arquivo.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(arquivo));
                StringBuilder sb = new StringBuilder();
                String l;
                while ((l = br.readLine()) != null) {
                    sb.append(l).append(" ");
                }
                texto = sb.toString().trim();
                br.close();
            } else {
                System.err.println("Arquivo de transcrição não encontrado.");
            }

        } catch (Exception e) {
            System.err.println("Erro ao executar o script Python ou ler a transcrição: " + e.getMessage());
            e.printStackTrace();
        }

        return texto;
    }
}
