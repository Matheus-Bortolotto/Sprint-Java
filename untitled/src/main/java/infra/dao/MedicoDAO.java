package infra.dao;

import infra.db.OracleConnectionFactory;
import model.Medico;

import java.sql.*;
import java.util.*;

public class MedicoDAO implements DAO<Medico, String> {

    @Override
    public String insert(Medico m) {
        String sql = "INSERT INTO medico (id, nome, crm, especialidade) VALUES (?, ?, ?, ?)";
        try (Connection conn = OracleConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getId());
            ps.setString(2, m.getNome());
            ps.setString(3, m.getCrm());
            ps.setString(4, m.getEspecialidade());
            ps.executeUpdate();
            return m.getId();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir médico", e);
        }
    }

    @Override
    public Optional<Medico> findById(String id) {
        String sql = "SELECT id, nome, crm, especialidade FROM medico WHERE id = ?";
        try (Connection conn = OracleConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Medico(
                            rs.getString("id"),
                            rs.getString("nome"),
                            rs.getString("crm"),
                            rs.getString("especialidade")
                    ));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar médico", e);
        }
    }

    @Override
    public List<Medico> findAll() {
        String sql = "SELECT id, nome, crm, especialidade FROM medico";
        List<Medico> out = new ArrayList<>();
        try (Connection conn = OracleConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                out.add(new Medico(
                        rs.getString("id"),
                        rs.getString("nome"),
                        rs.getString("crm"),
                        rs.getString("especialidade")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar médicos", e);
        }
        return out;
    }

    @Override
    public boolean update(Medico m) {
        String sql = "UPDATE medico SET nome=?, crm=?, especialidade=? WHERE id=?";
        try (Connection conn = OracleConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getNome());
            ps.setString(2, m.getCrm());
            ps.setString(3, m.getEspecialidade());
            ps.setString(4, m.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar médico", e);
        }
    }

    @Override
    public boolean deleteById(String id) {
        String sql = "DELETE FROM medico WHERE id=?";
        try (Connection conn = OracleConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir médico", e);
        }
    }
}
