package br.senac.pr.modelo;

public class Cmei {
    private int id;
    private String nome;
    private String cidade;
    private String bairro;
    private String endereco;

    public Cmei() {
    }

    public Cmei(int id, String nome, String cidade, String bairro, String endereco) {
        this.id = id;
        setNome(nome);
        this.cidade = cidade;
        this.bairro = bairro;
        this.endereco = endereco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return id + " - " + nome + " - " + bairro + ", " + cidade + " - " + endereco;
    }
}
