package Backend.Estoque;

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
        if (descricao.isBlank()){
            return "";
        }
        return descricao.substring(0, 1).toUpperCase() + descricao.substring(1).toLowerCase();
    }


    //setters

    public void setNome(String nome) {
        this.nome = nome.toLowerCase();
    }

    public void setPrecoUni(float precoUni) {
        if (precoUni < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo");
        }
        this.precoUni = precoUni;
    }

    public void setQuantd(int quantd) {
        if (quantd < 0) {
            throw new IllegalArgumentException("Quantidade não pode ser negativa");
        }
        this.quantd = quantd;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao.toLowerCase();
    }


    //methods

    public boolean temSuficiente(int quantd){
        return (this.quantd - quantd) >= 0;
    }

    public void alerta(){
        if(this.quantd < 5){
            System.out.println("Pouca quantidade, reabasteça o estoque imediatamente");
        }
    }

    public void printarProd(){
        if(getDescricao().isBlank()){
            System.out.printf(
                    "Nome: %s - Preço unitário: R$%.2f - Quantidade: %dx",
                    getNome(), getPrecoUni(), getQuantd()
                    );
        }
        else if (!getDescricao().isBlank()) {
            System.out.printf(
                    "Nome: %s - Preço unitário: R$%.2f - Quantidade: %dx - Descrição",
                    getNome(), getPrecoUni(), getQuantd(), getDescricao()
            );
        }

    }
}
