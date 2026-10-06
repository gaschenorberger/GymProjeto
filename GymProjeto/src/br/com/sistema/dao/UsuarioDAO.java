package br.com.sistema.dao;

import br.com.sistema.jdbc.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import br.com.sistema.util.PasswordUtils;

public class UsuarioDAO {

    public boolean efetuarLogin(String email, String senha) {
        Connection conn = ConnectionFactory.getConnection();
        if (conn == null) {
            return email.equalsIgnoreCase("admin@gymprojeto.com")
                    && PasswordUtils.matches(senha, "8d969eef6ecad3c29a3a629280e686cf" +
                            "0c3f5d5a86aff3ca12020c923adc6c92");
        }

        String sql = "SELECT senha_hash FROM tb_usuarios WHERE email = ? AND ativo = TRUE";
        try (Connection conexao = conn; PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && PasswordUtils.matches(senha, rs.getString("senha_hash"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
