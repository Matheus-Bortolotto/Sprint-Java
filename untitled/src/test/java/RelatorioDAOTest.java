import infra.dao.MedicoDAO;
import infra.dao.RelatorioDAO;
import model.Medico;
import model.PecaSimples;
import controller.RelatorioAnalise;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class RelatorioDAOTest {

    @Test
    public void crudRelatorio() {
        // Precisa existir um médico para o FK
        MedicoDAO mdao = new MedicoDAO();
        String medId = UUID.randomUUID().toString();
        Medico med = new Medico(medId, "Dr. Teste", "CRM123", "Patologia");
        mdao.insert(med);

        RelatorioDAO rdao = new RelatorioDAO();
        PecaSimples peca = new PecaSimples("Perna Direita");
        RelatorioAnalise r = new RelatorioAnalise(med, peca, "Sem alterações significativas");

        // CREATE
        Long id = rdao.insert(r);
        assertNotNull(id);
        assertEquals(id, r.getId());

        // READ
        Optional<RelatorioAnalise> got = rdao.findById(id);
        assertTrue(got.isPresent());
        assertEquals("Sem alterações significativas", got.get().getObservacoes());

        // UPDATE
        r.setObservacoes("Edema leve e escoriações superficiais");
        assertTrue(rdao.update(r));

        Optional<RelatorioAnalise> got2 = rdao.findById(id);
        assertTrue(got2.isPresent());
        assertEquals("Edema leve e escoriações superficiais", got2.get().getObservacoes());

        // DELETE
        assertTrue(rdao.deleteById(id));
    }
}
