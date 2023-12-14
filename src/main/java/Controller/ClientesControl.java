package Controller;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Connection.ClientesDAO;
import Model.ClientesVIP;

public class ClientesControl {

    // Atributos
    private List<ClientesVIP> clientes;
    private DefaultTableModel tableModel;
    private JTable table;

    // Construtor
    public ClientesControl(List<ClientesVIP> clientes, DefaultTableModel tableModel, JTable table) {
        this.clientes = clientes;
        this.tableModel = tableModel;
        this.table = table;
    }

    // Atualiza a tabela Swing com os dados do banco de dados
    private void atualizarTabela() {
        try {
            // Limpa todas as linhas existentes na tabela
            tableModel.setRowCount(0);
            
            // Obtém os clientes atualizados do banco de dados
            clientes = new ClientesDAO().listarTodos();

            // Adiciona os dados de cada cliente como uma nova linha na tabela Swing
            for (ClientesVIP cliente : clientes) {
                tableModel.addRow(new Object[] { cliente.getCpf(), cliente.getNome(), cliente.getTelefone() });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar a tabela: " + e.getMessage());
        }
    }

    // Método para cadastrar um novo cliente
    public void cadastrar(String cpf, String nome, String telefone) {
        try {
            if (cpf.isEmpty() || nome.isEmpty() || telefone.isEmpty()) {
                JOptionPane.showMessageDialog(null, "ATENÇÃO! \nExistem campos em branco");
                return;
            }
            if (!validarFormatoCPF(cpf)) {
                JOptionPane.showMessageDialog(null, "CPF inválido! O CPF deve conter apenas números e ter 11 dígitos.");
                return;
            }
            if (!telefone.matches("[0-9]+") || telefone.length() < 11) {
                JOptionPane.showMessageDialog(null, "O campo 'Telefone' deve conter apenas números.\nAdicione no seguinte formato: 19999999999.");
                return;
            }
            if (!nome.matches("[a-zA-ZÀ-ú\\s]+")) {
                JOptionPane.showMessageDialog(null, "O campo 'Nome' deve conter apenas letras.");
                return;
            }

            // Chama o método "cadastrar" do objeto ClientesDAO com os valores dos campos de entrada
            new ClientesDAO().cadastrar(cpf, nome, telefone);
            atualizarTabela();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    // Método para atualizar dados de um cliente
    public void atualizar(String cpf, String nome, String telefone) {
        try {
            if (cpf.isEmpty() || nome.isEmpty() || telefone.isEmpty()) {
                JOptionPane.showMessageDialog(null, "ATENÇÃO! \nExistem campos em branco");
            }else if (!cpf.isEmpty()) {
                JOptionPane.showMessageDialog(null,"ATENÇÃO! \nEsse CPF já se encontra Cadastrado");
            } else {
                // Chama o método "atualizar" do objeto ClientesDAO com os valores dos campos de entrada
                new ClientesDAO().atualizar(nome, telefone, cpf);
                atualizarTabela(); // Atualiza a tabela de exibição após a atualização
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar cliente: " + e.getMessage());
        }
    }
    
    // Método para apagar dados de um cliente
    public void apagar(String cpf) {
        try {
            if (cpf.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Selecione um cliente para apagar.");
            } else {
                int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza de que deseja apagar os campos?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (resposta == JOptionPane.YES_OPTION) {
                    // Chama o método "apagar" do objeto ClientesDAO com o valor do campo de entrada "cpf"
                    new ClientesDAO().apagar(cpf);
                    JOptionPane.showMessageDialog(null, "O Cliente de CPF " + cpf + " foi deletado!");
                } else {
                    JOptionPane.showMessageDialog(null, "O cliente não foi deletado!");
                }
                // Atualiza a tabela de exibição após a exclusão
                atualizarTabela();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao apagar cliente: " + e.getMessage());
        }
    }

    // Método privado para validar o formato do CPF
    private boolean validarFormatoCPF(String cpf) {
        try {
            if (cpf == null) {
                return false;
            }
            // Remove caracteres não numéricos do CPF
            cpf = cpf.replaceAll("[^0-9]", "");
        
            // Verifica se o CPF possui 11 dígitos
            return cpf.length() == 11;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao validar CPF: " + e.getMessage());
            return false;
        }
    }
}
