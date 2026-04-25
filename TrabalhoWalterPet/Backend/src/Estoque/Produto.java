package Estoque;

public class Produto {


    //atributes

    private String nome;
    private int quantd;
    private float precoUni;
    private String descricao;


    //constructor

    public Produto(String nome, float precoUni) {
        this(nome, 1, precoUni, "");
    }

    public Produto(String nome, int quantd, float precoUni) {
        this(nome, quantd, precoUni, "");
    }

    public Produto(String nome, int quantd, float precoUni, String descricao) {
        this.nome = nome.toLowerCase();
        this.quantd = (quantd <= 0) ? 1 : quantd;
        this.precoUni = precoUni;
        this.descricao = (descricao == null) ? "" : descricao.toLowerCase();
    }


    //getters

    public String getNome() {
        return nome.substring(0, 1).toUpperCase() + nome.substring(1).toLowerCase();
    }

    public float getPrecoUni() {
        return precoUni;
    }

    public int getQuantd() {
        return quantd;
    }

    public String getDescricao() {
        return descricao.substring(0, 1).toUpperCase() + descricao.substring(1).toLowerCase();
    }


    //setters

    public void setNome(String nome) {
        this.nome = nome.toLowerCase();
    }

    public void setPrecoUni(float precoUni) {
        this.precoUni = precoUni;
    }

    public void setQuantd(int quantd) {
        this.quantd = quantd;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao.toLowerCase();
    }


    //methods

    public boolean temSuficiente(int quantd){
        return (this.quantd - quantd) >= 0;
    }
}
