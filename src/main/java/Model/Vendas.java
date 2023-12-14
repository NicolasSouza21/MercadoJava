package Model;

public class Vendas {

    private String cpf;
    private String codBarras;
    private String quantidade;
    private String data;

    public Vendas(String cpf, String codBarras, String quantidade, String data) {
        this.cpf = cpf;
        this.codBarras = codBarras;
        this.quantidade = quantidade;
        this.data = data;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCodBarras() {
        return codBarras;
    }

    public void setCodBarras(String codBarras) {
        this.codBarras = codBarras;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
