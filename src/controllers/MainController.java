package controllers;

import migrations.DatabaseMigrator;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class MainController {
    JFrame mainFrame;
    DatabaseMigrator databaseMigrator;

    public MainController(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.databaseMigrator = new DatabaseMigrator();
    }


    public void handleGenerateDatabase() {
        try {
            databaseMigrator.createDatabase();

            JOptionPane.showMessageDialog(
                mainFrame,
                "Banco de dados criado/verificado com sucesso!",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE
            );

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                mainFrame,
                "Erro ao criar banco de dados:\n" + e.getMessage(),
                "Erro de Migração",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void handleRunTablesMigration() {
        try {
            databaseMigrator.runTablesMigration();

            JOptionPane.showMessageDialog(
                mainFrame,
                "Tabelas criadas com sucesso!",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE
            );

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                mainFrame,
                "Erro ao executar script de tabelas:\n" + e.getMessage(),
                "Erro de Migração",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void handleRunSeedsMigration() {
        try {
            databaseMigrator.runSeedsMigration();

            JOptionPane.showMessageDialog(
                mainFrame,
                "Tabelas populadas com sucesso!",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE
            );
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                mainFrame,
                "Erro ao executar script de população das tabelas:\n" + e.getMessage(),
                "Erro de Migração",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
