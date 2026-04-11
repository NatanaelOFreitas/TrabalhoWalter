package Animais;

public class Animal {

    //atributes

    private String nome;
    private double idade;


    //constructor

    Animal(String nome, double idade){
        this.nome = nome;
        this.idade = idade;
    }


    //getters

    public String getNome(){
        return nome;
    }

    public double getIdade(){
        return idade;
    }


    //setters

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdade(double idade) {
        this.idade = idade;
    }


    //methods

    public void printar(){
        System.out.printf("%s - %.2f;", nome, idade);
    }

    public String portugues(boolean condicao){
        if(condicao){
            return "sim";
        }
        else{
            return "não";
        }
    }
}
