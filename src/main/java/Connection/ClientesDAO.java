package Connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Model.ClientesVIP;

public class ClientesDAO {

    // atributos
    private Connection connection;
    private List<ClientesVIP> clientes;

    // construtor
    public ClientesDAO() {
        // Obtém uma conexão ao banco de dados ao instanciar o DAO
        this.connection = ConnectionFactory.getConnection();
    }

    // Cria a tabela no banco de dados
    public void criaTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS clientes_mercado (NOME VARCHAR(255), TELEFONE VARCHAR(255), CPF VARCHAR(255) PRIMARY KEY)";
        try (Statement stmt = this.connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela criada com sucesso.");
        } catch (SQLException e) {
            // Em caso de erro ao criar a tabela, lança uma exceção
            throw new RuntimeException("Erro ao criar a tabela: " + e.getMessage(), e);
        } finally {
            // Garante que a conexão seja fechada mesmo em caso de exceção
            ConnectionFactory.closeConnection(this.connection);
        }
    }

    // Lista todos os valores cadastrados
    public List<ClientesVIP> listarTodos() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        clientes = new ArrayList<>();

        try {
            // Prepara e executa a consulta SQL para selecionar todos os registros da tabela
            stmt = connection.prepareStatement("SELECT * FROM clientes_mercado");
            rs = stmt.executeQuery();

            while (rs.next()) {
                // Para cada registro no ResultSet, cria um objeto clientes com os valores do registro
                ClientesVIP cliente = new ClientesVIP(
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        rs.getString("telefone"));
                clientes.add(cliente);
            }
        } catch (SQLException ex) {
            // Em caso de erro durante a consulta, imprime o erro
            System.out.println(ex);
        } finally {
            // Fecha a conexão, o PreparedStatement e o ResultSet
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
        return clientes;
    }

    // Cadastra um cliente no banco
    public void cadastrar(String cpf, String nome, String telefone) {
        PreparedStatement stmt = null;
        String sql = "INSERT INTO clientes_mercado (cpf, nome, telefone) VALUES (?, ?, ?)";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, cpf);
            stmt.setString(2, nome);
            stmt.setString(3, telefone);
            stmt.executeUpdate();
            System.out.println("Dados inseridos com sucesso");
            JOptionPane.showMessageDialog(null, "Cliente cadastrado com Sucesso ✅");
        } catch (SQLException e) {
            // Trata erros específicos, como violação de chave única (CPF duplicado)
            if (e.getSQLState().equals("23505")) {
                JOptionPane.showMessageDialog(null, "Erro: O CPF inserido já existe na tabela.");
            } else {
                // Em caso de outros erros, lança uma exceção
                throw new RuntimeException("Erro ao inserir dados no banco de dados.", e);
            }
        } finally {
            // Fecha a conexão e o PreparedStatement
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }

    // Atualiza dados no banco
    public void atualizar(String nome, String telefone, String cpf) {
        PreparedStatement stmt = null;
        String sql = "UPDATE clientes_mercado SET nome = ?, telefone = ? WHERE cpf = ?";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, telefone);
            stmt.setString(3, cpf);

            stmt.executeUpdate();

            System.out.println("Dados atualizados com sucesso");
            JOptionPane.showMessageDialog(null, "Cliente editado com Sucesso ✅");
        } catch (SQLException e) {
            // Em caso de erro ao atualizar dados, lança uma exceção
            throw new RuntimeException("Erro ao atualizar dados no banco de dados.", e);
        } finally {
            // Fecha a conexão e o PreparedStatement
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }

    // Apaga dados do banco
    public void apagar(String cpf) {
        PreparedStatement stmt = null;
        String sql = "DELETE FROM clientes_mercado WHERE cpf = ?";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, cpf);
            stmt.executeUpdate();

            System.out.println("Dado apagado com sucesso");
            JOptionPane.showMessageDialog(null, "Cliente apagado com Sucesso ✅");
        } catch (SQLException e) {
            // Em caso de erro ao apagar dados, lança uma exceção
            throw new RuntimeException("Erro ao apagar dados no banco de dados.", e);
        } finally {
            // Fecha a conexão e o PreparedStatement
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }
}
