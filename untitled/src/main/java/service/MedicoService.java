package service;

import infra.dao.MedicoDAO;
import model.Medico;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class MedicoService {
    private final MedicoDAO dao = new MedicoDAO();
    private static final Pattern CRM_PATTERN = Pattern.compile("^\\d{4,7}$"); // ex: 4 a 7 dígitos

    public String create(Medico m) {
        validate(m, true);
        return dao.insert(m);
    }

    public Optional<Medico> findById(String id) {
        return dao.findById(id);
    }

    public List<Medico> findAll() {
        return dao.findAll();
    }

    public boolean update(String id, Medico m) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID obrigatório");
        m.setId(id);
        validate(m, false);
        return dao.update(m);
    }

    public boolean delete(String id) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID obrigatório");
        return dao.deleteById(id);
    }

    private void validate(Medico m, boolean creating) {
        if (m == null) throw new IllegalArgumentException("Objeto médico é obrigatório");
        if (creating && (m.getId() == null || m.getId().isBlank())) {
            throw new IllegalArgumentException("ID é obrigatório na criação");
        }
        if (m.getNome() == null || m.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (m.getCrm() == null || !CRM_PATTERN.matcher(m.getCrm().trim()).matches()) {
            throw new IllegalArgumentException("CRM deve conter de 4 a 7 dígitos numéricos");
        }
        if (m.getEspecialidade() == null || m.getEspecialidade().isBlank()) {
            throw new IllegalArgumentException("Especialidade é obrigatória");
        }
    }
}
