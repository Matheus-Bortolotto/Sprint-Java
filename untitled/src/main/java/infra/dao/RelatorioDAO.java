package infra.dao;

import infra.db.OracleConnectionFactory;
import controller.RelatorioAnalise;
import model.Medico;
import model.PecaSimples;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date; // usamos Date do java.sql
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RelatorioDAO implements DAO<RelatorioAnalise, Long> {

    private Long ensurePecaAndReturnId(Connection conn, String descricao) throws SQLException {
        // 1) Buscar existente
        try (PreparedStatement find = conn.prepareStatement("SELECT id FROM peca WHERE descricao = ?")) {
            find.setString(1, descricao);
            try (ResultSet rs = find.executeQuery()) {
                if (rs.next()) return rs.getLong(1);
            }
        }
        // 2) Inserir se não existir
        try (PreparedStatement ins = conn.prepareStatement(
                "INSERT INTO peca (descricao) VALUES (?)",
                new String[]{"id"})) {
            ins.setString(1, descricao);
            ins.executeUpdate();
            try (ResultSet rs = ins.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
        }
        // 3) Fallback: buscar de novo
        try (PreparedStatement find2 = conn.prepareStatement("SELECT id FROM peca WHERE descricao = ?")) {
            find2.setString(1, descricao);
            try (ResultSet rs = find2.executeQuery()) {
                if (rs.next()) return rs.getLong(1);
            }
        }
        throw new SQLException("Não foi possível resolver ID da peça");
    }

    @Override
    public Long insert(RelatorioAnalise r) {
        String sql = "INSERT INTO relatorio (medico_id, peca_id, observacoes, data_analise) VALUES (?, ?, ?, ?)";
        try (Connection conn = OracleConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, new String[]{"id"})) {

            Long pecaId = ensurePecaAndReturnId(conn, r.getPeca().getDescricao());
            ps.setString(1, r.getMedico().getId());
            ps.setLong(2, pecaId);
            ps.setString(3, r.getObservacoes());
            ps.setDate(4, Date.valueOf(LocalDate.now()));
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    long genId = rs.getLong(1);
                    r.setId(genId); // seta no objeto também
                    return genId;
                }
            }
            // fallback (acadêmico)
            try (PreparedStatement q = conn.prepareStatement("SELECT MAX(id) FROM relatorio")) {
                try (ResultSet rs2 = q.executeQuery()) {
                    if (rs2.next()) {
                        long genId = rs2.getLong(1);
                        r.setId(genId);
                        return genId;
                    }
                }
            }
            throw new RuntimeException("ID do relatório não retornado");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir relatório", e);
        }
    }

    @Override
    public Optional<RelatorioAnalise> findById(Long id) {
        String sql = "SELECT r.id, r.observacoes, r.data_analise, " +
                "m.id mid, m.nome mnome, m.crm mcrm, m.especialidade mesp, " +
                "p.descricao pdesc " +
                "FROM relatorio r " +
                "JOIN medico m ON r.medico_id=m.id " +
                "JOIN peca p ON r.peca_id=p.id " +
                "WHERE r.id=?";
        try (Connection conn = OracleConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Medico med = new Medico(rs.getString("mid"), rs.getString("mnome"),
                            rs.getString("mcrm"), rs.getString("mesp"));
                    PecaSimples p = new PecaSimples(rs.getString("pdesc"));
                    RelatorioAnalise out = new RelatorioAnalise(rs.getLong("id"), med, p, rs.getString("observacoes"));
                    return Optional.of(out);
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar relatório", e);
        }
    }

    @Override
    public List<RelatorioAnalise> findAll() {
        String sql = "SELECT r.id, r.observacoes, r.data_analise, " +
                "m.id mid, m.nome mnome, m.crm mcrm, m.especialidade mesp, " +
                "p.descricao pdesc " +
                "FROM relatorio r " +
                "JOIN medico m ON r.medico_id=m.id " +
                "JOIN peca p ON r.peca_id=p.id";
        List<RelatorioAnalise> out = new ArrayList<>();
        try (Connection conn = OracleConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Medico med = new Medico(rs.getString("mid"), rs.getString("mnome"),
                        rs.getString("mcrm"), rs.getString("mesp"));
                PecaSimples p = new PecaSimples(rs.getString("pdesc"));
                RelatorioAnalise r = new RelatorioAnalise(rs.getLong("id"), med, p, rs.getString("observacoes"));
                out.add(r);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar relatórios", e);
        }
        return out;
    }

    @Override
    public boolean update(RelatorioAnalise r) {
        if (r.getId() == null) {
            throw new IllegalArgumentException("Relatório sem ID: não é possível atualizar.");
        }
        String sql = "UPDATE relatorio SET medico_id=?, peca_id=?, observacoes=?, data_analise=? WHERE id=?";
        try (Connection conn = OracleConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            Long pecaId = ensurePecaAndReturnId(conn, r.getPeca().getDescricao());
            ps.setString(1, r.getMedico().getId());
            ps.setLong(2, pecaId);
            ps.setString(3, r.getObservacoes());
            ps.setDate(4, Date.valueOf(LocalDate.now())); // opcional: manter data da última atualização
            ps.setLong(5, r.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar relatório", e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM relatorio WHERE id=?";
        try (Connection conn = OracleConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir relatório", e);
        }
    }
}
