package Controller;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Connection.EstoqueDAO;
import Model.Estoque;

public class EstoqueControl {
    // Atributos
    private List<Estoque> estoques;
    private DefaultTableModel tableModel;
    private JTable table;

    // Construtor
    public EstoqueControl(List<Estoque> estoques, DefaultTableModel tableModel, JTable table) {
        this.estoques = estoques;
        this.tableModel = tableModel;
        this.table = table;
    }

    // Atualiza a tabela Swing com os dados do banco de dados
    public void atualizarTabela() {
        try {
            // Limpa todas as linhas existentes na tabela
            tableModel.setRowCount(0);

            // Obtém os produtos atualizados do banco de dados
            estoques = new EstoqueDAO().listarTodos();

            // Adiciona os dados de cada produto como uma nova linha na tabela Swing
            for (Estoque estoque : estoques) {
                tableModel.addRow(new Object[] { estoque.getNomeProduto(), estoque.getCodBarras(), estoque.getquantidade(),
                        estoque.getpreco() });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar a tabela: " + e.getMessage());
        }
    }

    // Cadastra um novo produto no banco de dados
    public void cadastrar(String nomeProduto, String codBarras, String quantidade, String preco) {
        try {
            // Verifica se algum campo está em branco
            if (nomeProduto.isEmpty() || codBarras.isEmpty() || quantidade.isEmpty() || preco.isEmpty()) {
                JOptionPane.showMessageDialog(null, "ATENÇÃO! \nExistem campos em branco");
                return;
            }

            // Chama o método de cadastro no banco de dados
            new EstoqueDAO().cadastrar(nomeProduto, codBarras, quantidade, preco);
            atualizarTabela(); // Atualiza a tabela de exibição após o cadastro
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    // Atualiza os dados de um produto no banco de dados
    public void atualizar(String nomeProduto, String codBarras, String quantidade, String preco) {
        try {
            // Verifica se algum campo está em branco
            if (nomeProduto.isEmpty() || codBarras.isEmpty() || quantidade.isEmpty() || preco.isEmpty()) {
                JOptionPane.showMessageDialog(null, "ATENÇÃO! \nExistem campos em branco");
            }else if (!codBarras.isEmpty()) {
                JOptionPane.showMessageDialog(null,"ATENÇÃO! \nEsse PRODUTO já se encontra Cadastrado");
            }  else {
                // Ajuste na ordem dos parâmetros
                new EstoqueDAO().atualizar(nomeProduto, codBarras, quantidade, preco);
                atualizarTabela(); // Atualiza a tabela de exibição após a atualização
                // Limpa os campos de entrada após a operação de atualização
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar produto: " + e.getMessage());
        }
    }

    // Apaga um produto do banco de dados
    public void apagar(String codBarras) {
        try {
            // Verifica se o código de barras é válido
            if (codBarras == null || codBarras.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Selecione um produto para apagar.");
                return;
            }

            // Confirmação do usuário para apagar o produto
            int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza de que deseja apagar os campos?", "Confirmação",
                    JOptionPane.YES_NO_OPTION);

            if (resposta == JOptionPane.YES_OPTION) {
                // Chama o método de exclusão no banco de dados
                new EstoqueDAO().apagar(codBarras);
                JOptionPane.showMessageDialog(null, "O Produto de Código " + codBarras + " foi deletado!");
            } else {
                JOptionPane.showMessageDialog(null, "O produto não foi deletado!");
            }

            atualizarTabela(); // Atualiza a tabela de exibição após a exclusão
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao apagar produto: " + e.getMessage());
        }
    }
}
