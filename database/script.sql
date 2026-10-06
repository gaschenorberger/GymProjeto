-- Execute este script conectado ao banco GymProjeto.

CREATE TABLE IF NOT EXISTS tb_usuarios (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    email VARCHAR(160) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL,
    perfil VARCHAR(40) NOT NULL DEFAULT 'Administrador',
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS tb_planos (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    duracao_meses INTEGER NOT NULL CHECK (duracao_meses > 0),
    valor NUMERIC(10, 2) NOT NULL CHECK (valor > 0),
    situacao VARCHAR(10) NOT NULL CHECK (situacao IN ('Ativo', 'Inativo')),
    descricao VARCHAR(500)
);

CREATE TABLE IF NOT EXISTS tb_alunos (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
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
    status VARCHAR(10) NOT NULL CHECK (status IN ('Ativo', 'Inativo'))
);

CREATE TABLE IF NOT EXISTS tb_matriculas (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    aluno_id INTEGER NOT NULL,
    plano_id INTEGER NOT NULL,
    data_inicio DATE NOT NULL,
    data_vencimento DATE NOT NULL,
    valor NUMERIC(10, 2) NOT NULL CHECK (valor > 0),
    situacao VARCHAR(10) NOT NULL CHECK (situacao IN ('Ativa', 'Vencida', 'Cancelada')),
    CONSTRAINT fk_matricula_aluno
        FOREIGN KEY (aluno_id) REFERENCES tb_alunos(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_matricula_plano
        FOREIGN KEY (plano_id) REFERENCES tb_planos(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT ck_matricula_datas
        CHECK (data_vencimento > data_inicio)
);

INSERT INTO tb_usuarios (nome, email, senha, perfil, ativo)
SELECT
    'Administrador',
    'admin@gymprojeto.com',
    '123',
    'Administrador',
    TRUE
WHERE NOT EXISTS (
    SELECT 1 FROM tb_usuarios WHERE email = 'admin@gymprojeto.com'
);

INSERT INTO tb_planos (nome, duracao_meses, valor, situacao, descricao)
SELECT
    'Musculação', 12, 119.90, 'Ativo',
    'Acesso livre aos equipamentos de musculação e esteiras.'
WHERE NOT EXISTS (
    SELECT 1 FROM tb_planos WHERE nome = 'Musculação'
);

INSERT INTO tb_planos (nome, duracao_meses, valor, situacao, descricao)
SELECT
    'Funcional', 6, 149.90, 'Ativo',
    'Treinamento funcional em circuito com acompanhamento.'
WHERE NOT EXISTS (
    SELECT 1 FROM tb_planos WHERE nome = 'Funcional'
);

INSERT INTO tb_planos (nome, duracao_meses, valor, situacao, descricao)
SELECT
    'Crossfit', 12, 199.90, 'Ativo',
    'Treinos de alta intensidade em área dedicada.'
WHERE NOT EXISTS (
    SELECT 1 FROM tb_planos WHERE nome = 'Crossfit'
);
