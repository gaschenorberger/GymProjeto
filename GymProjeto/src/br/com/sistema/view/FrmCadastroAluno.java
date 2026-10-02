package br.com.sistema.view;

import br.com.sistema.dao.AlunoDAO;
import br.com.sistema.model.Aluno;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class FrmCadastroAluno extends JFrame {

    private JTextField txtId;
    private JTextField txtNome;
    private JTextField txtCpf;
    private JTextField txtEmail;
    private JTextField txtEndereco;
    private JTextField txtTelefone;
    private JTextField txtNumero;
    private JTextField txtBairro;
    private JTextField txtDataNascimento;
    private JTextField txtCidade;
    private JComboBox<String> cbEstado;
    private JComboBox<String> cbPlano;
    private JComboBox<String> cbStatus;

    private JButton btnSalvar;
    private JButton btnEditar;
    private JButton btnExcluir;
    private JButton btnLimpar;
    private JButton btnVoltar;

    private JTable tabelaAlunos;
    private DefaultTableModel modelTabela;

    public FrmCadastroAluno() {
        initComponents();
        carregarTabela();
    }

    private void initComponents() {
        setTitle("GymProjeto - Cadastro de Aluno");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 780);
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
        JLabel lblTitulo = new JLabel("Cadastro de Aluno", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(20, 20, 20));

        JLabel lblSub = new JLabel("Preencha os dados do aluno e clique em Salvar", SwingConstants.CENTER);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSub.setForeground(new Color(100, 100, 100));

        pnlTitle.add(lblTitulo);
        pnlTitle.add(lblSub);
        pnlHeader.add(pnlTitle, BorderLayout.CENTER);

        add(pnlHeader, BorderLayout.NORTH);

        // Painel Central
        JPanel pnlCenter = new JPanel(new BorderLayout(0, 10));
        pnlCenter.setOpaque(false);
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Painel Dados do Aluno
        JPanel pnlDados = new JPanel(new GridBagLayout());
        pnlDados.setBackground(new Color(250, 250, 250));
        pnlDados.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                "Dados do Aluno", 0, 0,
                new Font("Segoe UI", Font.BOLD, 13), new Color(40, 40, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 10, 4, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtId = new JTextField(); // ID Oculto

        // Linha 1: Nome | CPF
        gbc.gridy = 0; gbc.gridx = 0; gbc.weightx = 0.5;
        pnlDados.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        pnlDados.add(new JLabel("CPF:"), gbc);

        gbc.gridy = 1; gbc.gridx = 0;
        txtNome = new JTextField();
        txtNome.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlDados.add(txtNome, gbc);

        gbc.gridx = 1;
        txtCpf = new JTextField();
        txtCpf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlDados.add(txtCpf, gbc);

        // Linha 2: E-mail | Endereço
        gbc.gridy = 2; gbc.gridx = 0;
        pnlDados.add(new JLabel("E-mail:"), gbc);
        gbc.gridx = 1;
        pnlDados.add(new JLabel("Endereço:"), gbc);

        gbc.gridy = 3; gbc.gridx = 0;
        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlDados.add(txtEmail, gbc);

        gbc.gridx = 1;
        txtEndereco = new JTextField();
        txtEndereco.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlDados.add(txtEndereco, gbc);

        // Linha 3: Telefone | Número | Bairro
        JPanel pnlLinha3Dir = new JPanel(new GridLayout(1, 2, 10, 0));
        pnlLinha3Dir.setOpaque(false);
        txtNumero = new JTextField();
        txtBairro = new JTextField();

        JPanel pnlNum = new JPanel(new BorderLayout(0, 2)); pnlNum.setOpaque(false);
        pnlNum.add(new JLabel("Número:"), BorderLayout.NORTH); pnlNum.add(txtNumero, BorderLayout.CENTER);
        JPanel pnlBai = new JPanel(new BorderLayout(0, 2)); pnlBai.setOpaque(false);
        pnlBai.add(new JLabel("Bairro:"), BorderLayout.NORTH); pnlBai.add(txtBairro, BorderLayout.CENTER);

        pnlLinha3Dir.add(pnlNum);
        pnlLinha3Dir.add(pnlBai);

        gbc.gridy = 4; gbc.gridx = 0;
        pnlDados.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1;
        pnlDados.add(pnlLinha3Dir, gbc);

        gbc.gridy = 5; gbc.gridx = 0;
        txtTelefone = new JTextField();
        txtTelefone.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlDados.add(txtTelefone, gbc);

        // Linha 4: Data de Nascimento | Cidade | Estado
        gbc.gridy = 6; gbc.gridx = 0;
        pnlDados.add(new JLabel("Data de Nascimento:"), gbc);

        JPanel pnlCidEst = new JPanel(new BorderLayout(10, 0)); pnlCidEst.setOpaque(false);
        txtCidade = new JTextField();
        cbEstado = new JComboBox<>(new String[]{"PR", "SP", "RJ", "SC", "RS", "MG", "BA"});
        pnlCidEst.add(txtCidade, BorderLayout.CENTER);
        pnlCidEst.add(cbEstado, BorderLayout.EAST);

        gbc.gridx = 1;
        pnlDados.add(new JLabel("Cidade / Estado:"), gbc);

        gbc.gridy = 7; gbc.gridx = 0;
        JPanel pnlData = new JPanel(new BorderLayout(5, 0)); pnlData.setOpaque(false);
        txtDataNascimento = new JTextField();
        JButton btnCal = new JButton("📅");
        btnCal.setMargin(new Insets(0, 4, 0, 4));
        pnlData.add(txtDataNascimento, BorderLayout.CENTER);
        pnlData.add(btnCal, BorderLayout.EAST);
        pnlDados.add(pnlData, gbc);

        gbc.gridx = 1;
        pnlDados.add(pnlCidEst, gbc);

        // Linha 5: Plano | Status
        gbc.gridy = 8; gbc.gridx = 0;
        pnlDados.add(new JLabel("Plano:"), gbc);
        gbc.gridx = 1;
        pnlDados.add(new JLabel("Status:"), gbc);

        gbc.gridy = 9; gbc.gridx = 0;
        cbPlano = new JComboBox<>(new String[]{"Musculação", "Funcional", "Crossfit"});
        pnlDados.add(cbPlano, gbc);

        gbc.gridx = 1;
        cbStatus = new JComboBox<>(new String[]{"Ativo", "Inativo"});
        pnlDados.add(cbStatus, gbc);

        pnlCenter.add(pnlDados, BorderLayout.NORTH);

        // Painel Ações (Botões Obrigatórios)
        JPanel pnlAcoes = new JPanel(new GridLayout(1, 4, 15, 0));
        pnlAcoes.setBackground(new Color(250, 250, 250));
        pnlAcoes.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                "Ações", 0, 0,
                new Font("Segoe UI", Font.BOLD, 13), new Color(40, 40, 40)
        ));

        btnSalvar = criarBotaoAcao("💾 Salvar");
        btnEditar = criarBotaoAcao("✏️ Editar");
        btnExcluir = criarBotaoAcao("🗑️ Excluir");
        btnLimpar = criarBotaoAcao("🧹 Limpar Campos");

        btnSalvar.addActionListener(e -> salvarAluno());
        btnEditar.addActionListener(e -> editarAluno());
        btnExcluir.addActionListener(e -> excluirAluno());
        btnLimpar.addActionListener(e -> limparCampos());

        pnlAcoes.add(btnSalvar);
        pnlAcoes.add(btnEditar);
        pnlAcoes.add(btnExcluir);
        pnlAcoes.add(btnLimpar);

        // Painel Tabela Alunos
        JPanel pnlTabela = new JPanel(new BorderLayout());
        pnlTabela.setOpaque(false);
        pnlTabela.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                "Alunos Cadastrados (Clique em uma linha para carregar)", 0, 0,
                new Font("Segoe UI", Font.BOLD, 13), new Color(40, 40, 40)
        ));

        modelTabela = new DefaultTableModel(new Object[]{"ID", "Nome", "CPF", "E-mail", "Telefone", "Data Nasc.", "Plano", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaAlunos = new JTable(modelTabela);
        tabelaAlunos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabelaAlunos.setRowHeight(24);
        tabelaAlunos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabelaAlunos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                carregarCamposDaTabela();
            }
        });

        pnlTabela.add(new JScrollPane(tabelaAlunos), BorderLayout.CENTER);

        JPanel pnlBottom = new JPanel(new BorderLayout(0, 10));
        pnlBottom.setOpaque(false);
        pnlBottom.add(pnlAcoes, BorderLayout.NORTH);
        pnlBottom.add(pnlTabela, BorderLayout.CENTER);

        pnlCenter.add(pnlBottom, BorderLayout.CENTER);
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
        btn.setPreferredSize(new Dimension(140, 38));
        return btn;
    }

    private void carregarTabela() {
        modelTabela.setRowCount(0);
        AlunoDAO dao = new AlunoDAO();
        List<Aluno> lista = dao.listarTodos();
        for (Aluno a : lista) {
            modelTabela.addRow(new Object[]{
                a.getId(),
                a.getNome(),
                a.getCpf(),
                a.getEmail(),
                a.getTelefone(),
                a.getDataNascimento(),
                a.getPlano(),
                a.getStatus()
            });
        }
    }

    private void carregarCamposDaTabela() {
        int linha = tabelaAlunos.getSelectedRow();
        if (linha != -1) {
            int id = Integer.parseInt(modelTabela.getValueAt(linha, 0).toString());
            AlunoDAO dao = new AlunoDAO();
            List<Aluno> todos = dao.listarTodos();
            for (Aluno a : todos) {
                if (a.getId() == id) {
                    txtId.setText(String.valueOf(a.getId()));
                    txtNome.setText(a.getNome());
                    txtCpf.setText(a.getCpf());
                    txtEmail.setText(a.getEmail());
                    txtTelefone.setText(a.getTelefone());
                    txtDataNascimento.setText(a.getDataNascimento());
                    txtEndereco.setText(a.getEndereco());
                    txtNumero.setText(a.getNumero());
                    txtBairro.setText(a.getBairro());
                    txtCidade.setText(a.getCidade());
                    cbEstado.setSelectedItem(a.getEstado());
                    cbPlano.setSelectedItem(a.getPlano());
                    cbStatus.setSelectedItem(a.getStatus());
                    break;
                }
            }
        }
    }

    private void salvarAluno() {
        if (txtNome.getText().trim().isEmpty() || txtEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha o Nome e E-mail do Aluno!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Aluno a = new Aluno();
        a.setNome(txtNome.getText().trim());
        a.setCpf(txtCpf.getText().trim());
        a.setEmail(txtEmail.getText().trim());
        a.setTelefone(txtTelefone.getText().trim());
        a.setDataNascimento(txtDataNascimento.getText().trim());
        a.setEndereco(txtEndereco.getText().trim());
        a.setNumero(txtNumero.getText().trim());
        a.setBairro(txtBairro.getText().trim());
        a.setCidade(txtCidade.getText().trim());
        a.setEstado(cbEstado.getSelectedItem().toString());
        a.setPlano(cbPlano.getSelectedItem().toString());
        a.setStatus(cbStatus.getSelectedItem().toString());

        AlunoDAO dao = new AlunoDAO();
        dao.salvar(a);

        JOptionPane.showMessageDialog(this, "Aluno cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        limparCampos();
        carregarTabela();
    }

    private void editarAluno() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um aluno na tabela para editar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Aluno a = new Aluno();
        a.setId(Integer.parseInt(txtId.getText()));
        a.setNome(txtNome.getText().trim());
        a.setCpf(txtCpf.getText().trim());
        a.setEmail(txtEmail.getText().trim());
        a.setTelefone(txtTelefone.getText().trim());
        a.setDataNascimento(txtDataNascimento.getText().trim());
        a.setEndereco(txtEndereco.getText().trim());
        a.setNumero(txtNumero.getText().trim());
        a.setBairro(txtBairro.getText().trim());
        a.setCidade(txtCidade.getText().trim());
        a.setEstado(cbEstado.getSelectedItem().toString());
        a.setPlano(cbPlano.getSelectedItem().toString());
        a.setStatus(cbStatus.getSelectedItem().toString());

        AlunoDAO dao = new AlunoDAO();
        dao.editar(a);

        JOptionPane.showMessageDialog(this, "Dados do aluno atualizados com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        limparCampos();
        carregarTabela();
    }

    private void excluirAluno() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um aluno na tabela para excluir!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int resp = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o aluno selecionado?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (resp == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(txtId.getText());
            AlunoDAO dao = new AlunoDAO();
            dao.excluir(id);
            JOptionPane.showMessageDialog(this, "Aluno excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
            carregarTabela();
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtCpf.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        txtDataNascimento.setText("");
        txtEndereco.setText("");
        txtNumero.setText("");
        txtBairro.setText("");
        txtCidade.setText("");
        cbEstado.setSelectedIndex(0);
        cbPlano.setSelectedIndex(0);
        cbStatus.setSelectedIndex(0);
        tabelaAlunos.clearSelection();
    }

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {}

        SwingUtilities.invokeLater(() -> {
            new FrmCadastroAluno().setVisible(true);
        });
    }
}
