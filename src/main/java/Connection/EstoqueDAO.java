package Connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Model.Estoque;

public class EstoqueDAO {
    // Atributos
    private Connection connection;
    private List<Estoque> estoques;

    // Construtor
    public EstoqueDAO() {
        // Obtém uma conexão ao banco de dados ao instanciar o DAO
        this.connection = ConnectionFactory.getConnection();
    }

    // Cria a Tabela no banco de dados
    public void criaTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS estoque_mercado (nomeProduto VARCHAR(255), codBarras VARCHAR(255) PRIMARY KEY, quantidade VARCHAR(255), preco VARCHAR(255))";
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
    public List<Estoque> listarTodos() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        estoques = new ArrayList<>();

        try {
            // Prepara e executa a consulta SQL para selecionar todos os registros da tabela
            stmt = connection.prepareStatement("SELECT * FROM estoque_mercado");
            rs = stmt.executeQuery();

            while (rs.next()) {
                // Para cada registro no ResultSet, cria um objeto Estoque com os valores do registro
                Estoque estoque = new Estoque(
                        rs.getString("nomeProduto"),
                        rs.getString("codBarras"),
                        rs.getString("quantidade"),
                        rs.getString("preco"));
                estoques.add(estoque);
            }
        } catch (SQLException ex) {
            // Em caso de erro durante a consulta, imprime o erro
            System.out.println(ex);
        } finally {
            // Fecha a conexão, o PreparedStatement e o ResultSet
            ConnectionFactory.closeConnection(connection, stmt, rs);
        }
        return estoques;
    }

    // Cadastra produto no banco
    public void cadastrar(String nomeProduto, String codBarras, String quantidade, String preco) {
        PreparedStatement stmt = null;
        String sql = "INSERT INTO estoque_mercado (nomeProduto, codBarras, quantidade, preco) VALUES (?, ?, ?, ?)";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, nomeProduto);
            stmt.setString(2, codBarras);
            stmt.setString(3, quantidade);
            stmt.setString(4, preco);
            stmt.executeUpdate();
            System.out.println("Dados inseridos com sucesso");
            JOptionPane.showMessageDialog(null, "Você Cadastrou o produto com sucesso ✅");
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                JOptionPane.showMessageDialog(null, "Erro: O código de barras inserido já existe na tabela.");
            } else {
                throw new RuntimeException("Erro ao inserir dados no banco de dados.", e);
            }
        } finally {
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }

    // Atualiza dados no banco
    public void atualizar(String nomeProduto, String codBarras, String quantidade, String preco) {
        PreparedStatement stmt = null;

        String sql = "UPDATE estoque_mercado SET nomeProduto = ?, quantidade = ?, preco = ? WHERE codBarras = ?";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, nomeProduto);
            stmt.setString(2, quantidade);
            stmt.setString(3, preco);
            stmt.setString(4, codBarras);

            stmt.executeUpdate();

            System.out.println("Dados atualizados com sucesso");
            JOptionPane.showMessageDialog(null, "Produto atualizado com sucesso ✅");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar dados no banco de dados.", e);
        } finally {
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }

    // Apaga dados do banco
    public void apagar(String codBarras) {
        PreparedStatement stmt = null;
        String sql = "DELETE FROM estoque_mercado WHERE codBarras = ?";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, codBarras);
            stmt.executeUpdate();
            System.out.println("Dado apagado com sucesso");
            JOptionPane.showMessageDialog(null, "Você Apagou o produto com sucesso ✅");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao apagar dados no banco de dados.", e);
        } finally {
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }
}
