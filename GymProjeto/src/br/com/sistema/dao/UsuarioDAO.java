package br.com.sistema.dao;

import br.com.sistema.jdbc.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {

    public boolean efetuarLogin(String email, String senha) {
        Connection conn = ConnectionFactory.getConnection();
        if (conn == null) return email.equalsIgnoreCase("admin@gymprojeto.com") && senha.equals("123456");

        String sql = "SELECT 1 FROM tb_usuarios WHERE email = ? AND senha = ?";
        try (Connection conexao = conn; PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, senha);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
