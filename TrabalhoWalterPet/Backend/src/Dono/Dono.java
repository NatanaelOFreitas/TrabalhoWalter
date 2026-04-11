package Dono;

import Animais.*;
import java.util.ArrayList;
import java.util.List;

public class Dono {


    //atributes

    private String nome;
    private String email;
    private int numero;
    private List<Animal> listaPets;


    //constructor

    public Dono(String nome, String email, int numero){
        this.nome = nome;
        this.email = email;
        this.numero = numero;
        this.listaPets = new ArrayList<>();
    }


    //getters

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public int getNumero() {
        return numero;
    }


    //setters

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }


    //methods

    public void adicionarPet(Animal pet){
        if (pet != null){
            this.listaPets.add(pet);
            System.out.printf(pet.getNome() + " adicionado com sucesso a lista!");
        }
    }

    public void listarPets(){
        System.out.println("Lista de Pets:");
        for (int i = 0; i < listaPets.size(); i++){
            System.out.printf("\n[%d] - ", i+1);
            listaPets.get(i).printar();
        }
    }
}
