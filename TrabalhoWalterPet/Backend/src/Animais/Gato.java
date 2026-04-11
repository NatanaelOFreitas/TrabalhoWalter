package Animais;

public class Gato extends Animal{


    //atributes

    private String raca;
    private boolean vacinado;


    //constructor

    Gato(String nome, double idade, String raca) {
        super(nome, idade);
        this.raca = raca;
        this.vacinado = false;
    }


    //getters

    public String getRaca() {
        return raca;
    }

    public boolean taVacinado() {
        return vacinado;
    }


    //setters

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public void foiVacinado(boolean vacinado) {
        this.vacinado = vacinado;
    }


    //methods

    @Override
    public void printar() {
        System.out.printf("Nome: %s - Raça: %s - %.2f - Vacinado: %s;",
                getNome(), getRaca(), getIdade(), portugues(vacinado));
    }
}
