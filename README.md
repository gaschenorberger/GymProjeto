# Sistema de Gerenciamento de Academia

## Sobre o Projeto

Este projeto tem como objetivo desenvolver um sistema desktop para gerenciamento de uma academia, utilizando **Java Swing** para construção da interface gráfica.

O sistema permitirá realizar o controle de alunos, planos e matrículas, além de possuir autenticação de usuário e um menu principal para acesso às funcionalidades.

O projeto será desenvolvido aplicando conceitos de análise, projeto e implementação de sistemas.

---

## Objetivo Geral

Desenvolver um sistema desktop capaz de auxiliar no gerenciamento básico de uma academia, permitindo o cadastro e controle de alunos, planos e matrículas.

---

## Objetivos Específicos

O sistema deverá permitir:

* Realizar login no sistema;
* Cadastrar alunos;
* Consultar alunos cadastrados;
* Editar alunos;
* Excluir alunos;
* Cadastrar planos;
* Consultar planos;
* Editar planos;
* Excluir planos;
* Realizar matrículas;
* Consultar matrículas;
* Editar matrículas;
* Excluir matrículas;
* Visualizar os registros através de tabelas;
* Navegar pelas funcionalidades através de um menu principal.

---

# Funcionalidades

## Login

A tela de login será responsável pela autenticação do usuário.

### Campos

* Usuário;
* Senha.

### Ações

* Entrar.

### Validações

* Os campos não poderão estar vazios;
* O usuário e a senha deverão ser validados;
* Caso o login seja inválido, o sistema deverá apresentar uma mensagem de erro;
* Caso o login seja válido, o usuário será direcionado para o menu principal.

---

## Menu Principal

A tela principal permitirá acessar as funcionalidades do sistema.

### Opções

* Alunos;
* Planos;
* Matrículas;
* Sair.

---

# Cadastro de Alunos

Tela responsável pelo gerenciamento dos alunos da academia.

## Campos

* Código;
* Nome;
* CPF;
* Data de nascimento;
* Telefone;
* E-mail;
* Endereço;
* Situação.

## Ações

* Salvar;
* Editar;
* Excluir;
* Limpar campos.

## Tabela

A tela deverá possuir uma `JTable` para exibir os alunos cadastrados.

Ao selecionar um aluno na tabela, seus dados deverão ser carregados nos campos para permitir edição ou exclusão.

---

# Cadastro de Planos

Tela responsável pelo gerenciamento dos planos oferecidos pela academia.

## Campos

* Código;
* Nome do plano;
* Duração em meses;
* Valor;
* Descrição;
* Situação.

## Exemplos de Planos

* Mensal;
* Trimestral;
* Semestral;
* Anual.

## Ações

* Salvar;
* Editar;
* Excluir;
* Limpar campos.

## Tabela

A tela deverá possuir uma `JTable` para exibir os planos cadastrados.

Ao selecionar um plano na tabela, seus dados deverão ser carregados nos campos para edição ou exclusão.

---

# Cadastro de Matrículas

Tela responsável pelo registro das matrículas dos alunos.

## Campos

* Código;
* Aluno;
* Plano;
* Data de início;
* Data de vencimento;
* Valor;
* Situação.

## Situações Possíveis

* Ativa;
* Vencida;
* Cancelada.

## Ações

* Salvar;
* Editar;
* Excluir;
* Limpar campos.

## Tabela

A tela deverá possuir uma `JTable` para exibir as matrículas cadastradas.

Ao selecionar uma matrícula na tabela, seus dados deverão ser carregados nos campos para edição ou exclusão.

---

# Requisitos Funcionais

## RF01 - Realizar Login

O sistema deverá permitir que o usuário informe usuário e senha para acessar o sistema.

## RF02 - Validar Login

O sistema deverá validar os dados informados e impedir o acesso quando o usuário ou senha estiverem incorretos.

## RF03 - Gerenciar Alunos

O sistema deverá permitir cadastrar, consultar, editar e excluir alunos.

## RF04 - Listar Alunos

O sistema deverá apresentar os alunos cadastrados em uma tabela.

## RF05 - Gerenciar Planos

O sistema deverá permitir cadastrar, consultar, editar e excluir planos.

## RF06 - Listar Planos

O sistema deverá apresentar os planos cadastrados em uma tabela.

## RF07 - Gerenciar Matrículas

O sistema deverá permitir cadastrar, consultar, editar e excluir matrículas.

## RF08 - Listar Matrículas

O sistema deverá apresentar as matrículas cadastradas em uma tabela.

## RF09 - Selecionar Registros

O sistema deverá permitir selecionar registros nas tabelas e carregar suas informações nos campos correspondentes.

## RF10 - Limpar Campos

O sistema deverá permitir limpar os campos dos formulários.

## RF11 - Navegação

