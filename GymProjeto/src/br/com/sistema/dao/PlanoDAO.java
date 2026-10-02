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
        // Dados iniciais de demonstração
        emMemoria.add(new Plano(proximoId++, "Musculação", 12, 119.90, "Ativo", "Acesso livre aos equipamentos de musculação e esteiras."));
        emMemoria.add(new Plano(proximoId++, "Funcional", 6, 149.90, "Ativo", "Treinamento funcional em circuito com acompanhamento."));
        emMemoria.add(new Plano(proximoId++, "Crossfit", 12, 199.90, "Ativo", "Treinos de alta intensidade em área dedicada."));
    }

    public void salvar(Plano p) {
        Connection conn = ConnectionFactory.getConnection();
        if (conn != null) {
            try {
                String sql = "INSERT INTO tb_planos (nome, duracao_meses, valor, situacao, descricao) VALUES (?,?,?,?,?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, p.getNome());
                stmt.setInt(2, p.getDuracaoMeses());
                stmt.setDouble(3, p.getValor());
                stmt.setString(4, p.getSituacao());
                stmt.setString(5, p.getDescricao());
                stmt.execute();
                stmt.close();
                conn.close();
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Fallback em memória
        p.setId(proximoId++);
        emMemoria.add(p);
    }

    public void editar(Plano p) {
        Connection conn = ConnectionFactory.getConnection();
        if (conn != null) {
            try {
                String sql = "UPDATE tb_planos SET nome=?, duracao_meses=?, valor=?, situacao=?, descricao=? WHERE id=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, p.getNome());
                stmt.setInt(2, p.getDuracaoMeses());
                stmt.setDouble(3, p.getValor());
                stmt.setString(4, p.getSituacao());
                stmt.setString(5, p.getDescricao());
                stmt.setInt(6, p.getId());
                stmt.execute();
                stmt.close();
                conn.close();
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Fallback em memória
        for (int i = 0; i < emMemoria.size(); i++) {
            if (emMemoria.get(i).getId() == p.getId()) {
                emMemoria.set(i, p);
                break;
            }
        }
    }

    public void excluir(int id) {
        Connection conn = ConnectionFactory.getConnection();
        if (conn != null) {
            try {
                String sql = "DELETE FROM tb_planos WHERE id=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, id);
                stmt.execute();
                stmt.close();
                conn.close();
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Fallback em memória
        emMemoria.removeIf(p -> p.getId() == id);
    }

    public List<Plano> listarTodos() {
        Connection conn = ConnectionFactory.getConnection();
        if (conn != null) {
            try {
                List<Plano> lista = new ArrayList<>();
                String sql = "SELECT * FROM tb_planos";
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Plano p = new Plano();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setDuracaoMeses(rs.getInt("duracao_meses"));
                    p.setValor(rs.getDouble("valor"));
                    p.setSituacao(rs.getString("situacao"));
                    p.setDescricao(rs.getString("descricao"));
                    lista.add(p);
                }
                rs.close();
                stmt.close();
                conn.close();
                if (!lista.isEmpty()) return lista;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new ArrayList<>(emMemoria);
    }
}
