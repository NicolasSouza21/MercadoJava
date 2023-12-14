package View;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Connection.ClientesDAO;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.BorderLayout;

import Controller.ClientesControl;
import Model.ClientesVIP;

public class JanelaClientes extends JPanel {
    // atributos - componentes
    // campo de texto - JTextField
    private JTextField inputCpf;
    private JTextField inputNome;
    private JTextField inputTelefone;
    // campo escrito - JLabel
    private JLabel labelCpf;
    private JLabel labelNome;
    private JLabel labelTelefone;

    private DefaultTableModel tableModel; // lógica
    private JTable table; // visual
    private List<ClientesVIP> clientes = new ArrayList<>();
    private int linhaSelecionada = -1;
    private JButton cadastrarButton, apagarButton, editarButton;

    public JanelaClientes() {
        JPanel frame1 = new JPanel();
        JPanel inputFrame = new JPanel();
        JPanel botoes = new JPanel();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        frame1.setLayout(new BorderLayout());

        // construir a tabela
        tableModel = new DefaultTableModel();
        tableModel.addColumn("CPF");
        tableModel.addColumn("Nome");
        tableModel.addColumn("Telefone");

        table = new JTable(tableModel);
        table.setBackground(Color.white);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setViewportView(table);

        // criar os componentes
        inputCpf = new JTextField(20);

        inputNome = new JTextField(20);

        inputTelefone = new JTextField(20);

        // criar os componentes - labels
        labelCpf = new JLabel("CPF");

        labelNome = new JLabel("Nome");

        labelTelefone = new JLabel("Telefone");

        // botões
        cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.setBackground(Color.white);

        apagarButton = new JButton("Apagar");
        apagarButton.setBackground(Color.white);

        editarButton = new JButton("Editar");
        editarButton.setBackground(Color.white);

        // adicionar os componentes

        inputFrame.add(labelNome);
        inputFrame.add(inputNome);
        inputFrame.add(labelCpf);
        inputFrame.add(inputCpf);
        inputFrame.add(labelTelefone);
        inputFrame.add(inputTelefone);

        botoes.add(cadastrarButton);
        botoes.add(editarButton);
        botoes.add(apagarButton);

        this.add(frame1);
        frame1.add(scrollPane, BorderLayout.NORTH);
        frame1.add(inputFrame, BorderLayout.CENTER);
        frame1.add(botoes, BorderLayout.SOUTH);

        // Criar o banco de dados
        new ClientesDAO().criaTabela();

        // incluir os elementos do banco na criação do painel
        atualizarTabela();

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                linhaSelecionada = table.rowAtPoint(evt.getPoint());
                if (linhaSelecionada != -1) {
                    inputCpf.setText((String) table.getValueAt(linhaSelecionada, 0));
                    inputNome.setText((String) table.getValueAt(linhaSelecionada, 1));
                    inputTelefone.setText((String) table.getValueAt(linhaSelecionada, 2));
                }
            }
        });

        ClientesControl operacoes = new ClientesControl(clientes, tableModel, table);

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    operacoes.cadastrar(inputCpf.getText(), inputNome.getText(), inputTelefone.getText());
                    // Limpa os campos de entrada após a operação de cadastro
                    inputCpf.setText("");
                    inputNome.setText("");
                    inputTelefone.setText("");
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar: " + ex.getMessage());
                }
            }
        });

        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    operacoes.atualizar(inputCpf.getText(), inputNome.getText(), inputTelefone.getText());
                    // Limpa os campos de entrada após a operação de cadastro
                    inputCpf.setText("");
                    inputNome.setText("");
                    inputTelefone.setText("");
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao editar: " + ex.getMessage());
                }
            }
        });

        apagarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    operacoes.apagar(inputCpf.getText());
                    inputCpf.setText("");
                } catch (RuntimeException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao apagar: " + ex.getMessage());
                }
            }
        });
    }

    // atualizar Tabela de Clientes com o Banco de Dados
    private void atualizarTabela() {
        // atualizar tabela pelo banco de dados
        tableModel.setRowCount(0);
        try {
            clientes = new ClientesDAO().listarTodos();
            for (ClientesVIP cliente : clientes) {
                tableModel.addRow(new Object[] { cliente.getCpf(), cliente.getNome(), cliente.getTelefone() });
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar dados: " + ex.getMessage());
        }
    }
}
