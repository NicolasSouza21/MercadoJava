package View;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class JanelaPrincipal extends JFrame {

    public JanelaPrincipal() {
        super("Mercado"); // Define o título da janela como "Mercado"
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Define a operação padrão ao fechar a janela

        JTabbedPane abas = new JTabbedPane(); // Cria um painel com abas

        // Adiciona abas com instâncias de diferentes Janelas (Vendas, Estoque, Cadastro Clientes)
        abas.add("Vendas", new JanelaVendas());
        abas.add("Estoque", new JanelaEstoque());
        abas.add("Cadastro Clientes", new JanelaClientes());

        this.add(abas); // Adiciona o painel de abas à janela principal
        setBounds(300, 250, 1400, 600); // Define a posição e o tamanho inicial da janela
        setResizable(true); // Permite redimensionamento da janela pelo usuário
    }

    public void run() {
        setVisible(true); // Torna a janela visível ao iniciar a aplicação
    }
}
