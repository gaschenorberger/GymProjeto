package br.com.sistema.view;

import br.com.sistema.dao.AlunoDAO;
import br.com.sistema.dao.MatriculaDAO;
import br.com.sistema.dao.PlanoDAO;
import br.com.sistema.model.Aluno;
import br.com.sistema.model.Matricula;
import br.com.sistema.model.Plano;
import br.com.sistema.util.DateUtils;
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
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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

public class FrmCadastroMatricula extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField txtId;
    private JComboBox<Aluno> cbAluno;
    private JComboBox<Plano> cbPlano;
    private JTextField txtDataInicio;
    private JTextField txtDataVencimento;
    private JTextField txtValor;
    private JComboBox<String> cbSituacao;
    private JTable tabelaMatriculas;
    private DefaultTableModel modelTabela;

    public FrmCadastroMatricula() {
        initComponents();
        carregarOpcoes();
        limparCampos();
        carregarTabela();
    }

    private void initComponents() {
        setTitle("GymProjeto - Cadastro de Matrícula");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1050, 740);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(242, 242, 242));
        setLayout(new BorderLayout());

        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setOpaque(false);
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        JButton btnVoltar = new JButton("< Voltar ao Menu");
        btnVoltar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnVoltar.setFocusPainted(false);
        btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVoltar.addActionListener(e -> {
            new FrmMenuPrincipal().setVisible(true);
            dispose();
        });
        pnlHeader.add(btnVoltar, BorderLayout.WEST);

        JPanel pnlTitle = new JPanel(new GridLayout(2, 1, 0, 5));
        pnlTitle.setOpaque(false);
        JLabel lblTitulo = new JLabel("Cadastro de Matrícula", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(20, 20, 20));
        JLabel lblSub = new JLabel("Vincule um aluno a um plano e clique em Salvar", SwingConstants.CENTER);
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSub.setForeground(new Color(100, 100, 100));
        pnlTitle.add(lblTitulo);
        pnlTitle.add(lblSub);
        pnlHeader.add(pnlTitle, BorderLayout.CENTER);
        add(pnlHeader, BorderLayout.NORTH);

        JPanel pnlCenter = new JPanel(new BorderLayout(0, 15));
        pnlCenter.setOpaque(false);
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel pnlDados = new JPanel(new GridBagLayout());
        pnlDados.setBackground(new Color(250, 250, 250));
        pnlDados.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                "Dados da Matrícula", 0, 0,
                new Font("Segoe UI", Font.BOLD, 13), new Color(40, 40, 40)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 12, 6, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        txtId = new JTextField();

        gbc.gridy = 0; gbc.gridx = 0;
        pnlDados.add(new JLabel("Aluno:"), gbc);
        gbc.gridx = 1;
        pnlDados.add(new JLabel("Plano:"), gbc);

        gbc.gridy = 1; gbc.gridx = 0;
        cbAluno = new JComboBox<>();
        cbAluno.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlDados.add(cbAluno, gbc);
        gbc.gridx = 1;
        cbPlano = new JComboBox<>();
        cbPlano.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cbPlano.addActionListener(e -> atualizarDadosDoPlano());
        pnlDados.add(cbPlano, gbc);

        gbc.gridy = 2; gbc.gridx = 0;
        pnlDados.add(new JLabel("Data de início (dd/MM/aaaa):"), gbc);
        gbc.gridx = 1;
        pnlDados.add(new JLabel("Data de vencimento (dd/MM/aaaa):"), gbc);

        gbc.gridy = 3; gbc.gridx = 0;
        txtDataInicio = new JTextField();
        txtDataInicio.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtDataInicio.addActionListener(e -> atualizarVencimento());
        pnlDados.add(txtDataInicio, gbc);
        gbc.gridx = 1;
        txtDataVencimento = new JTextField();
        txtDataVencimento.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlDados.add(txtDataVencimento, gbc);

        gbc.gridy = 4; gbc.gridx = 0;
        pnlDados.add(new JLabel("Valor:"), gbc);
        gbc.gridx = 1;
        pnlDados.add(new JLabel("Situação:"), gbc);

        gbc.gridy = 5; gbc.gridx = 0;
        txtValor = new JTextField();
        txtValor.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlDados.add(txtValor, gbc);
        gbc.gridx = 1;
        cbSituacao = new JComboBox<>(new String[]{"Ativa", "Vencida", "Cancelada"});
        cbSituacao.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pnlDados.add(cbSituacao, gbc);
        pnlCenter.add(pnlDados, BorderLayout.NORTH);

        JPanel pnlAcoes = new JPanel(new GridLayout(1, 4, 15, 0));
        pnlAcoes.setBackground(new Color(250, 250, 250));
        pnlAcoes.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                "Ações", 0, 0,
                new Font("Segoe UI", Font.BOLD, 13), new Color(40, 40, 40)
        ));
        JButton btnSalvar = criarBotaoAcao("Salvar");
        JButton btnEditar = criarBotaoAcao("Editar");
        JButton btnExcluir = criarBotaoAcao("Excluir");
        JButton btnLimpar = criarBotaoAcao("Limpar Campos");
        btnSalvar.addActionListener(e -> salvarMatricula());
        btnEditar.addActionListener(e -> editarMatricula());
        btnExcluir.addActionListener(e -> excluirMatricula());
        btnLimpar.addActionListener(e -> limparCampos());
        pnlAcoes.add(btnSalvar);
        pnlAcoes.add(btnEditar);
        pnlAcoes.add(btnExcluir);
        pnlAcoes.add(btnLimpar);

        JPanel pnlTabela = new JPanel(new BorderLayout());
        pnlTabela.setOpaque(false);
        pnlTabela.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                "Matrículas Cadastradas (Clique em uma linha para carregar)", 0, 0,
                new Font("Segoe UI", Font.BOLD, 13), new Color(40, 40, 40)
        ));
        modelTabela = new DefaultTableModel(
                new Object[]{"ID", "Aluno", "Plano", "Início", "Vencimento", "Valor (R$)", "Situação"}, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabelaMatriculas = new JTable(modelTabela);
        tabelaMatriculas.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabelaMatriculas.setRowHeight(25);
        tabelaMatriculas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabelaMatriculas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { carregarCamposDaTabela(); }
        });
        pnlTabela.add(new JScrollPane(tabelaMatriculas), BorderLayout.CENTER);

        JPanel pnlConteudo = new JPanel(new BorderLayout(0, 10));
        pnlConteudo.setOpaque(false);
        pnlConteudo.add(pnlAcoes, BorderLayout.NORTH);
        pnlConteudo.add(pnlTabela, BorderLayout.CENTER);
        pnlCenter.add(pnlConteudo, BorderLayout.CENTER);
        add(pnlCenter, BorderLayout.CENTER);
    }

    private JButton criarBotaoAcao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setPreferredSize(new Dimension(140, 40));
        return botao;
    }

    private void carregarOpcoes() {
        cbAluno.removeAllItems();
        for (Aluno aluno : new AlunoDAO().listarAtivos()) cbAluno.addItem(aluno);
        cbPlano.removeAllItems();
        for (Plano plano : new PlanoDAO().listarAtivos()) cbPlano.addItem(plano);
    }

    private void carregarTabela() {
        modelTabela.setRowCount(0);
        AlunoDAO alunoDAO = new AlunoDAO();
        PlanoDAO planoDAO = new PlanoDAO();
        List<Matricula> lista = new MatriculaDAO().listarTodos();
        for (Matricula matricula : lista) {
            Aluno aluno = alunoDAO.buscar(matricula.getAlunoId());
            Plano plano = planoDAO.buscar(matricula.getPlanoId());
            modelTabela.addRow(new Object[]{
                matricula.getId(),
                aluno == null ? "Aluno não encontrado" : aluno.getNome(),
                plano == null ? "Plano não encontrado" : plano.getNome(),
                DateUtils.format(matricula.getDataInicio()),
                DateUtils.format(matricula.getDataVencimento()),
                String.format("R$ %.2f", matricula.getValor()),
                matricula.getSituacao()
            });
        }
    }

    private void carregarCamposDaTabela() {
        int linha = tabelaMatriculas.getSelectedRow();
        if (linha < 0) return;
        Matricula matricula = new MatriculaDAO().buscar(
                Integer.parseInt(modelTabela.getValueAt(linha, 0).toString()));
        if (matricula == null) return;
        txtId.setText(String.valueOf(matricula.getId()));
        selecionarAluno(matricula.getAlunoId());
        selecionarPlano(matricula.getPlanoId());
        txtDataInicio.setText(DateUtils.format(matricula.getDataInicio()));
        txtDataVencimento.setText(DateUtils.format(matricula.getDataVencimento()));
        txtValor.setText(String.format("%.2f", matricula.getValor()));
        cbSituacao.setSelectedItem(matricula.getSituacao());
    }

    private void salvarMatricula() {
        try {
            Matricula matricula = montarMatricula();
            if (!validarMatricula(matricula)) return;
            if (new MatriculaDAO().salvar(matricula)) {
                JOptionPane.showMessageDialog(this, "Matrícula cadastrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
                carregarTabela();
            } else mostrarErroOperacao("cadastrar");
        } catch (NumberFormatException | DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Confira o valor e informe as datas no formato dd/MM/aaaa.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarMatricula() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione uma matrícula na tabela para editar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Matricula matricula = montarMatricula();
            matricula.setId(Integer.parseInt(txtId.getText()));
            if (!validarMatricula(matricula)) return;
            if (new MatriculaDAO().editar(matricula)) {
                JOptionPane.showMessageDialog(this, "Matrícula atualizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
                carregarTabela();
            } else mostrarErroOperacao("atualizar");
        } catch (NumberFormatException | DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Confira o valor e informe as datas no formato dd/MM/aaaa.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirMatricula() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione uma matrícula na tabela para excluir!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int resposta = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir esta matrícula?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (resposta == JOptionPane.YES_OPTION) {
            if (new MatriculaDAO().excluir(Integer.parseInt(txtId.getText()))) {
                JOptionPane.showMessageDialog(this, "Matrícula excluída com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
                carregarTabela();
            } else mostrarErroOperacao("excluir");
        }
    }

    private Matricula montarMatricula() {
        Matricula matricula = new Matricula();
        Aluno aluno = (Aluno) cbAluno.getSelectedItem();
        Plano plano = (Plano) cbPlano.getSelectedItem();
        matricula.setAlunoId(aluno == null ? 0 : aluno.getId());
        matricula.setPlanoId(plano == null ? 0 : plano.getId());
        matricula.setDataInicio(DateUtils.parse(txtDataInicio.getText()));
        matricula.setDataVencimento(DateUtils.parse(txtDataVencimento.getText()));
        matricula.setValor(Double.parseDouble(txtValor.getText().trim().replace("R$", "").replace(",", ".")));
        matricula.setSituacao(cbSituacao.getSelectedItem().toString());
        return matricula;
    }

    private boolean validarMatricula(Matricula matricula) {
        Aluno aluno = new AlunoDAO().buscar(matricula.getAlunoId());
        Plano plano = new PlanoDAO().buscar(matricula.getPlanoId());
        if (aluno == null || plano == null) {
            JOptionPane.showMessageDialog(this, "Selecione um aluno e um plano existentes!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (matricula.getId() == 0 && !aluno.isAtivo()) {
            JOptionPane.showMessageDialog(this, "Somente alunos ativos podem receber uma matrícula!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (matricula.getId() == 0 && !"Ativo".equalsIgnoreCase(plano.getSituacao())) {
            JOptionPane.showMessageDialog(this, "Somente planos ativos podem ser utilizados!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (!matricula.getDataVencimento().isAfter(matricula.getDataInicio())) {
            JOptionPane.showMessageDialog(this, "A data de vencimento deve ser posterior à data de início!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (matricula.getValor() <= 0 || Double.isNaN(matricula.getValor())
                || Double.isInfinite(matricula.getValor())) {
            JOptionPane.showMessageDialog(this, "O valor da matrícula deve ser maior que zero!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void atualizarDadosDoPlano() {
        Plano plano = (Plano) cbPlano.getSelectedItem();
        if (plano == null || txtValor == null) return;
        txtValor.setText(String.format("%.2f", plano.getValor()));
        atualizarVencimento();
    }

    private void atualizarVencimento() {
        Plano plano = (Plano) cbPlano.getSelectedItem();
        if (plano == null || txtDataInicio == null || txtDataInicio.getText().trim().isEmpty()) return;
        try {
            txtDataVencimento.setText(DateUtils.format(
                    DateUtils.parse(txtDataInicio.getText()).plusMonths(plano.getDuracaoMeses())));
        } catch (DateTimeParseException ignored) {}
    }

    private void selecionarAluno(int id) {
        for (int i = 0; i < cbAluno.getItemCount(); i++) {
            if (cbAluno.getItemAt(i).getId() == id) { cbAluno.setSelectedIndex(i); return; }
        }
        Aluno aluno = new AlunoDAO().buscar(id);
        if (aluno != null) { cbAluno.addItem(aluno); cbAluno.setSelectedItem(aluno); }
    }

    private void selecionarPlano(int id) {
        for (int i = 0; i < cbPlano.getItemCount(); i++) {
            if (cbPlano.getItemAt(i).getId() == id) { cbPlano.setSelectedIndex(i); return; }
        }
        Plano plano = new PlanoDAO().buscar(id);
        if (plano != null) { cbPlano.addItem(plano); cbPlano.setSelectedItem(plano); }
    }

    private void limparCampos() {
        txtId.setText("");
        if (cbAluno.getItemCount() > 0) cbAluno.setSelectedIndex(0);
        if (cbPlano.getItemCount() > 0) cbPlano.setSelectedIndex(0);
        txtDataInicio.setText(DateUtils.format(LocalDate.now()));
        cbSituacao.setSelectedIndex(0);
        tabelaMatriculas.clearSelection();
        atualizarDadosDoPlano();
    }

    private void mostrarErroOperacao(String operacao) {
        JOptionPane.showMessageDialog(this, "Não foi possível " + operacao + " a matrícula.", "Erro", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new FrmCadastroMatricula().setVisible(true));
    }
}
