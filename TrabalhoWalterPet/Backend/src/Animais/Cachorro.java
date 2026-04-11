package Animais;
import Utils.*;

public class Cachorro extends Animal{


    //atributes

    private String raca;
    private boolean tosado;
    private boolean vacinado;


    //constructor

    Cachorro(String nome, double idade, String raca) {
        super(nome, idade);
        this.raca = raca.toLowerCase();
        this.tosado = false;
        this.vacinado = false;
    }


    //getters

    public String getRaca() {
        return raca.substring(0, 1).toUpperCase() + raca.substring(1).toUpperCase();
    }

    public boolean taTosado() {
        return tosado;
    }

    public boolean taVacinado() {
        return vacinado;
    }

    //setters

    public void setRaca(String raca) {
        this.raca = raca.toLowerCase();
    }

    public void foiTosado(boolean tosado) {
        this.tosado = tosado;
    }

    public void foiVacinado(boolean vacinado) {
        this.vacinado = vacinado;
    }


    //methods

    @Override
    public void printar() {
        System.out.printf("Nome: %s - Raça: %s - %.2f - Tosado: %s - Vacinado: %s;",
                getNome(), getRaca(), getIdade(), portugues(taTosado()), portugues(taVacinado()));
    }
}
