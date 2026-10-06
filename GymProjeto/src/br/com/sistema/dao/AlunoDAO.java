package br.com.sistema.dao;

import br.com.sistema.jdbc.ConnectionFactory;
import br.com.sistema.model.Aluno;
import br.com.sistema.util.CpfValidator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    private static final List<Aluno> emMemoria = new ArrayList<>();
    private static int proximoId = 1;

    static {
        emMemoria.add(new Aluno(proximoId++, "João Silva", "529.982.247-25", "joao@email.com", "(45) 99999-1111", LocalDate.of(1995, 3, 15), "Rua das Flores", "100", "Centro", "Cascavel", "PR", "Musculação", "Ativo"));
        emMemoria.add(new Aluno(proximoId++, "Maria Oliveira", "111.444.777-35", "maria@email.com", "(45) 98888-2222", LocalDate.of(1998, 7, 20), "Av. Brasil", "500", "São Cristóvão", "Cascavel", "PR", "Funcional", "Ativo"));
        emMemoria.add(new Aluno(proximoId++, "Pedro Costa", "123.456.789-09", "pedro@email.com", "(45) 97777-3333", LocalDate.of(1990, 1, 10), "Rua Paraná", "250", "Centro", "Cascavel", "PR", "Musculação", "Inativo"));
        emMemoria.add(new Aluno(proximoId++, "Ana Souza", "935.411.347-80", "ana@email.com", "(45) 96666-4444", LocalDate.of(2000, 11, 5), "Rua das Palmeiras", "70", "Neva", "Cascavel", "PR", "Crossfit", "Ativo"));
        emMemoria.add(new Aluno(proximoId++, "Lucas Ferreira", "987.654.321-00", "lucas@email.com", "(45) 95555-5555", LocalDate.of(1993, 8, 12), "Rua Rio de Janeiro", "1200", "Centro", "Cascavel", "PR", "Musculação", "Ativo"));
    }

    public boolean salvar(Aluno aluno) {
        if (!valido(aluno, 0)) return false;
        Connection conn = ConnectionFactory.getConnection();
        if (conn == null) {
            aluno.setId(proximoId++);
            synchronized (emMemoria) { emMemoria.add(aluno); }
            return true;
        }
        String sql = "INSERT INTO tb_alunos (nome, cpf, email, telefone, data_nascimento, endereco, numero, bairro, cidade, estado, plano, status) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conexao = conn; PreparedStatement stmt = conexao.prepareStatement(sql)) {
            preencher(stmt, aluno);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editar(Aluno aluno) {
        if (!valido(aluno, aluno.getId())) return false;
        Connection conn = ConnectionFactory.getConnection();
        if (conn == null) {
            synchronized (emMemoria) {
                for (int i = 0; i < emMemoria.size(); i++) {
                    if (emMemoria.get(i).getId() == aluno.getId()) {
                        emMemoria.set(i, aluno);
                        return true;
                    }
                }
            }
            return false;
        }
        String sql = "UPDATE tb_alunos SET nome=?, cpf=?, email=?, telefone=?, data_nascimento=?, endereco=?, numero=?, bairro=?, cidade=?, estado=?, plano=?, status=? WHERE id=?";
        try (Connection conexao = conn; PreparedStatement stmt = conexao.prepareStatement(sql)) {
            preencher(stmt, aluno);
            stmt.setInt(13, aluno.getId());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int id) {
        if (MatriculaDAO.existeParaAluno(id)) return false;
        Connection conn = ConnectionFactory.getConnection();
        if (conn == null) {
            synchronized (emMemoria) { return emMemoria.removeIf(aluno -> aluno.getId() == id); }
        }
        String sql = "DELETE FROM tb_alunos WHERE id=?";
        try (Connection conexao = conn; PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Aluno> listarTodos() {
        Connection conn = ConnectionFactory.getConnection();
        if (conn == null) {
            synchronized (emMemoria) { return new ArrayList<>(emMemoria); }
        }
        List<Aluno> lista = new ArrayList<>();
        String sql = "SELECT * FROM tb_alunos ORDER BY nome";
        try (Connection conexao = conn; PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) lista.add(montarAluno(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Aluno buscar(int id) {
        for (Aluno aluno : listarTodos()) if (aluno.getId() == id) return aluno;
        return null;
    }

    public boolean cpfExiste(String cpf, int ignorarId) {
        String cpfLimpo = limparCpf(cpf);
        for (Aluno aluno : listarTodos()) if (aluno.getId() != ignorarId && limparCpf(aluno.getCpf()).equals(cpfLimpo)) return true;
        return false;
    }

    public List<Aluno> pesquisar(String nome, String email, String plano, String status) {
        List<Aluno> resultado = new ArrayList<>();
        String filtroNome = texto(nome).toLowerCase();
        String filtroEmail = texto(email).toLowerCase();
        for (Aluno aluno : listarTodos()) {
            boolean bateNome = filtroNome.isEmpty() || texto(aluno.getNome()).toLowerCase().contains(filtroNome);
            boolean bateEmail = filtroEmail.isEmpty() || texto(aluno.getEmail()).toLowerCase().contains(filtroEmail);
            boolean batePlano = plano == null || plano.equalsIgnoreCase("Todos") || texto(aluno.getPlano()).equalsIgnoreCase(plano);
            boolean bateStatus = status == null || status.equalsIgnoreCase("Todos") || texto(aluno.getStatus()).equalsIgnoreCase(status);
            if (bateNome && bateEmail && batePlano && bateStatus) resultado.add(aluno);
        }
        return resultado;
    }

    public List<Aluno> listarAtivos() {
        List<Aluno> ativos = new ArrayList<>();
        for (Aluno aluno : listarTodos()) if (aluno.isAtivo()) ativos.add(aluno);
        return ativos;
    }

    public boolean planoEmUso(String nomePlano) {
        for (Aluno aluno : listarTodos()) {
            if (texto(aluno.getPlano()).equalsIgnoreCase(texto(nomePlano))) return true;
        }
        return false;
    }

    public void atualizarNomePlano(String nomeAnterior, String nomeNovo) {
        Connection conn = ConnectionFactory.getConnection();
        if (conn == null) {
            synchronized (emMemoria) {
                for (Aluno aluno : emMemoria) {
                    if (texto(aluno.getPlano()).equalsIgnoreCase(texto(nomeAnterior))) {
                        aluno.setPlano(nomeNovo);
                    }
                }
            }
            return;
        }
        String sql = "UPDATE tb_alunos SET plano=? WHERE LOWER(plano)=LOWER(?)";
        try (Connection conexao = conn; PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, nomeNovo);
            stmt.setString(2, nomeAnterior);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void preencher(PreparedStatement stmt, Aluno aluno) throws Exception {
        stmt.setString(1, aluno.getNome());
        stmt.setString(2, aluno.getCpf());
        stmt.setString(3, aluno.getEmail());
        stmt.setString(4, aluno.getTelefone());
        if (aluno.getDataNascimento() == null) stmt.setDate(5, null);
        else stmt.setDate(5, Date.valueOf(aluno.getDataNascimento()));
        stmt.setString(6, aluno.getEndereco());
        stmt.setString(7, aluno.getNumero());
        stmt.setString(8, aluno.getBairro());
        stmt.setString(9, aluno.getCidade());
        stmt.setString(10, aluno.getEstado());
        stmt.setString(11, aluno.getPlano());
        stmt.setString(12, aluno.getStatus());
    }

    private Aluno montarAluno(ResultSet rs) throws Exception {
        Date nascimento = rs.getDate("data_nascimento");
        return new Aluno(rs.getInt("id"), rs.getString("nome"), rs.getString("cpf"), rs.getString("email"), rs.getString("telefone"), nascimento == null ? null : nascimento.toLocalDate(), rs.getString("endereco"), rs.getString("numero"), rs.getString("bairro"), rs.getString("cidade"), rs.getString("estado"), rs.getString("plano"), rs.getString("status"));
    }

    private String limparCpf(String cpf) {
        return texto(cpf).replaceAll("\\D", "");
    }

    private String texto(String valor) {
        return valor == null ? "" : valor.trim();
    }

    private boolean valido(Aluno aluno, int ignorarId) {
        return aluno != null
                && !texto(aluno.getNome()).isEmpty()
                && CpfValidator.isValid(aluno.getCpf())
                && !cpfExiste(aluno.getCpf(), ignorarId)
                && ("Ativo".equalsIgnoreCase(aluno.getStatus())
                    || "Inativo".equalsIgnoreCase(aluno.getStatus()));
    }
}
