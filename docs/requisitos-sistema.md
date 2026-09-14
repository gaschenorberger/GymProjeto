# Requisitos Funcionais e Regras de Negócio

## Sistema de Gerenciamento de Academia

---

# 1. Requisitos Funcionais

Os requisitos funcionais descrevem as funcionalidades que o sistema deverá disponibilizar ao usuário.

---

## RF01 - Realizar Login

O sistema deverá permitir que o usuário informe seu nome de usuário e senha para acessar o sistema.

---

## RF02 - Validar Login

O sistema deverá validar o usuário e a senha informados.

Caso os dados estejam incorretos, o sistema deverá:

* Impedir o acesso ao sistema;
* Exibir uma mensagem informando que o usuário ou senha são inválidos.

Caso os dados estejam corretos, o sistema deverá direcionar o usuário para o menu principal.

---

## RF03 - Acessar Menu Principal

Após realizar o login com sucesso, o sistema deverá disponibilizar um menu principal contendo acesso às funcionalidades:

* Alunos;
* Planos;
* Matrículas;
* Sair.

---

## RF04 - Cadastrar Aluno

O sistema deverá permitir o cadastro de novos alunos.

O cadastro deverá possuir os seguintes dados:

* Nome;
* CPF;
* Data de nascimento;
* Telefone;
* E-mail;
* Endereço;
* Situação.

---

## RF05 - Listar Alunos

O sistema deverá apresentar os alunos cadastrados através de uma `JTable`.

A tabela deverá apresentar informações suficientes para identificação do aluno.

---

## RF06 - Selecionar Aluno

O sistema deverá permitir selecionar um aluno através da tabela.

Ao selecionar um registro, os dados do aluno deverão ser carregados nos campos do formulário.

---

## RF07 - Editar Aluno

O sistema deverá permitir alterar os dados de um aluno previamente cadastrado.

---

## RF08 - Excluir Aluno

O sistema deverá permitir excluir um aluno cadastrado.

Antes da exclusão, deverá ser solicitada uma confirmação ao usuário.

---

## RF09 - Limpar Campos do Cadastro de Aluno

O sistema deverá permitir limpar todos os campos do formulário de cadastro de alunos.

---

## RF10 - Cadastrar Plano

O sistema deverá permitir cadastrar novos planos da academia.

O plano deverá possuir:

* Nome;
* Duração em meses;
* Valor;
* Descrição;
* Situação.

---

## RF11 - Listar Planos

O sistema deverá apresentar os planos cadastrados através de uma `JTable`.

---

## RF12 - Selecionar Plano

O sistema deverá permitir selecionar um plano através da tabela.

Ao selecionar um registro, os dados do plano deverão ser carregados nos campos do formulário.

---

## RF13 - Editar Plano

O sistema deverá permitir alterar os dados de um plano previamente cadastrado.

---

## RF14 - Excluir Plano

O sistema deverá permitir excluir um plano cadastrado.

Antes da exclusão, deverá ser solicitada uma confirmação ao usuário.

---

## RF15 - Limpar Campos do Cadastro de Plano

O sistema deverá permitir limpar todos os campos do formulário de cadastro de planos.

---

## RF16 - Cadastrar Matrícula

O sistema deverá permitir cadastrar uma matrícula para um aluno.

A matrícula deverá possuir:

* Aluno;
* Plano;
* Data de início;
* Data de vencimento;
* Valor;
* Situação.

---

## RF17 - Selecionar Aluno na Matrícula

O sistema deverá permitir selecionar um aluno previamente cadastrado para realizar uma matrícula.

---

## RF18 - Selecionar Plano na Matrícula

O sistema deverá permitir selecionar um plano previamente cadastrado para realizar uma matrícula.

---

## RF19 - Listar Matrículas

O sistema deverá apresentar as matrículas cadastradas através de uma `JTable`.

---

## RF20 - Selecionar Matrícula

O sistema deverá permitir selecionar uma matrícula através da tabela.

Ao selecionar um registro, os dados da matrícula deverão ser carregados nos campos do formulário.

---

## RF21 - Editar Matrícula

O sistema deverá permitir alterar os dados de uma matrícula previamente cadastrada.

---

## RF22 - Excluir Matrícula

O sistema deverá permitir excluir uma matrícula cadastrada.

Antes da exclusão, deverá ser solicitada uma confirmação ao usuário.

---

## RF23 - Limpar Campos da Matrícula

O sistema deverá permitir limpar todos os campos do formulário de matrícula.

---

## RF24 - Exibir Mensagens

O sistema deverá apresentar mensagens ao usuário para informar situações como:

* Cadastro realizado com sucesso;
* Alteração realizada com sucesso;
* Exclusão realizada com sucesso;
* Campos obrigatórios não preenchidos;
* Dados inválidos;
* Erro durante uma operação.

---

## RF25 - Sair do Sistema

O sistema deverá permitir que o usuário encerre a aplicação através da opção "Sair" disponível no menu principal.

---

# 2. Regras de Negócio

