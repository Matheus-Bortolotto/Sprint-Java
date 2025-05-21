package view;

import controller.RelatorioAnalise;
import model.*;
import util.ReconhecimentoVozWhisper;

import java.util.*;

public class Principal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Medico> medicos = Arrays.asList(
                new Medico("1", "Matheus", "555189", "Ortopedia"),
                new Medico("2", "Ana", "123456", "Patologia")
        );

        System.out.println("=== Bem-vindo ao Sistema de Análise Médica ===");
        System.out.print("Digite seu nome ou ID: ");
        String login = scanner.nextLine();

        System.out.print("Digite seu CRM (senha): ");
        String crm = scanner.nextLine();

        Medico medicoLogado = null;
        for (Medico m : medicos) {
            if ((m.getId().equalsIgnoreCase(login) || m.getNome().equalsIgnoreCase(login)) && m.autenticar(crm)) {
                medicoLogado = m;
                break;
            }
        }

        if (medicoLogado == null) {
            System.out.println("❌ Acesso negado. ID/CRM incorretos.");
            return;
        }

        System.out.println("\n✅ Login realizado com sucesso. Olá, Dr(a). " + medicoLogado.getNome());

        System.out.print("\nDescreva a peça a ser analisada: ");
        String descricao = scanner.nextLine();

        PecaAnatomica peca = new PecaSimples(descricao);

        System.out.print("Descrição do formulário (checklist): ");
        String checklist = scanner.nextLine();

        FormularioPadronizado form = new FormularioPadronizado(peca);
        form.preencherChecklist(checklist);

        ReconhecimentoVozWhisper voz = new ReconhecimentoVozWhisper();
        String textoFalado = voz.capturarETranscrever();

        RelatorioAnalise relatorio = new RelatorioAnalise(medicoLogado, peca, textoFalado);
        relatorio.gerarRelatorio();
        relatorio.salvarEmArquivo();

        System.out.println("\n✅ Relatório salvo com sucesso!");
    }
}
