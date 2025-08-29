package controller;

import model.Medico;
import model.PecaAnatomica;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

/** Entidade de relatório da análise anatômica. */
public class RelatorioAnalise {

    /** Identificador do relatório (PK no banco). */
    private Long id;

    /** Médico responsável. */
    private Medico medico;

    /** Peça analisada. */
    private PecaAnatomica peca;

    /** Observações clínicas/transcrição. */
    private String observacoes;

    /** Construtor sem id (para INSERT). */
    public RelatorioAnalise(Medico medico, PecaAnatomica peca, String observacoes) {
        this.medico = medico;
        this.peca = peca;
        this.observacoes = observacoes;
    }

    /** Construtor com id (para objetos vindos do banco). */
    public RelatorioAnalise(Long id, Medico medico, PecaAnatomica peca, String observacoes) {
        this.id = id;
        this.medico = medico;
        this.peca = peca;
        this.observacoes = observacoes;
    }

    // ===== Getters/Setters =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }

    public PecaAnatomica getPeca() { return peca; }
    public void setPeca(PecaAnatomica peca) { this.peca = peca; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    // ===== Comportamentos =====
    public void gerarRelatorio() {
        System.out.println("\nRelatório de Análise");
        System.out.println("Médico: " + medico.getNome());
        System.out.println("Peça: " + peca.identificar());
        System.out.println("Observações: " + observacoes);
        System.out.println("Data: " + LocalDate.now());
    }

    public void salvarEmArquivo() {
        try (FileWriter writer = new FileWriter("relatorio.txt")) {
            writer.write("Relatório de Análise\n");
            writer.write("Médico: " + medico.getNome() + "\n");
            writer.write("Peça: " + peca.identificar() + "\n");
            writer.write("Observações: " + observacoes + "\n");
            writer.write("Data: " + LocalDate.now() + "\n");
        } catch (IOException e) {
            System.err.println("Erro ao salvar o relatório: " + e.getMessage());
        }
    }
}
