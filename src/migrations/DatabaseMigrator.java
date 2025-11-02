package migrations;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseMigrator {

    private static final String DB_HOST = "localhost:3306";
    private static final String DB_NAME = "sales_order_module";

    private static final String URI = "jdbc:mysql://" + DB_HOST;

    // Credenciais
    private static final String USER = "root";
    private static final String PASSWORD = "181101";

    private static final String PATH_TABLES_MIGRATION = "src/migrations/base.sql";
    private static final String PATH_SEEDS_MIGRATION = "src/migrations/seed.sql";


    public void createDatabase() throws SQLException {
        try (Connection conn = DriverManager.getConnection(URI, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            String sqlCreateDb = "CREATE DATABASE IF NOT EXISTS " + DB_NAME;
            stmt.executeUpdate(sqlCreateDb);

            System.out.println("Banco de dados '" + DB_NAME + "' verificado/criado.");
        }
    }

    public void runTablesMigration() throws SQLException, IOException {
        this.runMigration(PATH_TABLES_MIGRATION);
    }

    public void runSeedsMigration() throws SQLException, IOException {
        this.runMigration(PATH_SEEDS_MIGRATION);
    }

    private void runMigration(String filePath) throws SQLException, IOException {
        String scriptCompleto = loadSqlScript(filePath);

        try (Connection conn = DriverManager.getConnection(URI + "/" + DB_NAME, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            conn.setAutoCommit(false);

            // Divide o script em comandos individuais pelo ";"
            String[] comandos = scriptCompleto.split(";");

            int comandosExecutados = 0;
            for (String comando : comandos) {
                // Ignora linhas vazias ou comentários (se o split gerar)
                if (comando.trim().isEmpty() || comando.trim().startsWith("--")) {
                    continue;
                }

                try {
                    // Executa o comando
                    System.out.println("Executando: " + comando.trim().substring(0, Math.min(comando.trim().length(), 60)) + "...");
                    stmt.execute(comando.trim());
                    comandosExecutados++;

                } catch (SQLException e) {
                    System.err.println("ERRO ao executar comando: " + comando.trim());
                    System.err.println("Mensagem: " + e.getMessage());
                    System.err.println("Iniciando ROLLBACK...");

                    conn.rollback(); // Desfaz TUDO
                    throw new SQLException("Falha ao executar script, transação revertida.", e);
                }
            }

            // Se o loop terminou sem erros, commita a transação
            conn.commit();
            System.out.println("Sucesso! " + comandosExecutados + " comandos executados.");

        } catch (SQLException e) {
            System.err.println("Falha na transação do script.");
            throw e;
        }
    }

    private static String loadSqlScript(String filePath) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(filePath));
        return new String(bytes, StandardCharsets.UTF_8);
    }
}