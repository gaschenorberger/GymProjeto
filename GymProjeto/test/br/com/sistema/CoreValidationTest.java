package br.com.sistema;

import br.com.sistema.dao.AlunoDAO;
import br.com.sistema.dao.MatriculaDAO;
import br.com.sistema.dao.PlanoDAO;
import br.com.sistema.dao.UsuarioDAO;
import br.com.sistema.model.Aluno;
import br.com.sistema.model.Matricula;
import br.com.sistema.model.Plano;
import br.com.sistema.util.CpfValidator;
import br.com.sistema.util.DateUtils;
import br.com.sistema.util.PasswordUtils;
import java.time.LocalDate;

public class CoreValidationTest {

    public static void main(String[] args) {
        System.setProperty("gym.db.url", "jdbc:mysql://127.0.0.1:1/indisponivel");
        testarValidadores();
        testarCrudMatriculaEIntegridade();
        System.out.println("Todos os testes do núcleo passaram.");
    }

    private static void testarValidadores() {
        check(CpfValidator.isValid("529.982.247-25"), "CPF válido foi recusado");
        check(!CpfValidator.isValid("111.111.111-11"), "CPF repetido foi aceito");
        check(!CpfValidator.isValid("529.982.247-26"), "CPF com dígito errado foi aceito");
        check(LocalDate.of(2026, 9, 29).equals(DateUtils.parse("29/09/2026")), "Data não foi convertida");
        check("29/09/2026".equals(DateUtils.format(LocalDate.of(2026, 9, 29))), "Data não foi formatada");
        check(PasswordUtils.matches("123456", PasswordUtils.sha256("123456")), "Senha correta foi recusada");
        check(!PasswordUtils.matches("errada", PasswordUtils.sha256("123456")), "Senha incorreta foi aceita");
        check(new UsuarioDAO().efetuarLogin("admin@gymprojeto.com", "123456"), "Login demonstrativo correto foi recusado");
        check(!new UsuarioDAO().efetuarLogin("admin@gymprojeto.com", "errada"), "Login demonstrativo incorreto foi aceito");
    }

    private static void testarCrudMatriculaEIntegridade() {
        Aluno aluno = new AlunoDAO().listarAtivos().get(0);
        Plano plano = new PlanoDAO().listarAtivos().get(0);
        Matricula matricula = new Matricula(0, aluno.getId(), plano.getId(),
                LocalDate.of(2026, 10, 5), LocalDate.of(2027, 10, 5),
                plano.getValor(), "Ativa");
        MatriculaDAO dao = new MatriculaDAO();
        Matricula invalida = new Matricula(0, 999999, plano.getId(),
                LocalDate.of(2026, 10, 5), LocalDate.of(2026, 10, 4), -1, "Ativa");
        check(!dao.salvar(invalida), "Matrícula inválida foi aceita");
        check(dao.salvar(matricula), "Matrícula não foi salva");
        check(matricula.getId() > 0, "Matrícula não recebeu ID");
        check(MatriculaDAO.existeParaAluno(aluno.getId()), "Vínculo com aluno não foi encontrado");
        check(MatriculaDAO.existeParaPlano(plano.getId()), "Vínculo com plano não foi encontrado");
        check(!new AlunoDAO().excluir(aluno.getId()), "Aluno com matrícula foi excluído");
        check(!new PlanoDAO().excluir(plano.getId()), "Plano com matrícula foi excluído");

        matricula.setSituacao("Cancelada");
        check(dao.editar(matricula), "Matrícula não foi editada");
        check("Cancelada".equals(dao.buscar(matricula.getId()).getSituacao()), "Edição não foi persistida");
        check(dao.excluir(matricula.getId()), "Matrícula não foi excluída");
        check(dao.buscar(matricula.getId()) == null, "Matrícula excluída ainda foi encontrada");
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
