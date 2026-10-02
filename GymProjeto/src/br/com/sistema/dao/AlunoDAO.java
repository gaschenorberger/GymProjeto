package br.com.sistema.dao;

import br.com.sistema.jdbc.ConnectionFactory;
import br.com.sistema.model.Aluno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    private static final List<Aluno> emMemoria = new ArrayList<>();
    private static int proximoId = 1;

    static {
        // Dados de alunos exatamente conforme exibidos na Imagem 4 (Listar Alunos)
        emMemoria.add(new Aluno(proximoId++, "João Silva", "111.222.333-44", "joao@email.com", "(45) 99999-1111", "15/03/1995", "Rua das Flores", "100", "Centro", "Cascavel", "PR", "Musculação", "Ativo"));
        emMemoria.add(new Aluno(proximoId++, "Maria Oliveira", "222.333.444-55", "maria@email.com", "(45) 98888-2222", "20/07/1998", "Av. Brasil", "500", "São Cristóvão", "Cascavel", "PR", "Funcional", "Ativo"));
        emMemoria.add(new Aluno(proximoId++, "Pedro Costa", "333.444.555-66", "pedro@email.com", "(45) 97777-3333", "10/01/1990", "Rua Paraná", "250", "Centro", "Cascavel", "PR", "Musculação", "Inativo"));
        emMemoria.add(new Aluno(proximoId++, "Ana Souza", "444.555.666-77", "ana@email.com", "(45) 96666-4444", "05/11/2000", "Rua das Palmeiras", "70", "Neva", "Cascavel", "PR", "Crossfit", "Ativo"));
        emMemoria.add(new Aluno(proximoId++, "Lucas Ferreira", "555.666.777-88", "lucas@email.com", "(45) 95555-5555", "12/08/1993", "Rua Rio de Janeiro", "1200", "Centro", "Cascavel", "PR", "Musculação", "Ativo"));
    }

    public void salvar(Aluno a) {
        Connection conn = ConnectionFactory.getConnection();
        if (conn != null) {
            try {
                String sql = "INSERT INTO tb_alunos (nome, cpf, email, telefone, data_nascimento, endereco, numero, bairro, cidade, estado, plano, status) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, a.getNome());
                stmt.setString(2, a.getCpf());
                stmt.setString(3, a.getEmail());
                stmt.setString(4, a.getTelefone());
                stmt.setString(5, a.getDataNascimento());
                stmt.setString(6, a.getEndereco());
                stmt.setString(7, a.getNumero());
                stmt.setString(8, a.getBairro());
                stmt.setString(9, a.getCidade());
                stmt.setString(10, a.getEstado());
                stmt.setString(11, a.getPlano());
                stmt.setString(12, a.getStatus());
                stmt.execute();
                stmt.close();
                conn.close();
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Fallback em memória
        a.setId(proximoId++);
        emMemoria.add(a);
    }

    public void editar(Aluno a) {
        Connection conn = ConnectionFactory.getConnection();
        if (conn != null) {
            try {
                String sql = "UPDATE tb_alunos SET nome=?, cpf=?, email=?, telefone=?, data_nascimento=?, endereco=?, numero=?, bairro=?, cidade=?, estado=?, plano=?, status=? WHERE id=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, a.getNome());
                stmt.setString(2, a.getCpf());
                stmt.setString(3, a.getEmail());
                stmt.setString(4, a.getTelefone());
                stmt.setString(5, a.getDataNascimento());
                stmt.setString(6, a.getEndereco());
                stmt.setString(7, a.getNumero());
                stmt.setString(8, a.getBairro());
                stmt.setString(9, a.getCidade());
                stmt.setString(10, a.getEstado());
                stmt.setString(11, a.getPlano());
                stmt.setString(12, a.getStatus());
                stmt.setInt(13, a.getId());
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
            if (emMemoria.get(i).getId() == a.getId()) {
                emMemoria.set(i, a);
                break;
            }
        }
    }

    public void excluir(int id) {
        Connection conn = ConnectionFactory.getConnection();
        if (conn != null) {
            try {
                String sql = "DELETE FROM tb_alunos WHERE id=?";
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
        emMemoria.removeIf(a -> a.getId() == id);
    }

    public List<Aluno> listarTodos() {
        Connection conn = ConnectionFactory.getConnection();
        if (conn != null) {
            try {
                List<Aluno> lista = new ArrayList<>();
                String sql = "SELECT * FROM tb_alunos";
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Aluno a = new Aluno();
                    a.setId(rs.getInt("id"));
                    a.setNome(rs.getString("nome"));
                    a.setCpf(rs.getString("cpf"));
                    a.setEmail(rs.getString("email"));
                    a.setTelefone(rs.getString("telefone"));
                    a.setDataNascimento(rs.getString("data_nascimento"));
                    a.setEndereco(rs.getString("endereco"));
                    a.setNumero(rs.getString("numero"));
                    a.setBairro(rs.getString("bairro"));
                    a.setCidade(rs.getString("cidade"));
                    a.setEstado(rs.getString("estado"));
                    a.setPlano(rs.getString("plano"));
                    a.setStatus(rs.getString("status"));
                    lista.add(a);
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

    public List<Aluno> pesquisar(String nome, String email, String plano, String status) {
        List<Aluno> todos = listarTodos();
        List<Aluno> resultado = new ArrayList<>();

        for (Aluno a : todos) {
            boolean bateNome = (nome == null || nome.trim().isEmpty() || a.getNome().toLowerCase().contains(nome.toLowerCase()));
            boolean bateEmail = (email == null || email.trim().isEmpty() || a.getEmail().toLowerCase().contains(email.toLowerCase()));
            boolean batePlano = (plano == null || plano.equalsIgnoreCase("Todos") || a.getPlano().equalsIgnoreCase(plano));
            boolean bateStatus = (status == null || status.equalsIgnoreCase("Todos") || a.getStatus().equalsIgnoreCase(status));

            if (bateNome && bateEmail && batePlano && bateStatus) {
                resultado.add(a);
            }
        }

        return resultado;
    }
}
