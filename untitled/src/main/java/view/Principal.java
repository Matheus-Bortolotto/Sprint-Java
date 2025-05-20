package view;

import controller.RelatorioAnalise;
import model.*;
import util.ReconhecimentoVozWhisper;

import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Coletando dados do médico
        System.out.print("Nome do médico: ");
        String nome = scanner.nextLine();

        System.out.print("CRM do médico: ");
        String crm = scanner.nextLine();

        System.out.print("Especialidade: ");
        String especialidade = scanner.nextLine();

        Medico medico = new Medico(nome, crm, especialidade);

        // Coletando dados da peça
        System.out.print("Tipo da peça anatômica (ex: Braco, Perna): ");
        String tipo = scanner.nextLine();

        System.out.print("Lado da peça (Direita ou Esquerda): ");
        String lado = scanner.nextLine();

        PecaAnatomica peca;

        // Aqui você pode criar outras classes (Perna, Cabeça, etc.)
        if (tipo.equalsIgnoreCase("Braco")) {
            peca = new Braco(lado);
        } else {
            peca = new OutraPeca(tipo, lado);

        }

        System.out.print("Descrição do formulário (checklist): ");
        String descricao = scanner.nextLine();

        peca.adicionarMarcacao("Marcado manualmente");

        FormularioPadronizado form = new FormularioPadronizado(peca);
        form.preencherChecklist(descricao);

        // Transcrição via Whisper
        ReconhecimentoVozWhisper voz = new ReconhecimentoVozWhisper();
        String textoFalado = voz.capturarETranscrever();

        System.out.println("\nTexto capturado do Python:");
        System.out.println(textoFalado);

        // Geração e salvamento do relatório
        RelatorioAnalise relatorio = new RelatorioAnalise(medico, peca, textoFalado);
        relatorio.gerarRelatorio();
        relatorio.salvarEmArquivo();

        System.out.println("\n✅ Relatório salvo com sucesso!");

        scanner.close();
    }
}
