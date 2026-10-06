CREATE DATABASE IF NOT EXISTS bdgym
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE bdgym;

CREATE TABLE IF NOT EXISTS tb_usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    email VARCHAR(160) NOT NULL UNIQUE,
    senha_hash CHAR(64) NOT NULL,
    perfil VARCHAR(40) NOT NULL DEFAULT 'Administrador',
    ativo TINYINT(1) NOT NULL DEFAULT 1
);

CREATE TABLE IF NOT EXISTS tb_planos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    duracao_meses INT NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    situacao ENUM('Ativo', 'Inativo') NOT NULL,
    descricao VARCHAR(500),
    CONSTRAINT ck_plano_duracao CHECK (duracao_meses > 0),
    CONSTRAINT ck_plano_valor CHECK (valor > 0)
);

CREATE TABLE IF NOT EXISTS tb_alunos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(160) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    email VARCHAR(160),
    telefone VARCHAR(30),
    data_nascimento DATE,
    endereco VARCHAR(180),
    numero VARCHAR(20),
    bairro VARCHAR(100),
    cidade VARCHAR(100),
    estado CHAR(2),
    plano VARCHAR(120),
    status ENUM('Ativo', 'Inativo') NOT NULL
);

CREATE TABLE IF NOT EXISTS tb_matriculas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    aluno_id INT NOT NULL,
    plano_id INT NOT NULL,
    data_inicio DATE NOT NULL,
    data_vencimento DATE NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    situacao ENUM('Ativa', 'Vencida', 'Cancelada') NOT NULL,
    CONSTRAINT fk_matricula_aluno FOREIGN KEY (aluno_id)
        REFERENCES tb_alunos(id) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_matricula_plano FOREIGN KEY (plano_id)
        REFERENCES tb_planos(id) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT ck_matricula_datas CHECK (data_vencimento > data_inicio),
    CONSTRAINT ck_matricula_valor CHECK (valor > 0)
);

INSERT INTO tb_usuarios (nome, email, senha_hash, perfil, ativo)
SELECT 'Administrador', 'admin@gymprojeto.com',
       '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92',
       'Administrador', 1
WHERE NOT EXISTS (
    SELECT 1 FROM tb_usuarios WHERE email = 'admin@gymprojeto.com'
);

INSERT INTO tb_planos (nome, duracao_meses, valor, situacao, descricao)
SELECT 'Musculação', 12, 119.90, 'Ativo', 'Acesso livre aos equipamentos de musculação e esteiras.'
WHERE NOT EXISTS (SELECT 1 FROM tb_planos WHERE nome = 'Musculação');

INSERT INTO tb_planos (nome, duracao_meses, valor, situacao, descricao)
SELECT 'Funcional', 6, 149.90, 'Ativo', 'Treinamento funcional em circuito com acompanhamento.'
WHERE NOT EXISTS (SELECT 1 FROM tb_planos WHERE nome = 'Funcional');

INSERT INTO tb_planos (nome, duracao_meses, valor, situacao, descricao)
SELECT 'Crossfit', 12, 199.90, 'Ativo', 'Treinos de alta intensidade em área dedicada.'
WHERE NOT EXISTS (SELECT 1 FROM tb_planos WHERE nome = 'Crossfit');
