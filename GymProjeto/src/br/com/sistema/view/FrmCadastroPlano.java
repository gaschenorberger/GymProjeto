package br.com.sistema.view;

import br.com.sistema.dao.PlanoDAO;
import br.com.sistema.model.Plano;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class FrmCadastroPlano extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField txtId;
    private JTextField txtNome;
    private JTextField txtDuracao;
    private JTextField txtValor;
    private JComboBox<String> cbSituacao;
    private JTextArea txtDescricao;

    private JButton btnSalvar;
    private JButton btnEditar;
    private JButton btnExcluir;
    private JButton btnLimpar;
    private JButton btnVoltar;

    private JTable tabelaPlanos;
    private DefaultTableModel modelTabela;

    public FrmCadastroPlano() {
        initComponents();
        carregarTabela();
    }

    private void initComponents() {
        setTitle("GymProjeto - Cadastro de Plano");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 720);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(242, 242, 242));
        setLayout(new BorderLayout());

        // Header Superior
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
        JLabel lblTitulo = new JLabel("Cadastro de Plano", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(20, 20, 20));

        JLabel lblSub = new JLabel("Preencha os dados do plano e clique em Salvar", SwingConstants.CENTER);
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

        // Painel Dados do Plano
        JPanel pnlDados = new JPanel(new GridBagLayout());
        pnlDados.setBackground(new Color(250, 250, 250));
        pnlDados.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                "Dados do Plano", 0, 0,
                new Font("Segoe UI", Font.BOLD, 13), new Color(40, 40, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 12, 6, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtId = new JTextField(); // Campo ID oculto/auxiliar

        // Linha 1: Nome do Plano | Duração (meses)
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 0.5;
        pnlDados.add(new JLabel("Nome do Plano:"), gbc);

        gbc.gridx = 1;
        pnlDados.add(new JLabel("Duração (meses):"), gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        txtNome = new JTextField();
        txtNome.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlDados.add(txtNome, gbc);

        gbc.gridx = 1;
        txtDuracao = new JTextField();
        txtDuracao.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlDados.add(txtDuracao, gbc);

        // Linha 2: Valor | Situação
        gbc.gridy = 2;
        gbc.gridx = 0;
        pnlDados.add(new JLabel("Valor:"), gbc);

        gbc.gridx = 1;
        pnlDados.add(new JLabel("Situação:"), gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        txtValor = new JTextField();
        txtValor.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlDados.add(txtValor, gbc);

        gbc.gridx = 1;
        cbSituacao = new JComboBox<>(new String[]{"Ativo", "Inativo"});
        cbSituacao.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlDados.add(cbSituacao, gbc);

        // Linha 3: Descrição
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        pnlDados.add(new JLabel("Descrição:"), gbc);

        gbc.gridy = 5;
        txtDescricao = new JTextArea(3, 40);
        txtDescricao.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtDescricao.setLineWrap(true);
        JScrollPane scrollDesc = new JScrollPane(txtDescricao);
        pnlDados.add(scrollDesc, gbc);

        pnlCenter.add(pnlDados, BorderLayout.NORTH);

        // Painel Ações (Botões Obrigatórios)
        JPanel pnlAcoes = new JPanel(new GridLayout(1, 4, 15, 0));
        pnlAcoes.setBackground(new Color(250, 250, 250));
        pnlAcoes.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                "Ações", 0, 0,
                new Font("Segoe UI", Font.BOLD, 13), new Color(40, 40, 40)
        ));

        btnSalvar = criarBotaoAcao("Salvar");
        btnEditar = criarBotaoAcao("Editar");
        btnExcluir = criarBotaoAcao("Excluir");
        btnLimpar = criarBotaoAcao("Limpar Campos");

        btnSalvar.addActionListener(e -> salvarPlano());
        btnEditar.addActionListener(e -> editarPlano());
        btnExcluir.addActionListener(e -> excluirPlano());
        btnLimpar.addActionListener(e -> limparCampos());

        pnlAcoes.add(btnSalvar);
        pnlAcoes.add(btnEditar);
        pnlAcoes.add(btnExcluir);
        pnlAcoes.add(btnLimpar);

        // Painel Tabela de Registros
        JPanel pnlTabela = new JPanel(new BorderLayout());
        pnlTabela.setOpaque(false);
        pnlTabela.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                "Planos Cadastrados (Clique em uma linha para carregar)", 0, 0,
                new Font("Segoe UI", Font.BOLD, 13), new Color(40, 40, 40)
        ));

        modelTabela = new DefaultTableModel(new Object[]{"ID", "Nome do Plano", "Duração (meses)", "Valor (R$)", "Situação", "Descrição"}, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaPlanos = new JTable(modelTabela);
        tabelaPlanos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabelaPlanos.setRowHeight(25);
        tabelaPlanos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabelaPlanos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                carregarCamposDaTabela();
            }
        });

        pnlTabela.add(new JScrollPane(tabelaPlanos), BorderLayout.CENTER);

        JPanel pnlConteudoCentral = new JPanel(new BorderLayout(0, 10));
        pnlConteudoCentral.setOpaque(false);
        pnlConteudoCentral.add(pnlAcoes, BorderLayout.NORTH);
        pnlConteudoCentral.add(pnlTabela, BorderLayout.CENTER);

        pnlCenter.add(pnlConteudoCentral, BorderLayout.CENTER);
        add(pnlCenter, BorderLayout.CENTER);

        // Rodapé
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
        btn.setPreferredSize(new Dimension(140, 40));
        return btn;
    }

    private void carregarTabela() {
        modelTabela.setRowCount(0);
        PlanoDAO dao = new PlanoDAO();
        List<Plano> lista = dao.listarTodos();
        for (Plano p : lista) {
            modelTabela.addRow(new Object[]{
                p.getId(),
                p.getNome(),
                p.getDuracaoMeses(),
                String.format("R$ %.2f", p.getValor()),
                p.getSituacao(),
                p.getDescricao()
            });
        }
    }

    private void carregarCamposDaTabela() {
        int linha = tabelaPlanos.getSelectedRow();
        if (linha != -1) {
            txtId.setText(modelTabela.getValueAt(linha, 0).toString());
            txtNome.setText(modelTabela.getValueAt(linha, 1).toString());
            txtDuracao.setText(modelTabela.getValueAt(linha, 2).toString());
            
            String valStr = modelTabela.getValueAt(linha, 3).toString().replace("R$", "").replace(",", ".").trim();
            txtValor.setText(valStr);
            
            cbSituacao.setSelectedItem(modelTabela.getValueAt(linha, 4).toString());
            txtDescricao.setText(modelTabela.getValueAt(linha, 5).toString());
        }
    }

    private void salvarPlano() {
        try {
            Plano p = montarPlano();
            if (!validarPlano(p)) return;
            PlanoDAO dao = new PlanoDAO();
            if (dao.salvar(p)) {
                JOptionPane.showMessageDialog(this, "Plano cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
                carregarTabela();
            } else JOptionPane.showMessageDialog(this, "Não foi possível cadastrar o plano.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha a Duração e o Valor com números válidos!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarPlano() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um plano na tabela para editar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Plano p = montarPlano();
            p.setId(Integer.parseInt(txtId.getText()));
            if (!validarPlano(p)) return;
            PlanoDAO dao = new PlanoDAO();
            if (dao.editar(p)) {
                JOptionPane.showMessageDialog(this, "Plano atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
                carregarTabela();
            } else JOptionPane.showMessageDialog(this, "Não foi possível atualizar o plano.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, verifique os campos numéricos!", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirPlano() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um plano na tabela para excluir!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int resp = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir este plano?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (resp == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(txtId.getText());
            PlanoDAO dao = new PlanoDAO();
            if (dao.excluir(id)) {
                JOptionPane.showMessageDialog(this, "Plano excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
                carregarTabela();
            } else JOptionPane.showMessageDialog(this, "Não foi possível excluir o plano. Verifique se ele está vinculado a alunos ou matrículas.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Plano montarPlano() {
        Plano plano = new Plano();
        plano.setNome(txtNome.getText().trim());
        plano.setDuracaoMeses(Integer.parseInt(txtDuracao.getText().trim()));
        plano.setValor(Double.parseDouble(txtValor.getText().trim().replace("R$", "").replace(",", ".")));
        plano.setSituacao(cbSituacao.getSelectedItem().toString());
        plano.setDescricao(txtDescricao.getText().trim());
        return plano;
    }

    private boolean validarPlano(Plano plano) {
        if (plano.getNome().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, informe o Nome do Plano!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (plano.getDuracaoMeses() <= 0 || plano.getValor() <= 0
                || Double.isNaN(plano.getValor()) || Double.isInfinite(plano.getValor())) {
            JOptionPane.showMessageDialog(this, "A Duração e o Valor devem ser maiores que zero!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtDuracao.setText("");
        txtValor.setText("");
        cbSituacao.setSelectedIndex(0);
        txtDescricao.setText("");
        tabelaPlanos.clearSelection();
    }

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {}

        SwingUtilities.invokeLater(() -> {
            new FrmCadastroPlano().setVisible(true);
        });
    }
}
