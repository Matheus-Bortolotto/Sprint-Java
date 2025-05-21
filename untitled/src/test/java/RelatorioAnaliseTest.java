import controller.RelatorioAnalise;
import model.Medico;
import model.PecaSimples;
import org.junit.jupiter.api.Test;

public class RelatorioAnaliseTest {

    @Test
    public void testGerarRelatorio() {
        Medico medico = new Medico("001", "João", "12345", "Ortopedia");
        PecaSimples peca = new PecaSimples("Fratura exposta no fêmur");
        RelatorioAnalise relatorio = new RelatorioAnalise(medico, peca, "Paciente em recuperação.");

        // Apenas verificamos se o método roda sem lançar exceções
        relatorio.gerarRelatorio();
    }
}
