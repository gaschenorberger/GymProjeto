package br.com.sistema.dao;

import br.com.sistema.jdbc.ConnectionFactory;
import br.com.sistema.model.Plano;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PlanoDAO {

    private static final List<Plano> emMemoria = new ArrayList<>();
    private static int proximoId = 1;

    static {
        emMemoria.add(new Plano(proximoId++, "Musculação", 12, 119.90, "Ativo", "Acesso livre aos equipamentos de musculação e esteiras."));
        emMemoria.add(new Plano(proximoId++, "Funcional", 6, 149.90, "Ativo", "Treinamento funcional em circuito com acompanhamento."));
        emMemoria.add(new Plano(proximoId++, "Crossfit", 12, 199.90, "Ativo", "Treinos de alta intensidade em área dedicada."));
    }

    public boolean salvar(Plano plano) {
        Connection conn = ConnectionFactory.getConnection();
        if (conn == null) {
            plano.setId(proximoId++);
            emMemoria.add(plano);
            return true;
        }
        String sql = "INSERT INTO tb_planos (nome, duracao_meses, valor, situacao, descricao) VALUES (?,?,?,?,?)";
        try (Connection conexao = conn; PreparedStatement stmt = conexao.prepareStatement(sql)) {
            preencher(stmt, plano);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editar(Plano plano) {
        Connection conn = ConnectionFactory.getConnection();
        if (conn == null) {
            for (int i = 0; i < emMemoria.size(); i++) {
                if (emMemoria.get(i).getId() == plano.getId()) {
                    emMemoria.set(i, plano);
                    return true;
                }
            }
            return false;
        }
        String sql = "UPDATE tb_planos SET nome=?, duracao_meses=?, valor=?, situacao=?, descricao=? WHERE id=?";
        try (Connection conexao = conn; PreparedStatement stmt = conexao.prepareStatement(sql)) {
            preencher(stmt, plano);
            stmt.setInt(6, plano.getId());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int id) {
        Connection conn = ConnectionFactory.getConnection();
        if (conn == null) return emMemoria.removeIf(plano -> plano.getId() == id);
        String sql = "DELETE FROM tb_planos WHERE id=?";
        try (Connection conexao = conn; PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Plano> listarTodos() {
        Connection conn = ConnectionFactory.getConnection();
        if (conn == null) return new ArrayList<>(emMemoria);
        List<Plano> lista = new ArrayList<>();
        String sql = "SELECT * FROM tb_planos ORDER BY nome";
        try (Connection conexao = conn; PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(new Plano(rs.getInt("id"), rs.getString("nome"), rs.getInt("duracao_meses"), rs.getDouble("valor"), rs.getString("situacao"), rs.getString("descricao")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    private void preencher(PreparedStatement stmt, Plano plano) throws Exception {
        stmt.setString(1, plano.getNome());
        stmt.setInt(2, plano.getDuracaoMeses());
        stmt.setDouble(3, plano.getValor());
        stmt.setString(4, plano.getSituacao());
        stmt.setString(5, plano.getDescricao());
    }
}
