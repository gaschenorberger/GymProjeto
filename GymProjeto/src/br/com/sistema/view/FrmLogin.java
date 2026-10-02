package br.com.sistema.view;

import br.com.sistema.dao.UsuarioDAO;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class FrmLogin extends JFrame {

    private JTextField txtEmail;
    private JPasswordField txtSenha;
    private JButton btnEntrar;

    public FrmLogin() {
        initComponents();
    }

    private void initComponents() {
        setTitle("GymProjeto - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 450);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(242, 242, 242));
        setLayout(new GridBagLayout());

        // Painel central estilo Card
        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        cardPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        // Título Academia
        JLabel lblSub = new JLabel("GymProjeto", SwingConstants.CENTER);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblSub.setForeground(new Color(80, 80, 80));
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        cardPanel.add(lblSub, gbc);

        // Bem-vindo(a)
        JLabel lblWelcome = new JLabel("Bem-vindo(a)", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblWelcome.setForeground(new Color(20, 20, 20));
        gbc.gridy = 1;
        gbc.insets = new Insets(2, 10, 20, 10);
        cardPanel.add(lblWelcome, gbc);

        // Subpainel dos campos
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridwidth = 1;

        // Label E-mail
        JLabel lblEmail = new JLabel("E-mail:");
        lblEmail.setFont(new Font("Segoe UI", Font.BOLD, 13));
        gbc.gridy = 2;
        gbc.gridx = 0;
        cardPanel.add(lblEmail, gbc);

        // Label Senha
        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setFont(new Font("Segoe UI", Font.BOLD, 13));
        gbc.gridx = 1;
        cardPanel.add(lblSenha, gbc);

        // Campo E-mail
        txtEmail = new JTextField(15);
        txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtEmail.setText("admin@gymprojeto.com");
        gbc.gridy = 3;
        gbc.gridx = 0;
        cardPanel.add(txtEmail, gbc);

        // Campo Senha
        txtSenha = new JPasswordField(15);
        txtSenha.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtSenha.setText("123456");
        gbc.gridx = 1;
        cardPanel.add(txtSenha, gbc);

        // Botão ENTRAR
        btnEntrar = new JButton("ENTRAR");
        btnEntrar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnEntrar.setFocusPainted(false);
        btnEntrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEntrar.setBackground(new Color(235, 235, 235));
        btnEntrar.setForeground(new Color(40, 40, 40));

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 5, 5, 5);
        cardPanel.add(btnEntrar, gbc);

        // Ação do Botão Entrar
        btnEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                efetuarLogin();
            }
        });

        // Ação de pressionar Enter na senha
        txtSenha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                efetuarLogin();
            }
        });

        add(cardPanel);
    }

    private void efetuarLogin() {
        String email = txtEmail.getText().trim();
        String senha = new String(txtSenha.getPassword()).trim();

        if (email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos para continuar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();
        if (dao.efetuarLogin(email, senha)) {
            JOptionPane.showMessageDialog(this, "Seja bem-vindo(a) ao GymProjeto!", "Login Aprovado", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            new FrmMenuPrincipal().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos!", "Erro de Autenticação", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {}

        SwingUtilities.invokeLater(() -> {
            new FrmLogin().setVisible(true);
        });
    }
}
