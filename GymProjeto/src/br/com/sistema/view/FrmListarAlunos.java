package br.com.sistema.view;

import br.com.sistema.dao.AlunoDAO;
import br.com.sistema.dao.PlanoDAO;
import br.com.sistema.model.Aluno;
import br.com.sistema.model.Plano;
import br.com.sistema.util.DateUtils;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class FrmListarAlunos extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField txtFiltroNome;
    private JTextField txtFiltroEmail;
    private JComboBox<String> cbFiltroPlano;
    private JComboBox<String> cbFiltroStatus;
    private JButton btnPesquisar;
    private JButton btnVoltar;

    private JTable tabelaAlunos;
    private DefaultTableModel modelTabela;
    private JLabel lblTotalAlunos;

    private JButton btnEditar;
    private JButton btnExcluir;
    private JButton btnVerDetalhes;

    public FrmListarAlunos() {
        initComponents();
        carregarPlanosFiltro();
        pesquisarAlunos();
    }

    private void initComponents() {
        setTitle("GymProjeto - Listar Alunos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(242, 242, 242));
        setLayout(new BorderLayout());

        // Header
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setOpaque(false);
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        btnVoltar = new JButton("< Voltar ao Menu");
        btnVoltar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnVoltar.setFocusPainted(false);
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.addActionListener(e -> {
            new FrmMenuPrincipal().setVisible(true);
            this.dispose();
        });
        pnlHeader.add(btnVoltar, BorderLayout.WEST);

        JPanel pnlTitle = new JPanel(new GridLayout(2, 1, 0, 5));
        pnlTitle.setOpaque(false);
        JLabel lblTitulo = new JLabel("Listar Alunos", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(20, 20, 20));

        JLabel lblSub = new JLabel("Visualize os alunos cadastrados no sistema", SwingConstants.CENTER);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSub.setForeground(new Color(100, 100, 100));

        pnlTitle.add(lblTitulo);
        pnlTitle.add(lblSub);
        pnlHeader.add(pnlTitle, BorderLayout.CENTER);

        add(pnlHeader, BorderLayout.NORTH);

        // Painel Central
        JPanel pnlCenter = new JPanel(new BorderLayout(0, 15));
        pnlCenter.setOpaque(false);
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Painel Filtros
        JPanel pnlFiltros = new JPanel(new GridBagLayout());
        pnlFiltros.setBackground(new Color(250, 250, 250));
        pnlFiltros.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                "Filtros", 0, 0,
                new Font("Segoe UI", Font.BOLD, 13), new Color(40, 40, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Linha 1 Labels
        gbc.gridy = 0; gbc.gridx = 0; gbc.weightx = 0.25;
        pnlFiltros.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.25;
        pnlFiltros.add(new JLabel("E-mail:"), gbc);
        gbc.gridx = 2; gbc.weightx = 0.2;
        pnlFiltros.add(new JLabel("Plano:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.2;
        pnlFiltros.add(new JLabel("Status:"), gbc);

        // Linha 2 Componentes
        gbc.gridy = 1; gbc.gridx = 0;
        txtFiltroNome = new JTextField();
        txtFiltroNome.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlFiltros.add(txtFiltroNome, gbc);

        gbc.gridx = 1;
        txtFiltroEmail = new JTextField();
        txtFiltroEmail.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlFiltros.add(txtFiltroEmail, gbc);

        gbc.gridx = 2;
        cbFiltroPlano = new JComboBox<>(new String[]{"Todos"});
        cbFiltroPlano.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlFiltros.add(cbFiltroPlano, gbc);

        gbc.gridx = 3;
        cbFiltroStatus = new JComboBox<>(new String[]{"Todos", "Ativo", "Inativo"});
        cbFiltroStatus.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlFiltros.add(cbFiltroStatus, gbc);

        gbc.gridx = 4; gbc.weightx = 0.1;
        btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnPesquisar.setFocusPainted(false);
        btnPesquisar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPesquisar.addActionListener(e -> pesquisarAlunos());
        pnlFiltros.add(btnPesquisar, gbc);

        pnlCenter.add(pnlFiltros, BorderLayout.NORTH);

        // Painel Tabela Alunos
        JPanel pnlTabela = new JPanel(new BorderLayout());
        pnlTabela.setOpaque(false);
        pnlTabela.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                "Alunos", 0, 0,
                new Font("Segoe UI", Font.BOLD, 13), new Color(40, 40, 40)
        ));

        modelTabela = new DefaultTableModel(new Object[]{"ID", "Nome", "E-mail", "Telefone", "Plano", "Status"}, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaAlunos = new JTable(modelTabela);
        tabelaAlunos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabelaAlunos.setRowHeight(26);
        tabelaAlunos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));

        // Renderizador customizado para exibir o Status com a cor exata (Verde para Ativo, Vermelho para Inativo)
        tabelaAlunos.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value != null) {
                    String st = value.toString();
                    if (st.equalsIgnoreCase("Ativo")) {
                        c.setForeground(new Color(0, 130, 0));
                        setFont(getFont().deriveFont(Font.BOLD));
                    } else if (st.equalsIgnoreCase("Inativo")) {
                        c.setForeground(new Color(180, 0, 0));
                        setFont(getFont().deriveFont(Font.BOLD));
                    } else {
                        c.setForeground(Color.BLACK);
                    }
                }
                return c;
            }
        });

        pnlTabela.add(new JScrollPane(tabelaAlunos), BorderLayout.CENTER);

        // Painel Inferior da Tabela (Contador de Alunos + Botões de Ação)
        JPanel pnlTabelaFooter = new JPanel(new BorderLayout());
        pnlTabelaFooter.setOpaque(false);
        pnlTabelaFooter.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        lblTotalAlunos = new JLabel("Total de alunos: 0");
        lblTotalAlunos.setFont(new Font("Segoe UI", Font.BOLD, 13));
        pnlTabelaFooter.add(lblTotalAlunos, BorderLayout.WEST);

        JPanel pnlBotoesAcao = new JPanel(new GridLayout(1, 3, 10, 0));
        pnlBotoesAcao.setOpaque(false);

        btnEditar = criarBotaoAcao("Editar");
        btnExcluir = criarBotaoAcao("Excluir");
        btnVerDetalhes = criarBotaoAcao("Ver Detalhes");

        btnEditar.addActionListener(e -> editarAlunoSelecionado());
        btnExcluir.addActionListener(e -> excluirAlunoSelecionado());
        btnVerDetalhes.addActionListener(e -> verDetalhesAluno());

        pnlBotoesAcao.add(btnEditar);
        pnlBotoesAcao.add(btnExcluir);
        pnlBotoesAcao.add(btnVerDetalhes);

        pnlTabelaFooter.add(pnlBotoesAcao, BorderLayout.EAST);
        pnlTabela.add(pnlTabelaFooter, BorderLayout.SOUTH);

        pnlCenter.add(pnlTabela, BorderLayout.CENTER);
        add(pnlCenter, BorderLayout.CENTER);

        // Rodapé Geral
        JPanel pnlFooter = new JPanel(new BorderLayout());
        pnlFooter.setBackground(Color.WHITE);
        pnlFooter.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));

        JLabel lblFooter = new JLabel("GymProjeto | Sistema de Gerenciamento");
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblFooter.setForeground(new Color(100, 100, 100));

        JLabel lblUser = new JLabel("Usuário: admin");
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblUser.setForeground(new Color(100, 100, 100));

        pnlFooter.add(lblFooter, BorderLayout.WEST);
        pnlFooter.add(lblUser, BorderLayout.EAST);
        add(pnlFooter, BorderLayout.SOUTH);
    }

    private JButton criarBotaoAcao(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(130, 36));
        return btn;
    }

    private void pesquisarAlunos() {
        modelTabela.setRowCount(0);
        String nome = txtFiltroNome.getText().trim();
        String email = txtFiltroEmail.getText().trim();
        String plano = cbFiltroPlano.getSelectedItem().toString();
        String status = cbFiltroStatus.getSelectedItem().toString();

        AlunoDAO dao = new AlunoDAO();
        List<Aluno> lista = dao.pesquisar(nome, email, plano, status);

        for (Aluno a : lista) {
            modelTabela.addRow(new Object[]{
                a.getId(),
                a.getNome(),
                a.getEmail(),
                a.getTelefone(),
                a.getPlano(),
                a.getStatus()
            });
        }

        lblTotalAlunos.setText("Total de alunos: " + lista.size());
    }

    private void editarAlunoSelecionado() {
        int linha = tabelaAlunos.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um aluno na tabela para editar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = Integer.parseInt(modelTabela.getValueAt(linha, 0).toString());
        new FrmCadastroAluno(id).setVisible(true);
        this.dispose();
    }

    private void excluirAlunoSelecionado() {
        int linha = tabelaAlunos.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um aluno na tabela para excluir!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = Integer.parseInt(modelTabela.getValueAt(linha, 0).toString());
        String nome = modelTabela.getValueAt(linha, 1).toString();

        int resp = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o aluno " + nome + "?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (resp == JOptionPane.YES_OPTION) {
            AlunoDAO dao = new AlunoDAO();
            if (dao.excluir(id)) {
                JOptionPane.showMessageDialog(this, "Aluno excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                pesquisarAlunos();
            } else JOptionPane.showMessageDialog(this, "Não foi possível excluir o aluno. Verifique se ele possui matrículas vinculadas.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verDetalhesAluno() {
        int linha = tabelaAlunos.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um aluno na tabela para visualizar os detalhes!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = Integer.parseInt(modelTabela.getValueAt(linha, 0).toString());
        AlunoDAO dao = new AlunoDAO();
        Aluno a = dao.buscar(id);
        if (a != null) {
                String msg = "<html><body style='width: 300px; font-family: Segoe UI; padding: 5px;'>"
                        + "<h2><b>" + a.getNome() + "</b></h2>"
                        + "<hr>"
                        + "<p><b>ID:</b> " + a.getId() + "</p>"
                        + "<p><b>CPF:</b> " + a.getCpf() + "</p>"
                        + "<p><b>E-mail:</b> " + a.getEmail() + "</p>"
                        + "<p><b>Telefone:</b> " + a.getTelefone() + "</p>"
                        + "<p><b>Data Nasc.:</b> " + DateUtils.format(a.getDataNascimento()) + "</p>"
                        + "<p><b>Endereço:</b> " + a.getEndereco() + ", " + a.getNumero() + " - " + a.getBairro() + "</p>"
                        + "<p><b>Cidade/UF:</b> " + a.getCidade() + "/" + a.getEstado() + "</p>"
                        + "<p><b>Plano:</b> " + a.getPlano() + "</p>"
                        + "<p><b>Status:</b> " + a.getStatus() + "</p>"
                        + "</body></html>";

                JOptionPane.showMessageDialog(this, msg, "Ficha do Aluno", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
    }

    private void carregarPlanosFiltro() {
        for (Plano plano : new PlanoDAO().listarTodos()) cbFiltroPlano.addItem(plano.getNome());
    }

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {}

        SwingUtilities.invokeLater(() -> {
            new FrmListarAlunos().setVisible(true);
        });
    }
}
