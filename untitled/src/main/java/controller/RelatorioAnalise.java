package controller;

import model.Medico;
import model.PecaAnatomica;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class RelatorioAnalise {
    private Medico medico;
    private PecaAnatomica peca;
    private String observacoes;

    public RelatorioAnalise(Medico medico, PecaAnatomica peca, String observacoes) {
        this.medico = medico;
        this.peca = peca;
        this.observacoes = observacoes;
    }

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
