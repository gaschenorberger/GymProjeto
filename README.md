# Sistema de Gerenciamento de Academia

Sistema desktop desenvolvido em **Java Swing** para auxiliar no gerenciamento básico de uma academia.

O projeto permite realizar o controle de **alunos**, **planos** e **matrículas**, além de contar com autenticação de usuário e navegação através de um menu principal.

---

# Sobre o Projeto

O sistema foi desenvolvido com o objetivo de aplicar conceitos de análise, projeto e desenvolvimento de software utilizando Java.

A aplicação possui interface gráfica construída com **Java Swing** e utiliza acesso a banco de dados através de **JDBC**.

O sistema será composto pelos seguintes módulos principais:

* Login;
* Menu principal;
* Cadastro de alunos;
* Cadastro de planos;
* Gerenciamento de matrículas.

---

# Funcionalidades

## Login

O sistema possui uma tela de autenticação onde o usuário deverá informar:

* Usuário;
* Senha.

Após a validação dos dados, o usuário será direcionado para o menu principal.

---

## Menu Principal

Através do menu principal será possível acessar as funcionalidades do sistema:

* Alunos;
* Planos;
* Matrículas;
* Sair.

---

## Gerenciamento de Alunos

O módulo de alunos permite realizar o controle dos alunos cadastrados na academia.

Entre as principais operações estão:

* Cadastrar aluno;
* Visualizar alunos cadastrados;
* Editar dados;
* Excluir registros;
* Limpar campos do formulário;
* Selecionar registros através da tabela.

### Dados do Aluno

* Nome;
* CPF;
* Data de nascimento;
* Telefone;
* E-mail;
* Endereço;
* Situação.

---

## Gerenciamento de Planos

O módulo de planos permite cadastrar e administrar os planos oferecidos pela academia.

### Dados do Plano

* Nome;
* Duração em meses;
* Valor;
* Descrição;
* Situação.

### Exemplos

* Mensal;
* Trimestral;
* Semestral;
* Anual.

---

## Gerenciamento de Matrículas

O módulo de matrículas será responsável por relacionar um aluno a determinado plano.

### Dados da Matrícula

* Aluno;
* Plano;
* Data de início;
* Data de vencimento;
* Valor;
* Situação.

### Situações

* Ativa;
* Vencida;
* Cancelada.

---

# Estrutura do Projeto

O projeto será organizado utilizando os seguintes pacotes:

```text
src
└── br
    └── com
        └── sistema
            ├── dao
            ├── jdbc
            ├── main
            ├── model
            └── view
```

---

# Pacotes

## `br.com.sistema.model`

Responsável pelas classes que representam as entidades do sistema.

Classes previstas:

```text
Usuario.java
Aluno.java
Plano.java
Matricula.java
```

---

## `br.com.sistema.dao`

Responsável pelas operações de acesso e manipulação dos dados no banco.

Classes previstas:

```text
UsuarioDao.java
AlunoDao.java
PlanoDao.java
MatriculaDao.java
```

---

## `br.com.sistema.view`

Responsável pelas interfaces gráficas desenvolvidas utilizando Java Swing.

Classes previstas:

```text
LoginView.java
MenuPrincipalView.java
AlunoView.java
PlanoView.java
MatriculaView.java
```

---

## `br.com.sistema.jdbc`

Responsável pela configuração e gerenciamento da conexão com o banco de dados.

Classe prevista:

```text
ConexaoBanco.java
```

---

## `br.com.sistema.main`

Responsável pela inicialização da aplicação.

Classe prevista:

```text
Main.java
```

---

# Entidades Principais

## Usuário

```text
Usuario
├── idUsuario
├── usuario
└── senha
```

---

## Aluno

```text
Aluno
├── idAluno
├── nome
├── cpf
├── dataNascimento
├── telefone
├── email
├── endereco
└── ativo
```

---

## Plano

```text
Plano
├── idPlano
├── nome
├── duracaoMeses
├── valor
├── descricao
└── ativo
```

---

## Matrícula

```text
Matricula
├── idMatricula
├── aluno
├── plano
├── dataInicio
├── dataVencimento
├── valor
└── situacao
```

---

# Relacionamento das Entidades

O sistema possuirá o seguinte relacionamento principal:

```text
Aluno 1 -------- N Matricula N -------- 1 Plano
```

Um aluno poderá possuir várias matrículas ao longo do tempo.

Um plano poderá estar associado a várias matrículas.

Cada matrícula estará vinculada a apenas um aluno e um plano.

---

# Tecnologias Utilizadas

* Java;
* Java Swing;
* JDBC;
* Banco de dados relacional;
* Git;
* GitHub.

---

# Documentação

A documentação complementar do projeto estará disponível em arquivos separados.

Exemplo:

```text
docs/
├── requisitos-funcionais-regras-negocio.md
├── diagrama-caso-uso
├── diagrama-classes
└── diagrama-atividades
```

---

# Estrutura Prevista do Repositório

```text
GymProjeto/
├── src/
│   └── br/
│       └── com/
│           └── sistema/
│               ├── dao/
│               ├── jdbc/
│               ├── main/
│               ├── model/
│               └── view/
│
├── docs/
│   ├── requisitos-funcionais-regras-negocio.md
│   ├── diagrama-caso-uso/
│   ├── diagrama-classes/
│   └── diagrama-atividades/
│
├── database/
│   └── script.sql
│
└── README.md
```

---

# Possíveis Melhorias Futuras

Após a conclusão das funcionalidades principais, poderão ser adicionadas novas funcionalidades ao sistema, como:

* Cadastro de instrutores;
* Cadastro de exercícios;
* Montagem de treinos;
* Registro de frequência;
* Controle de pagamentos;
* Histórico de matrículas;
* Relatórios.

---

# Status do Projeto

```text
Em desenvolvimento
```

---

# Integrantes

* Gabriel Alvise Schenorberger
* João Gabriel Gnoatto Arataque

---

# Disciplina

Projeto acadêmico desenvolvido para a disciplina de desenvolvimento de sistemas utilizando Java Swing.
