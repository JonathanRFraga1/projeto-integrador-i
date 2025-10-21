package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe base genérica para DAOs.
 * Fornece operações CRUD básicas para qualquer entidade T.
 * As subclasses precisam apenas implementar o mapeamento de ResultSet e SQLs específicos.
 */
public abstract class GenericDAO<T> {
    protected Connection connection;

    // --- CONFIGURAÇÃO DO BANCO ---
    private final String URL = "jdbc:mysql://localhost:3306/salesdb";
    private final String USER = "root";
    private final String PASSWORD = "senha";

    // --- MÉTODOS DE CONEXÃO ---
    protected void connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
    }

    protected void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    // --- MÉTODOS GENÉRICOS ABSTRACT ---
    protected abstract String getTableName();
    protected abstract T mapResultSetToEntity(ResultSet rs) throws SQLException;

    // --- CRUD BÁSICO ---
    public List<T> getAll() throws SQLException {
        List<T> list = new ArrayList<>();
        connect();

        String sql = "SELECT * FROM " + getTableName();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapResultSetToEntity(rs));
            }
        }

        disconnect();
        return list;
    }

    public T getById(int id) throws SQLException {
        connect();
        String sql = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        T entity = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                entity = mapResultSetToEntity(rs);
            }
        }

        disconnect();
        return entity;
    }

    public void deleteById(int id) throws SQLException {
        connect();
        String sql = "DELETE FROM " + getTableName() + " WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }

        disconnect();
    }

    // Os métodos insert/update ficam a cargo das subclasses
    public abstract void insert(T entity) throws SQLException;
    public abstract void update(T entity) throws SQLException;
}
