package br.com.sistema.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class FrmMenuPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;

    public FrmMenuPrincipal() {
        initComponents();
    }

    private void initComponents() {
        setTitle("GymProjeto - Menu Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(242, 242, 242));
        setLayout(new BorderLayout());
        setJMenuBar(criarBarraMenu());

        // Painel Superior (Header)
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(Color.WHITE);
        pnlHeader.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(15, 25, 15, 25)
        ));

        // Esquerda Logo
        JLabel lblLogo = new JLabel("GYMPROJETO");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblLogo.setForeground(new Color(20, 20, 20));
        pnlHeader.add(lblLogo, BorderLayout.WEST);

        // Direita Usuário + Sair
        JPanel pnlUserSair = new JPanel();
        pnlUserSair.setOpaque(false);
        JLabel lblUsuario = new JLabel("Usuário: admin   ");
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblUsuario.setForeground(new Color(60, 60, 60));

        JButton btnSair = new JButton("Sair");
        btnSair.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnSair.setFocusPainted(false);
        btnSair.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSair.addActionListener(e -> sairDoSistema());

        pnlUserSair.add(lblUsuario);
        pnlUserSair.add(btnSair);
        pnlHeader.add(pnlUserSair, BorderLayout.EAST);

        add(pnlHeader, BorderLayout.NORTH);

        // Painel Central (Boas-vindas e Botões)
        JPanel pnlCenter = new JPanel(new GridBagLayout());
        pnlCenter.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 5, 10);

        JLabel lblWelcome = new JLabel("Bem-vindo(a)!", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblWelcome.setForeground(new Color(20, 20, 20));
        pnlCenter.add(lblWelcome, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 30, 10);
        JLabel lblSub = new JLabel("O que deseja fazer?", SwingConstants.CENTER);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblSub.setForeground(new Color(100, 100, 100));
        pnlCenter.add(lblSub, gbc);

        // Grid de Botões Principais
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        JPanel pnlButtons = new JPanel(new GridLayout(2, 2, 25, 25));
        pnlButtons.setOpaque(false);

        // 1. Cadastrar Aluno
        JButton btnCadAluno = criarBotaoMenu("👥", "Cadastrar Aluno");
        btnCadAluno.addActionListener(e -> {
            new FrmCadastroAluno().setVisible(true);
            this.dispose();
        });

        // 2. Cadastrar Plano
        JButton btnCadPlano = criarBotaoMenu("📋", "Cadastrar Plano");
        btnCadPlano.addActionListener(e -> {
            new FrmCadastroPlano().setVisible(true);
            this.dispose();
        });

        // 3. Listar Alunos
        JButton btnListAlunos = criarBotaoMenu("📑", "Listar Alunos");
        btnListAlunos.addActionListener(e -> {
            new FrmListarAlunos().setVisible(true);
            this.dispose();
        });

        // 4. Cadastrar Matrícula
        JButton btnCadMatricula = criarBotaoMenu("📝", "Cadastrar Matrícula");
        btnCadMatricula.addActionListener(e -> {
            new FrmCadastroMatricula().setVisible(true);
            this.dispose();
        });

        pnlButtons.add(btnCadAluno);
        pnlButtons.add(btnCadPlano);
        pnlButtons.add(btnListAlunos);
        pnlButtons.add(btnCadMatricula);

        pnlCenter.add(pnlButtons, gbc);
        add(pnlCenter, BorderLayout.CENTER);
    }

    private JButton criarBotaoMenu(String icone, String texto) {
        JButton btn = new JButton("<html><center><font size='6'>" + icone + "</font><br><br><font size='4'><b>" + texto + "</b></font></center></html>");
        btn.setPreferredSize(new Dimension(220, 110));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(30, 30, 30));
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        return btn;
    }

    private JMenuBar criarBarraMenu() {
        JMenuBar barra = new JMenuBar();
        JMenu cadastros = new JMenu("Cadastros");
        JMenuItem alunos = new JMenuItem("Alunos");
        JMenuItem planos = new JMenuItem("Planos");
        JMenuItem matriculas = new JMenuItem("Matrículas");
        alunos.addActionListener(e -> abrirTela(new FrmCadastroAluno()));
        planos.addActionListener(e -> abrirTela(new FrmCadastroPlano()));
        matriculas.addActionListener(e -> abrirTela(new FrmCadastroMatricula()));
        cadastros.add(alunos);
        cadastros.add(planos);
        cadastros.add(matriculas);

        JMenu consultas = new JMenu("Consultas");
        JMenuItem listarAlunos = new JMenuItem("Listar alunos");
        listarAlunos.addActionListener(e -> abrirTela(new FrmListarAlunos()));
        consultas.add(listarAlunos);

        JMenu sistema = new JMenu("Sistema");
        JMenuItem sair = new JMenuItem("Sair");
        sair.addActionListener(e -> sairDoSistema());
        sistema.add(sair);
        barra.add(cadastros);
        barra.add(consultas);
        barra.add(sistema);
        return barra;
    }

    private void abrirTela(JFrame tela) {
        tela.setVisible(true);
        dispose();
    }

    private void sairDoSistema() {
        int resposta = JOptionPane.showConfirmDialog(this, "Deseja realmente sair do sistema?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (resposta == JOptionPane.YES_OPTION) {
            dispose();
            new FrmLogin().setVisible(true);
        }
    }

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {}

        SwingUtilities.invokeLater(() -> {
            new FrmMenuPrincipal().setVisible(true);
        });
    }
}
