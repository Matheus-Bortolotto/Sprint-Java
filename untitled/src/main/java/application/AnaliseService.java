package application;

import model.PecaAnatomica;
import model.Medico;
import controller.RelatorioAnalise;
import infra.dao.RelatorioDAO;

public class AnaliseService {

    public void registrarMarcacao(PecaAnatomica p, String marcacao) {
        if (marcacao == null || marcacao.isBlank()) {
            throw new IllegalArgumentException("Marcação inválida");
        }
        p.getMarcacoes().add(marcacao.trim());
    }

    public boolean validarChecklist(String checklist) {
        return checklist != null && checklist.length() >= 5;
    }

    public RelatorioAnalise gerarRelatorio(Medico m, PecaAnatomica p, String observacoes) {
        if (!m.autenticar(m.getCrm())) throw new IllegalStateException("Médico não autenticado");
        if (observacoes == null || observacoes.isBlank()) observacoes = "Sem observações";
        return new RelatorioAnalise(m, p, observacoes);
    }

    /** Novo: gera e persiste no Oracle, retornando o ID */
    public Long gerarESalvarRelatorio(Medico m, PecaAnatomica p, String observacoes) {
        RelatorioAnalise rel = gerarRelatorio(m, p, observacoes);
        Long id = new RelatorioDAO().insert(rel);
        return id;
    }
}
