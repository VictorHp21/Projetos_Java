-- =====================================================================
--  Criação do banco gerador_bi (Prova Prática de BI)
--  Rode este script inteiro ANTES de executar o gerador Java (Main).
--  Ele apaga e recria as tabelas, então pode ser rodado quantas vezes
--  for preciso para gerar a base do zero.
-- =====================================================================

CREATE DATABASE IF NOT EXISTS gerador_bi
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE gerador_bi;

-- Apaga na ordem inversa das dependências (filhas antes das mães)
DROP TABLE IF EXISTS itens_venda;
DROP TABLE IF EXISTS vendas;
DROP TABLE IF EXISTS produtos;
DROP TABLE IF EXISTS vendedores;
DROP TABLE IF EXISTS clientes;
DROP TABLE IF EXISTS categorias;

-- ---------------------------------------------------------------------
-- Dimensões
-- ---------------------------------------------------------------------
CREATE TABLE categorias (
    id_categoria  INT           NOT NULL,
    nome          VARCHAR(80)   NOT NULL,
    descricao     VARCHAR(255),
    PRIMARY KEY (id_categoria)
);

CREATE TABLE clientes (
    id_cliente    INT           NOT NULL,
    nome          VARCHAR(100)  NOT NULL,
    email         VARCHAR(120)  NOT NULL,
    cidade        VARCHAR(80)   NOT NULL,
    estado        CHAR(2)       NOT NULL,
    data_cadastro DATE          NOT NULL,
    PRIMARY KEY (id_cliente),
    UNIQUE KEY uk_clientes_email (email)
);

CREATE TABLE vendedores (
    id_vendedor   INT           NOT NULL,
    nome          VARCHAR(100)  NOT NULL,
    setor         VARCHAR(50)   NOT NULL,
    data_admissao DATE          NOT NULL,
    PRIMARY KEY (id_vendedor)
);

CREATE TABLE produtos (
    id_produto    INT           NOT NULL,
    id_categoria  INT           NOT NULL,
    nome          VARCHAR(100)  NOT NULL,
    preco         DECIMAL(10,2) NOT NULL,
    custo         DECIMAL(10,2) NOT NULL,
    estoque       INT           NOT NULL,
    PRIMARY KEY (id_produto),
    CONSTRAINT fk_produtos_categoria
        FOREIGN KEY (id_categoria) REFERENCES categorias (id_categoria)
);

-- ---------------------------------------------------------------------
-- Fatos
-- ---------------------------------------------------------------------
CREATE TABLE vendas (
    id_venda        INT           NOT NULL,
    id_cliente      INT           NOT NULL,
    id_vendedor     INT           NOT NULL,
    data_venda      DATETIME      NOT NULL,
    valor_total     DECIMAL(10,2) NOT NULL,
    forma_pagamento VARCHAR(20)   NOT NULL,
    status          VARCHAR(30)   NOT NULL,
    PRIMARY KEY (id_venda),
    CONSTRAINT fk_vendas_cliente
        FOREIGN KEY (id_cliente) REFERENCES clientes (id_cliente),
    CONSTRAINT fk_vendas_vendedor
        FOREIGN KEY (id_vendedor) REFERENCES vendedores (id_vendedor)
);

-- Granularidade: uma linha por produto dentro de uma venda
CREATE TABLE itens_venda (
    id_item         INT           NOT NULL,
    id_venda        INT           NOT NULL,
    id_produto      INT           NOT NULL,
    quantidade      INT           NOT NULL,
    preco_unitario  DECIMAL(10,2) NOT NULL,
    subtotal        DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id_item),
    UNIQUE KEY uk_item_venda_produto (id_venda, id_produto),
    CONSTRAINT fk_itens_venda
        FOREIGN KEY (id_venda) REFERENCES vendas (id_venda),
    CONSTRAINT fk_itens_produto
        FOREIGN KEY (id_produto) REFERENCES produtos (id_produto)
);

-- Conferência: deve listar as 6 tabelas
SHOW TABLES;
