package service;

import controller.RelatorioAnalise;
import infra.dao.RelatorioDAO;
import model.Medico;
import model.PecaSimples;

import java.util.List;
import java.util.Optional;

public class RelatorioService {
    private final RelatorioDAO dao = new RelatorioDAO();

    public Long create(RelatorioAnalise r) {
        validate(r, true);
        return dao.insert(r);
    }

    public Optional<RelatorioAnalise> findById(Long id) {
        return dao.findById(id);
    }

    public List<RelatorioAnalise> findAll() {
        return dao.findAll();
    }

    public boolean update(Long id, RelatorioAnalise r) {
        if (id == null) throw new IllegalArgumentException("ID obrigatório");
        r.setId(id);
        validate(r, false);
        return dao.update(r);
    }

    public boolean delete(Long id) {
        if (id == null) throw new IllegalArgumentException("ID obrigatório");
        return dao.deleteById(id);
    }

    private void validate(RelatorioAnalise r, boolean creating) {
        if (r == null) throw new IllegalArgumentException("Relatório é obrigatório");
        if (!creating && r.getId() == null) throw new IllegalArgumentException("ID ausente");
        if (r.getMedico() == null || r.getMedico().getId() == null) {
            throw new IllegalArgumentException("Médico é obrigatório");
        }
        if (r.getPeca() == null || r.getPeca().getDescricao() == null || r.getPeca().getDescricao().isBlank()) {
            throw new IllegalArgumentException("Peça (descrição) é obrigatória");
        }
        if (r.getObservacoes() == null) r.setObservacoes("");
    }
}
