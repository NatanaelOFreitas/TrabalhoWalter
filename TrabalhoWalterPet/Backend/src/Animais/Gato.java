package Animais;

public class Gato extends Animal{


    //atributes

    private String raca;
    private boolean vacinado;


    //constructor

    Gato(String nome, double idade, String raca) {
        super(nome, idade);
        this.raca = raca.toLowerCase();
        this.vacinado = false;
    }


    //getters

    public String getRaca() {
        return raca.substring(0, 1).toUpperCase() + raca.substring(1).toUpperCase();
    }

    public boolean taVacinado() {
        return vacinado;
    }


    //setters

    public void setRaca(String raca) {
        this.raca = raca.toLowerCase();
    }

    public void foiVacinado(boolean vacinado) {
        this.vacinado = vacinado;
    }


    //methods

    @Override
    public void printar() {
        System.out.printf("Nome: %s - Raça: %s - %.2f - Vacinado: %s;",
                getNome(), getRaca(), getIdade(), portugues(taVacinado()));
    }
}