As regras de negócio representam condições e restrições que deverão ser respeitadas durante a utilização do sistema.

---

## RN01 - CPF Único

Não deverá ser permitido cadastrar mais de um aluno com o mesmo CPF.

---

## RN02 - Nome do Aluno Obrigatório

O nome do aluno deverá ser obrigatoriamente preenchido.

---

## RN03 - CPF do Aluno Obrigatório

O CPF do aluno deverá ser obrigatoriamente preenchido.

---

## RN04 - Situação do Aluno

O aluno deverá possuir uma situação definida.

As situações disponíveis serão:

* Ativo;
* Inativo.

---

## RN05 - Nome do Plano Obrigatório

Todo plano deverá possuir um nome.

---

## RN06 - Valor do Plano

O valor do plano deverá ser maior que zero.

Não será permitido cadastrar planos com valor igual ou inferior a zero.

---

## RN07 - Duração do Plano

A duração do plano deverá ser maior que zero.

A duração será informada em meses.

---

## RN08 - Situação do Plano

Todo plano deverá possuir uma situação.

As situações disponíveis serão:

* Ativo;
* Inativo.

---

## RN09 - Aluno Obrigatório na Matrícula

Toda matrícula deverá estar associada a um aluno previamente cadastrado.

---

## RN10 - Plano Obrigatório na Matrícula

Toda matrícula deverá estar associada a um plano previamente cadastrado.

---

## RN11 - Data de Início Obrigatória

Toda matrícula deverá possuir uma data de início.

---

## RN12 - Data de Vencimento Obrigatória

Toda matrícula deverá possuir uma data de vencimento.

---

## RN13 - Validação das Datas da Matrícula

A data de vencimento da matrícula deverá ser posterior à data de início.

---

## RN14 - Valor da Matrícula

O valor da matrícula deverá ser maior que zero.

---

## RN15 - Situação da Matrícula

Toda matrícula deverá possuir uma situação.

As situações disponíveis serão:

* Ativa;
* Vencida;
* Cancelada.

---

## RN16 - Matrícula com Plano Ativo

Somente planos com situação ativa poderão ser utilizados em novas matrículas.

---

## RN17 - Matrícula com Aluno Ativo

Somente alunos com situação ativa poderão receber novas matrículas.

---

## RN18 - Confirmação de Exclusão

Antes de excluir qualquer registro, o sistema deverá solicitar confirmação ao usuário.

---

## RN19 - Integridade dos Dados

Não deverá ser permitida a realização de uma matrícula utilizando um aluno ou plano inexistente.

---

# 3. Resumo dos Requisitos

| Código | Descrição                     |
| ------ | ----------------------------- |
| RF01   | Realizar login                |
| RF02   | Validar login                 |
| RF03   | Acessar menu principal        |
| RF04   | Cadastrar aluno               |
| RF05   | Listar alunos                 |
| RF06   | Selecionar aluno              |
| RF07   | Editar aluno                  |
| RF08   | Excluir aluno                 |
| RF09   | Limpar cadastro de aluno      |
| RF10   | Cadastrar plano               |
| RF11   | Listar planos                 |
| RF12   | Selecionar plano              |
| RF13   | Editar plano                  |
| RF14   | Excluir plano                 |
| RF15   | Limpar cadastro de plano      |
| RF16   | Cadastrar matrícula           |
| RF17   | Selecionar aluno na matrícula |
| RF18   | Selecionar plano na matrícula |
| RF19   | Listar matrículas             |
| RF20   | Selecionar matrícula          |
| RF21   | Editar matrícula              |
| RF22   | Excluir matrícula             |
| RF23   | Limpar campos da matrícula    |
| RF24   | Exibir mensagens              |
| RF25   | Sair do sistema               |

---

# 4. Resumo das Regras de Negócio

| Código | Descrição                                           |
| ------ | --------------------------------------------------- |
| RN01   | CPF do aluno deve ser único                         |
| RN02   | Nome do aluno é obrigatório                         |
| RN03   | CPF do aluno é obrigatório                          |
| RN04   | Aluno deve possuir situação                         |
| RN05   | Plano deve possuir nome                             |
| RN06   | Valor do plano deve ser maior que zero              |
| RN07   | Duração do plano deve ser maior que zero            |
| RN08   | Plano deve possuir situação                         |
| RN09   | Matrícula deve possuir aluno                        |
| RN10   | Matrícula deve possuir plano                        |
| RN11   | Data de início é obrigatória                        |
| RN12   | Data de vencimento é obrigatória                    |
| RN13   | Vencimento deve ser posterior ao início             |
| RN14   | Valor da matrícula deve ser maior que zero          |
| RN15   | Matrícula deve possuir situação                     |
| RN16   | Apenas planos ativos podem receber novas matrículas |
| RN17   | Apenas alunos ativos podem receber novas matrículas |
| RN18   | Exclusões devem solicitar confirmação               |
| RN19   | Matrícula deve utilizar aluno e plano existentes    |
