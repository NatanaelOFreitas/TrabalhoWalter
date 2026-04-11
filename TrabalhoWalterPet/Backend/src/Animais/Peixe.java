package Animais;

public class Peixe extends Animal{


    //atributes

    private String especie;


    //constructor

    Peixe(String nome, double idade, String especie) {
        super(nome, idade);
        this.especie = especie;
    }


    //getters

    public String getEspecie() {
        return especie;
    }


    //setters

    public void setEspecie(String especie) {
        this.especie = especie;
    }


    //methods

    @Override
    public void printar() {
        System.out.printf("Nome: %s - Espécie: %s - %.2f;",
                getNome(), getEspecie(), getIdade());
    }
}
