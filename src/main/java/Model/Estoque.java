package Model;

public class Estoque {

    private String nomeProduto;
    private String codBarras;
    private String quantidade;
    private String preco;

    public Estoque(String nomeProduto, String codBarras, String quantidade, String preco) {
        this.nomeProduto = nomeProduto;
        this.codBarras = codBarras;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getCodBarras() {
        return codBarras;
    }

    public void setCodBarras(String codBarras) {
        this.codBarras = codBarras;
    }

    public String getquantidade() {
        return quantidade;
    }

    public void setquantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getpreco() {
        return preco;
    }

    public void setpreco(String preco) {
        this.preco = preco;
    }

}
