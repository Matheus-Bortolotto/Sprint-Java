package controller;

import model.Medico;
import model.PecaAnatomica;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Gera relatório baseado na análise médica.
 */
public class RelatorioAnalise {
    private Medico medico;
    private PecaAnatomica peca;
    private String observacoes;
    private LocalDate data;

    public RelatorioAnalise(Medico medico, PecaAnatomica peca, String observacoes) {
        this.medico = medico;
        this.peca = peca;
        this.observacoes = observacoes;
        this.data = LocalDate.now();
    }

    public void gerarRelatorio() {
        System.out.println("Relatório de Análise");
        System.out.println("Médico: " + medico.getNome());
        System.out.println("Peça: " + peca.identificar());
        System.out.println("Observações: " + observacoes);
        System.out.println("Data: " + data);
    }

    public void salvarEmArquivo() {
        try (FileWriter writer = new FileWriter("relatorio.txt")) {
            writer.write("Relatório de Análise\n");
            writer.write("Médico: " + medico.getNome() + "\n");
            writer.write("Peça: " + peca.identificar() + "\n");
            writer.write("Observações: " + observacoes + "\n");
            writer.write("Data: " + data + "\n");
        } catch (IOException e) {
            System.err.println("Erro ao salvar relatório: " + e.getMessage());
        }
    }
}