package application;
import model.PecaAnatomica;
import model.Medico;
import controller.RelatorioAnalise;

    public class AnaliseService {
        public void registrarMarcacao(PecaAnatomica p, String marcacao) {
            if (marcacao == null || marcacao.isBlank()) {
                throw new IllegalArgumentException("Marcação inválida");
            }
            p.getMarcacoes().add(marcacao.trim());
        }

        public boolean validarChecklist(String checklist) {
            return checklist != null && checklist.length() >= 5; // regra mínima de exemplo
        }

        public RelatorioAnalise gerarRelatorio(Medico m, PecaAnatomica p, String observacoes) {
            if (!m.autenticar(m.getCrm())) throw new IllegalStateException("Médico não autenticado");
            if (observacoes == null || observacoes.isBlank()) observacoes = "Sem observações";
            return new RelatorioAnalise(m, p, observacoes);
        }
    }


