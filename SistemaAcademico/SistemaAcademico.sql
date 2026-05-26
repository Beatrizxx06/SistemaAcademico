DROP DATABASE IF EXISTS sistema_academico;
CREATE DATABASE sistema_academico
DEFAULT CHARACTER SET utf8mb4
DEFAULT COLLATE utf8mb4_general_ci;

USE sistema_academico;


CREATE TABLE aluno (

    rgm VARCHAR(20) PRIMARY KEY,

    nome VARCHAR(150) NOT NULL,

    mae VARCHAR(150),

    data_nasc VARCHAR(20),

    cpf VARCHAR(20) UNIQUE,

    email VARCHAR(150),

    endereco VARCHAR(200),

    municipio VARCHAR(100),

    uf VARCHAR(5),

    bairro VARCHAR(100),

    cep VARCHAR(20),

    celular VARCHAR(25)

);


CREATE TABLE curso_aluno (

    id INT AUTO_INCREMENT PRIMARY KEY,

    rgm VARCHAR(20) NOT NULL,

    curso VARCHAR(100),

    campus VARCHAR(100),

    periodo VARCHAR(50),

    CONSTRAINT fk_curso_aluno
    FOREIGN KEY (rgm)
    REFERENCES aluno(rgm)
    ON DELETE CASCADE

);



CREATE TABLE notas_faltas (

    id INT AUTO_INCREMENT PRIMARY KEY,

    rgm VARCHAR(20) NOT NULL,

    disciplina VARCHAR(100),

    semestre VARCHAR(20),

    nota DOUBLE,

    faltas INT,

    CONSTRAINT fk_notas_aluno
    FOREIGN KEY (rgm)
    REFERENCES aluno(rgm)
    ON DELETE CASCADE

);

CREATE INDEX idx_aluno_nome
ON aluno(nome);

CREATE INDEX idx_notas_rgm
ON notas_faltas(rgm);

CREATE INDEX idx_curso_rgm
ON curso_aluno(rgm);

SHOW TABLES;

DESC aluno;

DESC curso_aluno;

DESC notas_faltas;
ALTER TABLE notas_faltas ADD COLUMN status VARCHAR(30);
