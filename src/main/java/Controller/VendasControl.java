package Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Connection.EstoqueDAO;
import Connection.VendasDAO;
import Model.Vendas;

public class VendasControl {

    private List<Vendas> vendas; // Lista de objetos Vendas
    private DefaultTableModel tableModel; // Modelo da tabela Swing para exibição dos dados
    private JTable table; // Tabela Swing onde os dados são exibidos

    // Construtor
    public VendasControl(List<Vendas> vendas, DefaultTableModel tableModel, JTable table) {
        this.vendas = vendas; // Inicializa a lista de vendas
        this.tableModel = tableModel; // Inicializa o modelo da tabela
        this.table = table; // Inicializa a tabela Swing
    }

    // Método para atualizar a tabela de exibição com dados do banco de dados
    private void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa todas as linhas existentes na tabela
        vendas = new VendasDAO().listarTodos(); // Obtém as vendas atualizadas do banco de dados
        for (Vendas venda : vendas) {
            // Adiciona os dados de cada venda como uma nova linha na tabela Swing
            tableModel.addRow(
                    new Object[] { venda.getData(), venda.getCpf(), venda.getQuantidade(), venda.getCodBarras() });
        }
    }

    // Método para cadastrar uma nova venda no banco de dados
    public void cadastrar(String dataInput, String clienteInput, String quantidadeInput, String codBarrasInput) {
        try {
            // Obter valores dos campos
            String data = dataInput;
            String cliente = clienteInput;
            String quantidade = quantidadeInput;
            String codBarras = codBarrasInput;

            // Validar campos
            if (data.isEmpty() || quantidade.isEmpty() || cliente.equals("Selecione um cliente")
                    || codBarras.equals("Selecione um produto")) {
                JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.");
                return; // Retorna para evitar a execução do restante do código
            }

            // Validar se quantidade contém apenas números
            if (!quantidade.matches("[0-9]+")) {
                JOptionPane.showMessageDialog(null, "O campo 'quantidade' deve conter apenas números.");
                return; // Retorna para evitar a execução do restante do código
            }

            // Validar formato da data
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false);
            Date parsedDate = dateFormat.parse(data);
            if (!data.equals(dateFormat.format(parsedDate))) {
                throw new ParseException("Formato inválido", 0);
            }

            // Obter o código do cliente
            String clienteId = cliente.split(" ")[0];

            // Apagar produto do estoque
            new EstoqueDAO().apagar(codBarras);

            // Cadastrar venda no banco de dados
            new VendasDAO().cadastrar(data, clienteId, quantidade, codBarras);
            // Chama o método de cadastro no banco de dados
            atualizarTabela(); // Atualiza a tabela de exibição após o cadastro
            JOptionPane.showMessageDialog(null, "Venda cadastrada com sucesso!");
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, "Formato de data inválido. Utilize o formato dd/MM/yyyy.");
        } catch (Exception e) {
            // Adicione um tratamento adequado para a exceção (pode ser SQLException)
            e.printStackTrace();
        }
    }

    // Método para atualizar os dados de uma venda no banco de dados
    public void atualizar(String data, String cliente, String quantidade, String codBarras) {
        try {
            validarSelecaoParaEdicao(codBarras);
            new VendasDAO().atualizar(data, cliente, quantidade, codBarras);
            // Chama o método de atualização no banco de dados
            atualizarTabela(); // Atualiza a tabela de exibição após a atualização
            JOptionPane.showMessageDialog(null, "Produto atualizado com sucesso!", "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de Validação", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            // Adicione um tratamento adequado para a exceção (pode ser SQLException)
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao atualizar o produto. Por favor, tente novamente.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void validarSelecaoParaEdicao(String codBarras) {
        if (codBarras.isEmpty()) {
            throw new IllegalArgumentException("Selecione um produto para editar.");
        }
    }

    // Método para apagar uma venda do banco de dados
    public void apagar(String codBarras) {
        try {
            new VendasDAO().apagar(codBarras);
            // Chama o método de exclusão no banco de dados
            atualizarTabela(); // Atualiza a tabela de exibição após a exclusão
        } catch (Exception e) {
            // Adicione um tratamento adequado para a exceção (pode ser SQLException)
            e.printStackTrace();
        }
    }

}
