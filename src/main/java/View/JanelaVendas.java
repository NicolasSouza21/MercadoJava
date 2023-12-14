package View;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Connection.EstoqueDAO;
import Connection.ClientesDAO;
import Connection.VendasDAO;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Controller.VendasControl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.awt.*;

import Model.Estoque;
import Model.Vendas;
import Model.ClientesVIP;

public class JanelaVendas extends JPanel {

    // atributos - componentes
    // campo de texto - JTextField
    private JTextField inputData;
    private JTextField inputCliente;
    private JTextField inputQuantidade;
    private JTextField inputProduto;

    // campo escrito - JLabel
    private JLabel labelData;
    private JLabel labelQuantidade;

    private DefaultTableModel tableModel; // lógica
    private JTable table; // visual
    private List<Vendas> vendas = new ArrayList<>();
    private List<Estoque> estoques;
    private List<ClientesVIP> clientes;
    private int linhaSelecionada = -1;
    private JButton cadastrarButton, apagarButton, editarButton, atualizarButton;

    JComboBox<String> produtosComboBox;
    JComboBox<String> clientesComboBox;

    public JanelaVendas() {
        JPanel frame1 = new JPanel(new BorderLayout());
        JPanel inputFrame = new JPanel();
        JPanel botoes = new JPanel();

        produtosComboBox = new JComboBox<>();
        clientesComboBox = new JComboBox<>();

        // construir a tabela
        tableModel = new DefaultTableModel();

        tableModel.addColumn("Cliente");
        tableModel.addColumn("Data");
        tableModel.addColumn("Produto");
        tableModel.addColumn("Quantidade");
        tableModel.addColumn("Valor Total");

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setViewportView(table);

        // criar os componentes
        inputCliente = new JTextField(20);
        inputData = new JTextField(10);
        inputProduto = new JTextField(20);
        inputQuantidade = new JTextField(10);

        labelData = new JLabel("Data");

        labelQuantidade = new JLabel("Quantidade de Produtos");

        // botões
        cadastrarButton = new JButton("Comprar");
        cadastrarButton.setBackground(Color.white);
        apagarButton = new JButton("Apagar");
        apagarButton.setBackground(Color.white);
        editarButton = new JButton("Editar");
        editarButton.setBackground(Color.white);
        atualizarButton = new JButton("Atualizar");
        atualizarButton.setBackground(Color.white);

        // adicionar os componentes
        inputFrame.add(clientesComboBox);
        inputFrame.add(produtosComboBox);
        inputFrame.add(labelData);
        inputFrame.add(inputData);

        inputFrame.add(labelQuantidade);
        inputFrame.add(inputQuantidade);

        botoes.add(cadastrarButton);
        botoes.add(editarButton);
        botoes.add(apagarButton);
        botoes.add(atualizarButton);

        this.add(frame1);
        frame1.add(scrollPane, BorderLayout.CENTER);
        frame1.add(inputFrame, BorderLayout.SOUTH);
        frame1.add(botoes, BorderLayout.NORTH);

        produtosComboBox.addItem("Selecione um Produto");
        estoques = new EstoqueDAO().listarTodos();
        for (Estoque estoque : estoques) {
            produtosComboBox.addItem(estoque.getCodBarras() + " " + estoque.getNomeProduto());
        }

        clientesComboBox.addItem("Selecione um cliente");
        clientes = new ClientesDAO().listarTodos();
        // criar um método para atualizar o combobox
        for (ClientesVIP cliente : clientes) {
            clientesComboBox.addItem(cliente.getNome() + " " + cliente.getCpf());
        }

        // adicionando o JComboBox ao painel

        // Criar o banco de dados
        new VendasDAO().criaTabela();

        // incluir os elementos do banco na criação do painel
        atualizarTabela();

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                linhaSelecionada = table.rowAtPoint(evt.getPoint());
                if (linhaSelecionada != -1) {
                    inputData.setText((String) table.getValueAt(linhaSelecionada, 0));
                    inputCliente.setText((String) table.getValueAt(linhaSelecionada, 1));
                    inputQuantidade.setText((String) table.getValueAt(linhaSelecionada, 2));
                    inputProduto.setText((String) table.getValueAt(linhaSelecionada, 3));
                }
            }
        });

        VendasControl operacoes = new VendasControl(vendas, tableModel, table);

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String data = inputData.getText();
                String quantidade = inputQuantidade.getText();
                String clienteSelecionado = (String) clientesComboBox.getSelectedItem();
                String produtoSelecionado = (String) produtosComboBox.getSelectedItem();
                operacoes.cadastrar(data, clienteSelecionado, quantidade, produtoSelecionado);
                inputData.setText("");
                inputQuantidade.setText("");
                clientesComboBox.setSelectedIndex(0);
                produtosComboBox.setSelectedIndex(0);
            }
        });
        editarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                operacoes.atualizar(inputData.getText(),
                        inputQuantidade.getText(), null, null);
                inputData.setText("");
                inputQuantidade.setText("");
            }
        });

        // Configura a ação do botão "apagar" para excluir um registro no banco de dados
        apagarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String produtoSelecionado = (String) produtosComboBox.getSelectedItem(); // pegar o carro selecionado no
                                                                                         // ComboBox
                if (inputProduto.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Selecione um registro para apagar.");
                } else {
                    int resposta = JOptionPane.showConfirmDialog(null, "Tem certeza de que deseja apagar os campos?",
                            "Confirmação", JOptionPane.YES_NO_OPTION);
                    if (resposta == JOptionPane.YES_OPTION) {
                        // Chama o método "apagar" do objeto operacoes com o valor do campo de entrada
                        // "placa"
                        operacoes.apagar(inputProduto.getText());
                        JOptionPane.showMessageDialog(null, "A venda deletada com Sucesso!");

                        // Limpa os campos de entrada após a operação de exclusão
                        inputData.setText("");
                        inputQuantidade.setText("");
                        clientesComboBox.setSelectedIndex(0);
                        produtosComboBox.setSelectedIndex(0);
                    } else {
                        JOptionPane.showMessageDialog(null, "A venda foi Cancelada!");
                    }
                }
            }
        });

        // atualizar as comboBox com os valores atuais
        atualizarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarComboBoxClientes();
                atualizarComboBoxProdutos();
            }
        });

    }

    // atualizar Tabela de Carros com o Banco de Dados
    private void atualizarTabela() {
        // atualizar tabela pelo banco de dados
        tableModel.setRowCount(0);
        vendas = new VendasDAO().listarTodos();
        for (Vendas venda : vendas) {
            tableModel.addRow(
                    new Object[] { venda.getCpf(), venda.getData(), venda.getCodBarras(), venda.getQuantidade() });
        }
    }

    // Método para atualizar ComboBox de Clientes
    private void atualizarComboBoxClientes() {
        clientesComboBox.removeAllItems();
        clientesComboBox.addItem("Selecione um cliente");
        clientes = new ClientesDAO().listarTodos();
        for (ClientesVIP cliente : clientes) {
            clientesComboBox.addItem(cliente.getNome() + " " + cliente.getCpf());
        }
    }

    // Método para atualizar ComboBox de Produtos
    private void atualizarComboBoxProdutos() {
        produtosComboBox.removeAllItems();
        produtosComboBox.addItem("Selecione um Produto");
        estoques = new EstoqueDAO().listarTodos();
        for (Estoque estoque : estoques) {
            produtosComboBox.addItem(estoque.getCodBarras() + " " + estoque.getNomeProduto());
        }
    }

}
