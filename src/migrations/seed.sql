INSERT IGNORE INTO sellers (id, cnpj, name)
VALUES
    (1, '12345678000199', 'Marcos Andrade'),

INSERT IGNORE INTO customers_legal (id, name, email, cnpj, responsible_name, status)
VALUES
    (1, 'TechNova Soluções LTDA', 'contato@technova.com.br', '12345678000199', 'Marcos Andrade', 1),
    (2, 'Alfa Comércio e Serviços ME', 'vendas@alfacomercio.com', '98765432000155', 'Luciana Pereira', 1),
    (3, 'Beta Engenharia LTDA', 'contato@betaengenharia.com', '45678912000188', 'Rafael Gomes', 0),
    (4, 'Gamma Consultoria Empresarial', 'suporte@gammaconsultoria.com', '32165487000122', 'Juliana Costa', 2),
    (5, 'Delta Market Digital SA', 'admin@deltamarket.com', '85296374000144', 'Rodrigo Lima', 1);

INSERT IGNORE INTO customers_physical (id, name, email, cpf, birth_date, gender, status)
VALUES
    (1, 'Ana Beatriz Souza', 'ana.souza@email.com', '12345678901', '1990-04-15', 2, 1),
    (2, 'Carlos Henrique Lima', 'carlos.lima@email.com', '98765432100', '1985-09-22', 1, 1),
    (3, 'Fernanda Oliveira', 'fernanda.oliveira@email.com', '45612378999', '1993-07-08', 2, 0),
    (4, 'João Pedro Santos', 'joaopedro.santos@email.com', '74185296377', '1998-12-03', 1, 1),
    (5, 'Patrícia Mendes', 'patricia.mendes@email.com', '36925814766', '1989-01-27', 2, 2);

INSERT IGNORE INTO items (id, name, price, promotional_price, quantity, status)
VALUES
    (1, 'Caneta Esferográfica Azul Bic', 2.50, 1.99, 500.000, 1),
    (2, 'Caderno Universitário 200 Folhas', 18.90, 15.90, 150.000, 1),
    (3, 'Lápis Preto Nº2 Faber-Castell', 1.20, 0.99, 1000.000, 1),
    (4, 'Borracha Branca Staedtler', 2.00, 1.50, 300.000, 1),
    (5, 'Apontador com Depósito', 3.50, 3.00, 250.000, 1),
    (6, 'Marcador de Texto Amarelo Stabilo', 5.90, 4.99, 200.000, 1),
    (7, 'Grampeador Pequeno', 24.90, 21.90, 80.000, 1),
    (8, 'Caixa de Grampos Nº10', 6.50, 5.90, 120.000, 1),
    (9, 'Bloco de Notas Autoadesivas 76x76mm', 8.90, 7.50, 140.000, 1),
    (10, 'Pasta Plástica com Elástico A4', 4.50, 3.99, 180.000, 1),
    (11, 'Envelope Kraft A4', 1.00, 0.85, 600.000, 1),
    (12, 'Régua 30cm Acrílica', 3.90, 3.50, 220.000, 1),
    (13, 'Calculadora de Mesa 12 Dígitos', 49.90, 44.90, 35.000, 1),
    (14, 'Perf Furador de Papel 2 Furos', 22.50, 19.90, 60.000, 0),
    (15, 'Toner Compatível HP 12A', 159.90, 139.90, 20.000, 1);
