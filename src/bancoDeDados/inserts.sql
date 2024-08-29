-- inserção de dados

-- Tabela clientes
INSERT INTO banco_oficina_mecanica.clientes (nome, cpf, telefone) VALUES
('João Silva', '12345678900', 11987654321),
('Maria Oliveira', '98765432100', 11912345678),
('Carlos Pereira', '45678912300', 11987651234);
-- Tabela mecanicos
INSERT INTO banco_oficina_mecanica.mecanicos (nome, cpf, telefone) VALUES
('Pedro Almeida', '11223344556', 11987650000),
('Ana Costa', '22334455667', 11912340000),
('Marcos Souza', '33445566778', 11987651111);
-- Tabela carros
INSERT INTO banco_oficina_mecanica.carros (placa, id_proprietario) VALUES
('ABC-1234', 1),
('DEF-5678', 2),
('GHI-9012', 3);
-- Tabela servicos
INSERT INTO banco_oficina_mecanica.servicos (nome, valor) VALUES
('Troca de óleo', 150.00),
('Revisão completa', 500.00),
('Alinhamento', 120.00),
('Balanceamento', 100.00),
('Troca de Pneu', 250.00),
('Troca de Freio', 300.00),
('Troca de Bateria', 200.00),
('Inspeção de Freios', 75.00),
('Troca de Filtro de Ar', 80.00),
('Troca de Filtro de Combustível', 90.00),
('Troca de Correia Dentada', 400.00),
('Serviço de Pintura', 700.00),
('Polimento Completo', 350.00);
-- Tabela ordens de servico
INSERT INTO banco_oficina_mecanica.ordens_de_servico (id_carro, id_mecanico, valor_total) VALUES
(1, 1, NULL),
(2, 2, NULL),
(3, 3, NULL);
-- Tabela servicos adquiridos
INSERT INTO banco_oficina_mecanica.servicos_adquiridos (id_servico, id_ordem_servico) VALUES
(1, 1),
(2, 2),
(3, 3),
(1, 3),
(2, 1);