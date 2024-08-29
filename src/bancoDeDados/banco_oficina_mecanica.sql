-- ---------------------------------------------------
-- Schema banco_oficina_mecanica
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS banco_oficina_mecanica ;

-- -----------------------------------------------------
-- Schema banco_oficina_mecanica
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS banco_oficina_mecanica DEFAULT CHARACTER SET utf8 ;
USE banco_oficina_mecanica ;

-- -----------------------------------------------------
-- Table banco_oficina.clientes
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS banco_oficina_mecanica.clientes (
  id INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(45) NOT NULL,
  cpf VARCHAR(45) NOT NULL,
  telefone BIGINT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX cpf_UNIQUE (cpf ASC) VISIBLE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table banco_oficina_mecanica.mecanicos
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS banco_oficina_mecanica.mecanicos (
  id INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(45) NOT NULL,
  cpf VARCHAR(45) NOT NULL,
  telefone BIGINT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX cpf_UNIQUE (cpf ASC) VISIBLE)
ENGINE = InnoDB;

-- drop table carros;
-- -----------------------------------------------------
-- Table banco_oficina_mecanica.carros
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS banco_oficina_mecanica.carros (
  id INT NOT NULL AUTO_INCREMENT,
  placa VARCHAR(45) NOT NULL,
  id_proprietario INT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX placa_UNIQUE (placa ASC) VISIBLE,
  INDEX id_proprietario_idx (id_proprietario ASC) VISIBLE,
  CONSTRAINT id_proprietario
    FOREIGN KEY (id_proprietario)
    REFERENCES banco_oficina_mecanica.clientes (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table banco_oficina_mecanica.servicos
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS banco_oficina_mecanica.servicos (
  id INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(45) NOT NULL,
  valor FLOAT NOT NULL,
  PRIMARY KEY (id))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table banco_oficina_mecanica.ordens_de_servico
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS banco_oficina_mecanica.ordens_de_servico (
  id INT NOT NULL AUTO_INCREMENT,
  id_carro INT NULL,
  id_mecanico INT NULL,
  valor_total FLOAT NULL,
  PRIMARY KEY (id),
  INDEX id_carro_idx (id_carro ASC) VISIBLE,
  INDEX id_mecanico_idx (id_mecanico ASC) VISIBLE,
  CONSTRAINT id_carro
    FOREIGN KEY (id_carro)
    REFERENCES banco_oficina_mecanica.carros (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT id_mecanico
    FOREIGN KEY (id_mecanico)
    REFERENCES banco_oficina_mecanica.mecanicos (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

ALTER TABLE banco_oficina_mecanica.ordens_de_servico
ADD COLUMN data_hora_atualizacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;


-- -----------------------------------------------------
-- Table banco_oficina_mecanica.servicos_adquiridos
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS banco_oficina_mecanica.servicos_adquiridos (
  idservicos_adquiridos INT NOT NULL AUTO_INCREMENT,
  id_servico INT NULL,
  id_ordem_servico INT NULL,
  PRIMARY KEY (idservicos_adquiridos),
  INDEX id_servico_idx (id_servico ASC) VISIBLE,
  INDEX id_ordem_servico_idx (id_ordem_servico ASC) VISIBLE,
  CONSTRAINT id_servico
    FOREIGN KEY (id_servico)
    REFERENCES banco_oficina_mecanica.servicos (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT id_ordem_servico
    FOREIGN KEY (id_ordem_servico)
    REFERENCES banco_oficina_mecanica.ordens_de_servico (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Trigger AtualizarValor
-- -----------------------------------------------------
DELIMITER $$
CREATE TRIGGER AtualizarValorTotalAposInserir AFTER INSERT
ON servicos_adquiridos
FOR EACH ROW
BEGIN
    DECLARE total FLOAT;

    -- Calcular a soma dos valores dos serviços adquiridos para a ordem de serviço
    SELECT SUM(servicos.valor) INTO total
    FROM servicos_adquiridos
    JOIN servicos ON servicos_adquiridos.id_servico = servicos.id
    WHERE servicos_adquiridos.id_ordem_servico = NEW.id_ordem_servico;

    -- Atualizar o valor_total na tabela ordens_de_servico
    UPDATE ordens_de_servico
    SET valor_total = total
    WHERE id = NEW.id_ordem_servico;
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER AtualizarValorTotalAposAtualizar AFTER UPDATE
ON servicos_adquiridos
FOR EACH ROW
BEGIN
    DECLARE total FLOAT;

    -- Calcular a soma dos valores dos serviços adquiridos para a ordem de serviço
    SELECT SUM(servicos.valor) INTO total
    FROM servicos_adquiridos
    JOIN servicos ON servicos_adquiridos.id_servico = servicos.id
    WHERE servicos_adquiridos.id_ordem_servico = NEW.id_ordem_servico;

    -- Atualizar o valor_total na tabela ordens_de_servico
    UPDATE ordens_de_servico
    SET valor_total = total
    WHERE id = NEW.id_ordem_servico;
END$$
DELIMITER ;