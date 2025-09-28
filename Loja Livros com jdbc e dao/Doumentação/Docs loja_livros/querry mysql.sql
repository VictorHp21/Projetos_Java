-- Querry loja_livros

CREATE DATABASE `LOJA_LIVROS`;

USE LOJA_LIVROS;

CREATE TABLE LIVROS (
id_livro int auto_increment not null,
nome varchar(40),
autor varchar(40),
preco decimal(10,2),
categoria varchar(30),
primary key (id_livro)
);

CREATE TABLE FUNCIONARIOS (
id_funcionario int auto_increment not null,
nome varchar(40),
data_nascimento date,
salario decimal(10,2),
id_departamento int,
primary key (id_funcionario),
foreign key (id_departamento) references DEPARTAMENTO (id_departamento)
);

CREATE TABLE DEPARTAMENTO (
id_departamento int auto_increment not null,
nome varchar(40),
primary key (id_departamento)
);

CREATE TABLE CLIENTES (
    id_cliente INT AUTO_INCREMENT NOT NULL,
    nome_cliente VARCHAR(100) NOT NULL,
    PRIMARY KEY (id_cliente)
);

CREATE TABLE VENDAS (
    id_venda INT AUTO_INCREMENT NOT NULL,
    id_cliente INT NOT NULL,
    id_funcionario INT NOT NULL,
    data_venda DATE NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id_venda),
    FOREIGN KEY (id_cliente) REFERENCES CLIENTES(id_cliente),
    FOREIGN KEY (id_funcionario) REFERENCES FUNCIONARIOS(id_funcionario)
);

CREATE TABLE GERENTE (
    id_gerente INT AUTO_INCREMENT NOT NULL,
    nome_gerente VARCHAR(100) NOT NULL,
    senha_acesso VARCHAR(255) NOT NULL, 
    id_departamento INT UNIQUE,
    PRIMARY KEY (id_gerente),
    FOREIGN KEY (id_departamento) REFERENCES DEPARTAMENTO(id_departamento)
);

