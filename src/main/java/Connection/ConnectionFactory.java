package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionFactory {
    // Atributos
    private static final String url = "jdbc:postgresql://localhost:5432/";
    private static final String usuario = "postgres"; // Nome do ADM do banco
    private static final String senha = "postgres"; // Senha do ADM do banco

    // Método para obter uma conexão com o banco de dados
    public static Connection getConnection() {
        try {
            // Obtém uma conexão usando o DriverManager com as informações fornecidas
            return DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException e) {
            // Em caso de erro, lança uma exceção de tempo de execução com a mensagem de erro original
            throw new RuntimeException("Erro ao obter conexão com o banco de dados.", e);
        }
    }

    // Método para fechar a conexão com o banco de dados
    public static void closeConnection(Connection connection) {
        try {
            // Verifica se a conexão não é nula antes de tentar fechá-la
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            // Em caso de erro ao fechar a conexão, imprime a stack trace
            ex.printStackTrace();
        }
    }

    // Método para fechar a conexão e o objeto PreparedStatement
    public static void closeConnection(Connection connection, PreparedStatement stmt) {
        // Utiliza o método closeConnection(Connection) para fechar a conexão
        closeConnection(connection);
        try {
            // Verifica se o PreparedStatement não é nulo antes de tentar fechá-lo
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException ex) {
            // Em caso de erro ao fechar o PreparedStatement, imprime a stack trace
            ex.printStackTrace();
        }
    }

    // Método para fechar a conexão, o objeto PreparedStatement e o ResultSet
    public static void closeConnection(Connection connection, PreparedStatement stmt, ResultSet rs) {
        // Utiliza o método closeConnection(Connection, PreparedStatement) para fechar a conexão e o PreparedStatement
        closeConnection(connection, stmt);
        try {
            // Verifica se o ResultSet não é nulo antes de tentar fechá-lo
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            // Em caso de erro ao fechar o ResultSet, imprime a stack trace
            ex.printStackTrace();
        }
    }
}