O sistema deverá possuir um menu principal para acesso às funcionalidades.

## RF12 - Sair do Sistema

O sistema deverá permitir encerrar a aplicação através do menu principal.

---

# Regras de Negócio

## RN01 - CPF do Aluno

Não deverá ser permitido cadastrar dois alunos com o mesmo CPF.

## RN02 - Campos Obrigatórios do Aluno

O nome e o CPF do aluno deverão ser obrigatoriamente preenchidos.

## RN03 - Valor do Plano

O valor do plano deverá ser maior que zero.

## RN04 - Duração do Plano

A duração do plano deverá ser maior que zero.

## RN05 - Matrícula com Aluno

Toda matrícula deverá estar associada a um aluno cadastrado.

## RN06 - Matrícula com Plano

Toda matrícula deverá estar associada a um plano cadastrado.

## RN07 - Datas da Matrícula

A data de vencimento deverá ser posterior à data de início.

## RN08 - Exclusão de Registros

Antes de excluir um registro, o sistema deverá solicitar confirmação ao usuário.

---

# Entidades do Sistema

## Usuário

```text
idUsuario
usuario
senha
```

---

## Aluno

```text
idAluno
nome
cpf
dataNascimento
telefone
email
endereco
ativo
```

---

## Plano

```text
idPlano
nome
duracaoMeses
valor
descricao
ativo
```

---

## Matrícula

```text
idMatricula
idAluno
idPlano
dataInicio
dataVencimento
valor
situacao
```

---

# Relacionamentos

Um aluno poderá possuir várias matrículas ao longo do tempo.

Um plano poderá estar associado a várias matrículas.

Cada matrícula deverá estar associada a apenas um aluno e um plano.

```text
Aluno 1 -------- N Matricula N -------- 1 Plano
```

---

# Casos de Uso

O principal ator do sistema será o:

```text
Usuario
```

## Casos de Uso

* Realizar login;
* Gerenciar alunos;
* Gerenciar planos;
* Gerenciar matrículas;
* Consultar registros;
* Sair do sistema.

---

# Fluxo Principal de Matrícula

```text
Inicio
  |
  v
Realizar Login
  |
  v
Validar Login
  |
  v
Acessar Menu Principal
  |
  v
Selecionar Matriculas
  |
  v
Selecionar Aluno
  |
  v
Selecionar Plano
  |
  v
Informar Dados da Matricula
  |
  v
Validar Dados
  |
  +-------------------+
  |                   |
  v                   v
Dados Validos?       Nao
  |                   |
 Sim                  v
  |             Exibir Mensagem
  v                   |
Salvar Matricula <----+
  |
  v
Atualizar Tabela
  |
  v
Exibir Mensagem de Sucesso
  |
  v
Fim
```

---

# Estrutura do Projeto

O projeto será organizado nos seguintes pacotes:

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

# Pacote Model

Responsável pelas classes que representam as entidades do sistema.

```text
br.com.sistema.model
```

Classes:

```text
Usuario.java
Aluno.java
Plano.java
Matricula.java
```

---

# Pacote DAO

Responsável pelas operações de acesso ao banco de dados.

```text
br.com.sistema.dao
```

Classes:

```text
UsuarioDao.java
AlunoDao.java
PlanoDao.java
MatriculaDao.java
```

---

# Pacote View

Responsável pelas interfaces gráficas desenvolvidas utilizando Java Swing.

```text
br.com.sistema.view
```

Classes:

```text
LoginView.java
MenuPrincipalView.java
AlunoView.java
PlanoView.java
MatriculaView.java
```

---

# Pacote JDBC

Responsável pela conexão com o banco de dados.

```text
br.com.sistema.jdbc
```

Classe:

```text
ConexaoBanco.java
```

---

# Pacote Main

Responsável pela inicialização da aplicação.

```text
br.com.sistema.main
```

Classe:

```text
Main.java
```

---

# Estrutura Simplificada das Classes

```text
Usuario
├── idUsuario
├── usuario
└── senha

Aluno
├── idAluno
├── nome
├── cpf
├── dataNascimento
├── telefone
├── email
├── endereco
└── ativo

Plano
├── idPlano
├── nome
├── duracaoMeses
├── valor
├── descricao
└── ativo

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

# Tecnologias Utilizadas

* Java;
* Java Swing;
* JDBC;
* Banco de dados relacional;
* Git;
* GitHub.

---

# Possíveis Funcionalidades Futuras

Caso as funcionalidades obrigatórias sejam concluídas, poderão ser adicionados novos módulos.

Exemplos:

* Cadastro de instrutores;
* Cadastro de exercícios;
* Montagem de treinos;
* Controle de frequência;
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

* Joãp Gabriel Gnoatto Arataque;
* Gabriel Alvise Schenorberger.

