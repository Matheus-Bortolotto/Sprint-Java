import model.FormularioPadronizado;
import model.PecaSimples;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FormularioPadronizadoTest {

    @Test
    public void testPreencherChecklist() {
        PecaSimples peca = new PecaSimples("Fratura no fêmur");
        FormularioPadronizado form = new FormularioPadronizado(peca);

        form.preencherChecklist("Checklist preenchido");
        assertEquals("Checklist preenchido", form.getChecklist());
    }

    @Test
    public void testGetPeca() {
        PecaSimples peca = new PecaSimples("Fratura no fêmur");
        FormularioPadronizado form = new FormularioPadronizado(peca);

        assertEquals("Fratura no fêmur", form.getPeca().getDescricao());
    }
}
