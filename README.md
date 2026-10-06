# Sistema de Gerenciamento de Academia

Aplicação desktop em Java Swing para gerenciar alunos, planos e matrículas de uma academia. O projeto foi desenvolvido para o trabalho avaliativo de Java Swing e implementa as telas, operações CRUD, tabelas e documentação solicitadas no enunciado.

## Funcionalidades

- Login com validação de campos e credenciais;
- menu principal com menus, submenus e atalhos visuais;
- cadastro, edição, exclusão e listagem de alunos;
- validação algorítmica e unicidade de CPF;
- cadastro, edição, exclusão e listagem de planos;
- cadastro, edição, exclusão e listagem de matrículas;
- seleção somente de alunos e planos ativos para novas matrículas;
- validação das datas, valor e situação da matrícula;
- confirmação antes de exclusões;
- proteção contra exclusão de registros que possuam vínculos;
- tabelas com seleção por clique e carregamento dos dados no formulário;
- persistência em MySQL com modo demonstrativo em memória quando o banco não estiver disponível.

## Tecnologias

- Java 8;
- Java Swing;
- JDBC;
- MySQL 8;
- Apache Ant / NetBeans.

## Como executar rapidamente

1. Abra a pasta `GymProjeto` no NetBeans.
2. Execute a classe `br.com.sistema.main.Main`.
3. Entre com:
   - e-mail: `admin@gymprojeto.com`
   - senha: `123456`

Sem o MySQL, a aplicação inicia em modo demonstrativo e mantém os dados somente enquanto estiver aberta. O motivo da indisponibilidade do banco é informado no console.

## Configuração do MySQL

1. Execute [`database/script.sql`](database/script.sql) no MySQL 8.
2. Baixe o MySQL Connector/J 8 e coloque o JAR em `GymProjeto/lib/`.
3. No NetBeans, acesse **Propriedades do Projeto > Bibliotecas > Adicionar JAR/Pasta** e adicione o Connector/J.
4. Por padrão, o sistema utiliza:
   - URL: `jdbc:mysql://localhost:3306/bdgym`
   - usuário: `root`
   - senha vazia.

Esses valores podem ser alterados sem modificar o código:

| Configuração | Propriedade Java | Variável de ambiente |
| --- | --- | --- |
| URL | `gym.db.url` | `GYM_DB_URL` |
| Usuário | `gym.db.user` | `GYM_DB_USER` |
| Senha | `gym.db.password` | `GYM_DB_PASSWORD` |

Exemplo:

```text
java -Dgym.db.user=usuario -Dgym.db.password=senha -jar GymProjeto.jar
```

As senhas são comparadas por hash SHA-256; o banco não armazena a senha em texto puro.

## Estrutura do projeto

```text
GymProjeto/
├── database/
│   └── script.sql
├── docs/
│   ├── requisitos-sistema.md
│   ├── diagrama-caso-uso/
│   ├── diagrama-classes/
│   └── diagrama-atividades/
├── GymProjeto/
│   ├── lib/
│   ├── src/br/com/sistema/
│   │   ├── dao/
│   │   ├── jdbc/
│   │   ├── main/
│   │   ├── model/
│   │   ├── util/
│   │   └── view/
│   └── test/br/com/sistema/
└── README.md
```

## Organização dos pacotes

- `br.com.sistema.model`: `Usuario`, `Aluno`, `Plano` e `Matricula`;
- `br.com.sistema.dao`: operações JDBC e modo demonstrativo das entidades;
- `br.com.sistema.view`: telas Java Swing;
- `br.com.sistema.jdbc`: configuração centralizada da conexão;
- `br.com.sistema.util`: validação de CPF, datas e senhas;
- `br.com.sistema.main`: inicialização da aplicação.

## Documentação

- [Requisitos funcionais e regras de negócio](docs/requisitos-sistema.md)
- [Diagrama de caso de uso](docs/diagrama-caso-uso/Diagrama%20de%20caso%20de%20uso.png)
- [Diagrama de classes](docs/diagrama-classes/diagrama-classes.png)
- [Atividade: Login](docs/diagrama-atividades/Realizar%20Login.png)
- [Atividade: Aluno](docs/diagrama-atividades/Cadastrar%20Aluno.png)
- [Atividade: Plano](docs/diagrama-atividades/Cadastrar%20Plano.png)
- [Atividade: Matrícula](docs/diagrama-atividades/Cadastrar%20Matricula.png)

## Integrantes

- Gabriel Alvise Schenorberger
- João Gabriel Gnoatto Arataque

## Status

Funcionalidades obrigatórias concluídas. A conexão MySQL depende apenas da instalação local do Connector/J e da execução do script fornecido.

## Testes

O arquivo `GymProjeto/test/br/com/sistema/CoreValidationTest.java` verifica CPF, datas, autenticação, CRUD de matrícula e integridade dos vínculos. `UiStructureTest.java` confere menus, JTable, botões obrigatórios e o padrão visual das três telas de cadastro. Os testes podem ser executados como classes Java com as asserções habilitadas (`-ea`).

O projeto também pode ser validado pelo NetBeans usando **Limpar e Construir**, que gera `GymProjeto/dist/GymProjeto.jar`.
