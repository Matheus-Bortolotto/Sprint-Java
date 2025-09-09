package view;

import controller.RelatorioAnalise;
import model.*;
import util.ReconhecimentoVozWhisper;

import application.AnaliseService;
import infra.dao.MedicoDAO;

import java.util.*;
import java.time.LocalDateTime;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class Principal {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Médicos cadastrados em memória (para login)
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
        relatorio.salvarEmArquivo(); // continua gerando o TXT como antes

        System.out.println("\n✅ Relatório salvo em arquivo local!");

        // ================= PERSISTÊNCIA NO ORACLE =================
        try {
            // 1) persistir médico (se ainda não estiver no banco)
            Medico medicoParaBanco = new Medico(
                    UUID.randomUUID().toString(),
                    medicoLogado.getNome(),
                    medicoLogado.getCrm() + "-" + (System.currentTimeMillis() % 1000), // evita conflito
                    medicoLogado.getEspecialidade()
            );
            new MedicoDAO().insert(medicoParaBanco);
            System.out.println("🗄️  MÉDICO inserido no banco -> ID=" + medicoParaBanco.getId());

            // 2) salvar relatório no banco
            AnaliseService service = new AnaliseService();
            Long idRelatorio = service.gerarESalvarRelatorio(medicoParaBanco, peca, textoFalado);
            System.out.println("🗄️  RELATÓRIO inserido no banco -> ID=" + idRelatorio);

            // 3) gerar cópia do TXT com ID do banco
            String conteudo = """
                    ===== RELATÓRIO DE ANÁLISE =====
                    ID Relatório : %s
                    Data/Hora    : %s

                    -- Dados do Médico --
                    ID           : %s
                    Nome         : %s
                    CRM          : %s
                    Especialidade: %s

                    -- Peça Analisada --
                    Descrição    : %s

                    -- Observações --
                    %s
                    """.formatted(
                    idRelatorio,
                    LocalDateTime.now(),
                    medicoParaBanco.getId(),
                    medicoParaBanco.getNome(),
                    medicoParaBanco.getCrm(),
                    medicoParaBanco.getEspecialidade(),
                    peca.getDescricao(),
                    textoFalado
            );

            Path pasta = Path.of("target");
            Files.createDirectories(pasta);
            Path arquivo = pasta.resolve("relatorio_" + idRelatorio + ".txt");
            Files.writeString(arquivo, conteudo);

            System.out.println("📝 Relatório com ID gerado em: " + arquivo.toAbsolutePath());

        } catch (Exception e) {
            System.err.println("❌ Erro ao persistir no Oracle: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n✅ Processo concluído!");
    }
}
