package br.com.sistema.dao;

import br.com.sistema.jdbc.ConnectionFactory;
import br.com.sistema.model.Matricula;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MatriculaDAO {

    private static final List<Matricula> emMemoria = new ArrayList<>();
    private static int proximoId = 1;

    public boolean salvar(Matricula matricula) {
        if (!valida(matricula, true)) return false;
        Connection conn = ConnectionFactory.getConnection();
        if (conn == null) {
            matricula.setId(proximoId++);
            synchronized (emMemoria) { emMemoria.add(matricula); }
            return true;
        }
        String sql = "INSERT INTO tb_matriculas (aluno_id, plano_id, data_inicio, data_vencimento, valor, situacao) VALUES (?,?,?,?,?,?)";
        try (Connection conexao = conn; PreparedStatement stmt = conexao.prepareStatement(sql)) {
            preencher(stmt, matricula);
            return stmt.executeUpdate() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean editar(Matricula matricula) {
        if (!valida(matricula, false)) return false;
        Connection conn = ConnectionFactory.getConnection();
        if (conn == null) {
            synchronized (emMemoria) {
                for (int i = 0; i < emMemoria.size(); i++) {
                    if (emMemoria.get(i).getId() == matricula.getId()) {
                        emMemoria.set(i, matricula);
                        return true;
                    }
                }
            }
            return false;
        }
        String sql = "UPDATE tb_matriculas SET aluno_id=?, plano_id=?, data_inicio=?, data_vencimento=?, valor=?, situacao=? WHERE id=?";
        try (Connection conexao = conn; PreparedStatement stmt = conexao.prepareStatement(sql)) {
            preencher(stmt, matricula);
            stmt.setInt(7, matricula.getId());
            return stmt.executeUpdate() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int id) {
        Connection conn = ConnectionFactory.getConnection();
        if (conn == null) {
            synchronized (emMemoria) { return emMemoria.removeIf(item -> item.getId() == id); }
        }
        String sql = "DELETE FROM tb_matriculas WHERE id=?";
        try (Connection conexao = conn; PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<Matricula> listarTodos() {
        Connection conn = ConnectionFactory.getConnection();
        if (conn == null) {
            synchronized (emMemoria) { return new ArrayList<>(emMemoria); }
        }
        List<Matricula> lista = new ArrayList<>();
        String sql = "SELECT * FROM tb_matriculas ORDER BY data_inicio DESC, id DESC";
        try (Connection conexao = conn;
                PreparedStatement stmt = conexao.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(montarMatricula(rs));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public Matricula buscar(int id) {
        for (Matricula item : listarTodos()) if (item.getId() == id) return item;
        return null;
    }

    public static boolean existeParaAluno(int alunoId) {
        for (Matricula item : new MatriculaDAO().listarTodos()) {
            if (item.getAlunoId() == alunoId) return true;
        }
        return false;
    }

    public static boolean existeParaPlano(int planoId) {
        for (Matricula item : new MatriculaDAO().listarTodos()) {
            if (item.getPlanoId() == planoId) return true;
        }
        return false;
    }

    private void preencher(PreparedStatement stmt, Matricula matricula) throws Exception {
        stmt.setInt(1, matricula.getAlunoId());
        stmt.setInt(2, matricula.getPlanoId());
        stmt.setDate(3, Date.valueOf(matricula.getDataInicio()));
        stmt.setDate(4, Date.valueOf(matricula.getDataVencimento()));
        stmt.setDouble(5, matricula.getValor());
        stmt.setString(6, matricula.getSituacao());
    }

    private Matricula montarMatricula(ResultSet rs) throws Exception {
        return new Matricula(
                rs.getInt("id"),
                rs.getInt("aluno_id"),
                rs.getInt("plano_id"),
                rs.getDate("data_inicio").toLocalDate(),
                rs.getDate("data_vencimento").toLocalDate(),
                rs.getDouble("valor"),
                rs.getString("situacao")
        );
    }

    private boolean valida(Matricula matricula, boolean nova) {
        if (matricula == null || matricula.getDataInicio() == null
                || matricula.getDataVencimento() == null
                || !matricula.getDataVencimento().isAfter(matricula.getDataInicio())
                || matricula.getValor() <= 0
                || Double.isNaN(matricula.getValor())
                || Double.isInfinite(matricula.getValor())) return false;
        if (!("Ativa".equalsIgnoreCase(matricula.getSituacao())
                || "Vencida".equalsIgnoreCase(matricula.getSituacao())
                || "Cancelada".equalsIgnoreCase(matricula.getSituacao()))) return false;

        br.com.sistema.model.Aluno aluno = new AlunoDAO().buscar(matricula.getAlunoId());
        br.com.sistema.model.Plano plano = new PlanoDAO().buscar(matricula.getPlanoId());
        if (aluno == null || plano == null) return false;
        return !nova || (aluno.isAtivo() && "Ativo".equalsIgnoreCase(plano.getSituacao()));
    }
}
