package Model;

public class ClientesVIP {

    private String cpf;
    private String nome;
    private String telefone;

    // Construtor que recebe os atributos necessários para criar um objeto ClientesVIP
    public ClientesVIP(String cpf, String nome, String telefone) {
        // Poderia adicionar verificações aqui para garantir que os valores não sejam nulos ou vazios, se necessário.
        this.cpf = cpf;
        this.nome = nome;
        this.telefone = telefone;
    }

    // Métodos getters e setters para acessar e modificar os atributos

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        // Poderia adicionar verificações aqui para garantir que o CPF seja válido, se necessário.
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        // Poderia adicionar verificações aqui para garantir que o nome não seja nulo ou vazio, se necessário.
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        // Poderia adicionar verificações aqui para garantir que o telefone seja válido, se necessário.
        this.telefone = telefone;
    }
}
