package Animais;

public class Peixe extends Animal{


    //atributes

    private String especie;


    //constructor

    Peixe(String nome, double idade, String especie) {
        super(nome, idade);
        this.especie = especie.toLowerCase();
    }


    //getters

    public String getEspecie() {
        return especie.substring(0, 1).toUpperCase() + especie.substring(1).toUpperCase();
    }


    //setters

    public void setEspecie(String especie) {
        this.especie = especie.toLowerCase();
    }


    //methods

    @Override
    public void printar() {
        System.out.printf("Nome: %s - Espécie: %s - %.2f;",
                getNome(), getEspecie(), getIdade());
    }
}
