package View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Connection.ClientesDAO;
import Connection.EstoqueDAO;
import Connection.VendasDAO;
import Controller.VendasControl;
import Model.ClientesVIP;
import Model.Estoque;
import Model.Vendas;

public class JanelaVendas extends JPanel {

    private JTextField inputData;
    private JTextField inputCliente;
    private JTextField inputQuantidade;
    private JTextField inputProduto;
    private JLabel labelData;
    private JLabel labelQuantidade;
    private DefaultTableModel tableModel;
    private JTable table;
    private List<Vendas> vendas = new ArrayList<>();
    private List<Estoque> estoques;
    private List<ClientesVIP> clientes;
    private int linhaSelecionada = -1;
    private JButton cadastrarButton, apagarButton, editarButton, atualizarButton;
    JComboBox<String> produtosComboBox;
    JComboBox<String> clientesComboBox;

    public JanelaVendas() {
        setLayout(new BorderLayout());

        JPanel inputFrame = new JPanel(new FlowLayout());
        JPanel botoes = new JPanel(new FlowLayout());

        produtosComboBox = new JComboBox<>();
        clientesComboBox = new JComboBox<>();

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Cliente");
        tableModel.addColumn("Data");
        tableModel.addColumn("Produto");
        tableModel.addColumn("Quantidade");
        tableModel.addColumn("Valor Total");

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        inputCliente = new JTextField(20);
        inputData = new JTextField(10);
        inputProduto = new JTextField(20);
        inputQuantidade = new JTextField(10);

        labelData = new JLabel("Data");
        labelQuantidade = new JLabel("Quantidade de Produtos");

        cadastrarButton = new JButton("Comprar");
        cadastrarButton.setBackground(Color.white);
        apagarButton = new JButton("Apagar");
        apagarButton.setBackground(Color.white);
        editarButton = new JButton("Editar");
        editarButton.setBackground(Color.white);
        atualizarButton = new JButton("Atualizar");
        atualizarButton.setBackground(Color.white);

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

        add(scrollPane, BorderLayout.CENTER);
        add(inputFrame, BorderLayout.SOUTH);
        add(botoes, BorderLayout.NORTH);

        produtosComboBox.addItem("Selecione um Produto");
        estoques = new EstoqueDAO().listarTodos();
        for (Estoque estoque : estoques) {
            produtosComboBox.addItem(estoque.getCodBarras() + " " + estoque.getNomeProduto());
        }

        clientesComboBox.addItem("Selecione um cliente");
        clientes = new ClientesDAO().listarTodos();
        for (ClientesVIP cliente : clientes) {
            clientesComboBox.addItem(cliente.getNome() + " " + cliente.getCpf());
        }

        new VendasDAO().criaTabela();
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
                operacoes.atualizar(inputData.getText(), inputQuantidade.getText(), null, null);
                inputData.setText("");
                inputQuantidade.setText("");
            }
        });

        apagarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String produtoSelecionado = (String) produtosComboBox.getSelectedItem();
                if (inputProduto.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Selecione um registro para apagar.");
                } else {
                    int resposta = JOptionPane.showConfirmDialog(null,
                            "Tem certeza de que deseja apagar os campos?", "Confirmação",
                            JOptionPane.YES_NO_OPTION);
                    if (resposta == JOptionPane.YES_OPTION) {
                        operacoes.apagar(inputProduto.getText());
                        JOptionPane.showMessageDialog(null, "A venda deletada com Sucesso!");
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

        atualizarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarComboBoxClientes();
                atualizarComboBoxProdutos();
            }
        });
    }

    private void atualizarTabela() {
        tableModel.setRowCount(0);
        vendas = new VendasDAO().listarTodos();
        for (Vendas venda : vendas) {
            tableModel.addRow(
                    new Object[] { venda.getCpf(), venda.getData(), venda.getCodBarras(), venda.getQuantidade() });
        }
    }

    private void atualizarComboBoxClientes() {
        clientesComboBox.removeAllItems();
        clientesComboBox.addItem("Selecione um cliente");
        clientes = new ClientesDAO().listarTodos();
        for (ClientesVIP cliente : clientes) {
            clientesComboBox.addItem(cliente.getNome() + " " + cliente.getCpf());
        }
    }

    private void atualizarComboBoxProdutos() {
        produtosComboBox.removeAllItems();
        produtosComboBox.addItem("Selecione um Produto");
        estoques = new EstoqueDAO().listarTodos();
        for (Estoque estoque : estoques) {
            produtosComboBox.addItem(estoque.getCodBarras() + " " + estoque.getNomeProduto());
        }
    }
}
