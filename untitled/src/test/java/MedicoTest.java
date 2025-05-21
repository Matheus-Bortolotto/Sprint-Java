import model.Medico;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MedicoTest {

    @Test
    public void testMedicoValido() {
        Medico medico = new Medico("001", "João", "12345", "Cardiologia");
        assertEquals("001", medico.getId());
        assertEquals("João", medico.getNome());
        assertEquals("12345", medico.getCrm());
        assertEquals("Cardiologia", medico.getEspecialidade());
    }

    @Test
    public void testAutenticacaoCorreta() {
        Medico medico = new Medico("002", "Ana", "98765", "Pediatria");
        assertTrue(medico.autenticar("98765"));
    }

    @Test
    public void testAutenticacaoIncorreta() {
        Medico medico = new Medico("003", "Carlos", "54321", "Ortopedia");
        assertFalse(medico.autenticar("00000"));
    }
}
