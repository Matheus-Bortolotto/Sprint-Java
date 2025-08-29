import infra.dao.MedicoDAO;
import model.Medico;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class MedicoDAOTest {

    @Test
    public void crudMedico() {
        MedicoDAO dao = new MedicoDAO();
        String id = UUID.randomUUID().toString();
        Medico m = new Medico(id, "Dra. Ana", "12345", "Ortopedia");

        // Create
        String savedId = dao.insert(m);
        assertEquals(id, savedId);

        // Read
        Optional<Medico> got = dao.findById(id);
        assertTrue(got.isPresent());
        assertEquals("Dra. Ana", got.get().getNome());

        // Update
        Medico m2 = new Medico(id, "Dra. Ana Paula", "12345", "Ortopedia");
        assertTrue(dao.update(m2));

        // Delete
        assertTrue(dao.deleteById(id));
    }
}
