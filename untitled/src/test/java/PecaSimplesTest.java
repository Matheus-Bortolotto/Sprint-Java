import model.PecaSimples;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PecaSimplesTest {

    @Test
    public void testDescricaoEIdentificacao() {
        PecaSimples peca = new PecaSimples("Fratura no úmero");

        // Verifica se o método identificar() retorna o texto esperado
        String resultado = peca.identificar();
        assertEquals("Peça analisada: Fratura no úmero", resultado);
    }
}
