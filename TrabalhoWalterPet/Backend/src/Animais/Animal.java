package Animais;

public class Animal {

    //atributes

    private int id;
    private int idDono;
    private String nome;
    private double idade;


    //constructor

    public Animal(int id, int idDono, String nome, double idade){
        this.id = id;
        this.idDono = idDono;
        this.nome = nome.toLowerCase();
        this.idade = idade;
    }


    //getters

    public String getNome(){
        return nome.substring(0, 1).toUpperCase() + nome.substring(1).toLowerCase();
    }

    public double getIdade(){
        return idade;
    }

    public int getId(){
        return id;
    }

    public int getIdDono(){
        return idDono;
    }


    //setters

    public void setNome(String nome) {
        this.nome = nome.toLowerCase();
    }

    public void setIdade(double idade) {
        this.idade = idade;
    }


    //methods

    public void printar(){
        System.out.printf("%s - %.2f;", getNome(), getIdade());
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
