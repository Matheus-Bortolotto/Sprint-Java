package app;

import application.AnaliseService;
import controller.RelatorioAnalise;
import infra.dao.MedicoDAO;
import model.*;
import util.GravadorAudio;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class UiMain {

    public static void start() {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Sistema de Análise Médica");
            f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            CardLayout layout = new CardLayout();
            JPanel root = new JPanel(layout);

            // ====== LOGIN ======
            JPanel login = new JPanel(new GridBagLayout());
            login.setBorder(new EmptyBorder(20, 24, 20, 24));
            GridBagConstraints c = new GridBagConstraints();
            c.insets = new Insets(6, 6, 6, 6);
            c.fill = GridBagConstraints.HORIZONTAL;

            JLabel lTitulo = new JLabel("Login");
            lTitulo.setFont(lTitulo.getFont().deriveFont(Font.BOLD, 20f));
            JTextField tfLogin = new JTextField(18);
            JPasswordField pfCrm = new JPasswordField(18);
            JButton btEntrar = new JButton("Entrar");

            c.gridx = 0; c.gridy = 0; c.gridwidth = 2; c.weightx = 1;
            login.add(lTitulo, c);
            c.gridwidth = 1; c.gridy = 1; c.gridx = 0;
            login.add(new JLabel("Nome ou ID:"), c);
            c.gridx = 1; c.weightx = 1;
            login.add(tfLogin, c);
            c.gridy = 2; c.gridx = 0; c.weightx = 0;
            login.add(new JLabel("CRM (senha):"), c);
            c.gridx = 1; c.weightx = 1;
            login.add(pfCrm, c);
            c.gridy = 3; c.gridx = 0; c.gridwidth = 2;
            login.add(btEntrar, c);

            // ====== FORM ======
            JPanel form = new JPanel(new GridBagLayout());
            form.setBorder(new EmptyBorder(20, 24, 20, 24));
            GridBagConstraints g = new GridBagConstraints();
            g.insets = new Insets(6, 6, 6, 6);
            g.fill = GridBagConstraints.HORIZONTAL;

            JLabel fTitulo = new JLabel("Nova Análise");
            fTitulo.setFont(fTitulo.getFont().deriveFont(Font.BOLD, 20f));
            JTextField tfDescricao = new JTextField(22);
            JTextArea taChecklist = new JTextArea(4, 22);
            taChecklist.setLineWrap(true); taChecklist.setWrapStyleWord(true);
            JScrollPane spChecklist = new JScrollPane(taChecklist);

            JButton btIniciar = new JButton("🎙️ Iniciar gravação");
            JButton btEncerrar = new JButton("⏹️ Encerrar / Transcrever");
            btEncerrar.setEnabled(false);

            JTextArea taObs = new JTextArea(6, 22);
            taObs.setLineWrap(true); taObs.setWrapStyleWord(true);
            JScrollPane spObs = new JScrollPane(taObs);

            JButton btGerar = new JButton("Gerar Relatório (banco + .txt)");

            g.gridx = 0; g.gridy = 0; g.gridwidth = 2; g.weightx = 1;
            form.add(fTitulo, g);
            g.gridwidth = 1; g.gridy = 1; g.gridx = 0;
            form.add(new JLabel("Descrição da Peça:"), g);
            g.gridx = 1; g.weightx = 1;
            form.add(tfDescricao, g);
            g.gridy = 2; g.gridx = 0;
            form.add(new JLabel("Checklist:"), g);
            g.gridx = 1;
            form.add(spChecklist, g);
            g.gridy = 3; g.gridx = 0; g.gridwidth = 2;
            form.add(btIniciar, g);
            g.gridy = 4;
            form.add(btEncerrar, g);
            g.gridy = 5;
            form.add(spObs, g);
            g.gridy = 6;
            form.add(btGerar, g);

            root.add(login, "login");
            root.add(form, "form");
            f.setContentPane(root);

            // médicos de exemplo (mesmo do console)
            List<Medico> medicos = Arrays.asList(
                    new Medico("1", "Matheus", "555189", "Ortopedia"),
                    new Medico("2", "Ana", "123456", "Patologia")
            );
            final Medico[] medicoLogado = {null};

            // estado do gravador
            final GravadorAudio gravador = new GravadorAudio();
            final File[] arquivoWav = { null };

            // ===== AÇÕES =====
            f.getRootPane().setDefaultButton(btEntrar);

            btEntrar.addActionListener(e -> {
                String loginTxt = tfLogin.getText().trim();
                String crmTxt = new String(pfCrm.getPassword()).trim();
                Medico ok = null;
                for (Medico m : medicos) {
                    if ((m.getId().equalsIgnoreCase(loginTxt) || m.getNome().equalsIgnoreCase(loginTxt))
                            && m.autenticar(crmTxt)) {
                        ok = m; break;
                    }
                }
                if (ok == null) {
                    JOptionPane.showMessageDialog(f, "Acesso negado. ID/CRM incorretos.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                medicoLogado[0] = ok;
                layout.show(root, "form");
                tfDescricao.requestFocusInWindow();
            });

            btIniciar.addActionListener(e -> {
                try {
                    String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
                    File pasta = new File("target"); pasta.mkdirs();
                    arquivoWav[0] = new File(pasta, "gravacao_" + ts + ".wav");
                    gravador.iniciar(arquivoWav[0]);
                    btIniciar.setEnabled(false);
                    btEncerrar.setEnabled(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(f, "Erro ao iniciar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            });

            btEncerrar.addActionListener(e -> {
                try {
                    File wav = gravador.encerrar();
                    btIniciar.setEnabled(true);
                    btEncerrar.setEnabled(false);

                    if (wav == null || !wav.exists()) {
                        JOptionPane.showMessageDialog(f, "Nenhum áudio gravado.", "Aviso", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // chama Python para transcrever
                    String python = "python"; // troque para "py" se for o seu comando no Windows
                    // >>> AJUSTE O CAMINHO DO SCRIPT AQUI <<<
                    String script = "C:/Users/Matheus/Documents/GitHub/Sprint-Java/whisper_cli.py";

                    ProcessBuilder pb = new ProcessBuilder(python, script, "--audio", wav.getAbsolutePath());
                    pb.redirectErrorStream(true);
                    pb.environment().put("PYTHONIOENCODING", "utf-8"); // acentuação no stdout
                    Process proc = pb.start();

                    StringBuilder saida = new StringBuilder();
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream(), StandardCharsets.UTF_8))) {
                        String line;
                        while ((line = br.readLine()) != null) saida.append(line).append("\n");
                    }
                    int code = proc.waitFor();
                    if (code != 0) {
                        JOptionPane.showMessageDialog(f, "Erro transcrição:\n" + saida, "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String texto = saida.toString().trim();
                    taObs.setText(texto.isBlank() ? "(sem conteúdo)" : texto);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(f, "Erro ao encerrar/transcrever: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            });

            // ===== GERAR RELATÓRIO (banco + .txt) =====
            btGerar.addActionListener(e -> {
                try {
                    if (medicoLogado[0] == null) {
                        JOptionPane.showMessageDialog(f, "Faça login primeiro.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String descricao = tfDescricao.getText().trim();
                    String checklist = taChecklist.getText().trim();
                    String observ = taObs.getText();

                    if (descricao.isBlank()) {
                        JOptionPane.showMessageDialog(f, "Informe a descrição da peça.", "Erro", JOptionPane.ERROR_MESSAGE);
                        tfDescricao.requestFocusInWindow();
                        return;
                    }

                    // Domínio: peça + formulário
                    PecaAnatomica peca = new PecaSimples(descricao);
                    FormularioPadronizado fp = new FormularioPadronizado(peca);
                    fp.preencherChecklist(checklist);

                    // Relatório: gerar e salvar txt "padrão" (sua lógica existente)
                    RelatorioAnalise rel = new RelatorioAnalise(medicoLogado[0], peca, observ);
                    rel.gerarRelatorio();
                    rel.salvarEmArquivo();

                    // Banco: salvar médico (gera um CRM único para não colidir)
                    Medico medicoBanco = new Medico(
                            UUID.randomUUID().toString(),
                            medicoLogado[0].getNome(),
                            medicoLogado[0].getCrm() + "-" + (System.currentTimeMillis() % 1000),
                            medicoLogado[0].getEspecialidade()
                    );
                    new MedicoDAO().insert(medicoBanco);

                    // Banco: salvar relatório via service/DAO
                    Long idRel = new AnaliseService().gerarESalvarRelatorio(medicoBanco, peca, observ);

                    // TXT extra com ID do banco (em /target)
                    Files.createDirectories(Path.of("target"));
                    Path arquivo = Path.of("target", "relatorio_" + idRel + ".txt");
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
                            idRel,
                            LocalDateTime.now(),
                            medicoBanco.getId(),
                            medicoBanco.getNome(),
                            medicoBanco.getCrm(),
                            medicoBanco.getEspecialidade(),
                            peca.getDescricao(),
                            (observ == null || observ.isBlank()) ? "Sem observações" : observ
                    );
                    Files.writeString(arquivo, conteudo);

                    JOptionPane.showMessageDialog(f,
                            "✅ Relatório salvo!\n" +
                                    "ID (banco): " + idRel + "\n" +
                                    "TXT: " + arquivo.toAbsolutePath(),
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(f, "Falha ao gerar/salvar: " + ex.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            });

            f.pack();
            f.setMinimumSize(new Dimension(560, 420));
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}
