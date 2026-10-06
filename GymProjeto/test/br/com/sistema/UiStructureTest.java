package br.com.sistema;

import br.com.sistema.view.FrmCadastroAluno;
import br.com.sistema.view.FrmCadastroMatricula;
import br.com.sistema.view.FrmCadastroPlano;
import br.com.sistema.view.FrmMenuPrincipal;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

public class UiStructureTest {

    public static void main(String[] args) throws Exception {
        System.setProperty("gym.db.url", "jdbc:mysql://127.0.0.1:1/indisponivel");
        SwingUtilities.invokeAndWait(() -> {
            FrmMenuPrincipal menu = new FrmMenuPrincipal();
            FrmCadastroAluno aluno = new FrmCadastroAluno();
            FrmCadastroPlano plano = new FrmCadastroPlano();
            FrmCadastroMatricula matricula = new FrmCadastroMatricula();
            try {
                check(menu.getJMenuBar() != null && menu.getJMenuBar().getMenuCount() == 3,
                        "Menu principal não possui os menus obrigatórios");
                check(hasButton(menu, "Cadastrar Matrícula"), "Atalho de matrícula não foi encontrado");
                validarTelaCadastro(aluno);
                validarTelaCadastro(plano);
                validarTelaCadastro(matricula);
            } finally {
                menu.dispose();
                aluno.dispose();
                plano.dispose();
                matricula.dispose();
            }
        });
        System.out.println("Estrutura e padrão visual das telas validados.");
    }

    private static void validarTelaCadastro(JFrame frame) {
        check(new Color(242, 242, 242).equals(frame.getContentPane().getBackground()),
                "Cor de fundo diferente do padrão em " + frame.getTitle());
        check(find(frame, JTable.class).size() == 1, "JTable ausente em " + frame.getTitle());
        check(hasButton(frame, "Salvar"), "Botão Salvar ausente em " + frame.getTitle());
        check(hasButton(frame, "Editar"), "Botão Editar ausente em " + frame.getTitle());
        check(hasButton(frame, "Excluir"), "Botão Excluir ausente em " + frame.getTitle());
        check(hasButton(frame, "Limpar Campos"), "Botão Limpar ausente em " + frame.getTitle());
    }

    private static boolean hasButton(Container root, String text) {
        for (JButton button : find(root, JButton.class)) {
            String value = button.getText();
            if (value != null && value.contains(text)) return true;
        }
        return false;
    }

    private static <T extends Component> List<T> find(Container root, Class<T> type) {
        List<T> result = new ArrayList<>();
        for (Component component : root.getComponents()) {
            if (type.isInstance(component)) result.add(type.cast(component));
            if (component instanceof Container) result.addAll(find((Container) component, type));
        }
        return result;
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
