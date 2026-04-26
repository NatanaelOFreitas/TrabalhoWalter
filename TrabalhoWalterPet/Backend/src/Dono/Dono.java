package Dono;

import Animais.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Dono {


    //atributes

    private String nome;
    private String email;
    private int numero;
    private List<Animal> listaPets;


    //constructor

    public Dono(String nome, String email, int numero){
        this.nome = nome.toLowerCase();
        this.email = email;
        this.numero = numero;
        this.listaPets = new ArrayList<>();
    }


    //getters

    public String getNome() {
        return nome.substring(0, 1).toUpperCase() + nome.substring(1).toLowerCase();
    }

    public String getEmail() {
        return email;
    }

    public int getNumero() {
        return numero;
    }


    //setters

    public void setNome(String nome) {
        this.nome = nome.toLowerCase();
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
            System.out.println(pet.getNome() + " adicionado com sucesso a lista!");
        }
    }

    public void listarPets(){
        System.out.println("Lista de Pets:");
        for (int i = 0; i < listaPets.size(); i++){
            System.out.printf("\n[%d] - ", i+1);
            listaPets.get(i).printar();
        }
    }

    public boolean validPos(int pos){
        return pos > 0 && pos <= listaPets.size();
    }

    public void ordenadorLista(){
        Collections.sort(listaPets, (a, b) ->
                a.getNome().compareToIgnoreCase(b.getNome()));
    }

    public int buscarPetPorNome(String nome){
        final int NIL = -1;
        if(!isSorted()){
            ordenadorLista();
        }

        int esquerda = 0;
        int direita = listaPets.size() - 1;

        while (esquerda <= direita) {
            int meio = esquerda + (direita - esquerda) / 2;
            String nomeMeio = listaPets.get(meio).getNome().toLowerCase();
            int comparacao = nomeMeio.compareTo(nome);

            if (comparacao == 0) {
                return meio;
            } else if (comparacao < 0) {
                esquerda = meio + 1;
            } else {
                direita = meio - 1;
            }
        }
        return NIL;
    }

    public void printarPet(int pos){
        if(validPos(pos)){
            listaPets.get(pos).printar();
        }
    }

    public boolean isSorted(){
        if (listaPets == null || listaPets.size() <= 1) {
            return true;
        }

        for (int i = 0; i < listaPets.size() - 1; i++) {
            String nomeAtual = listaPets.get(i).getNome().toLowerCase();
            String nomeProximo = listaPets.get(i + 1).getNome().toLowerCase();

            if (nomeAtual.compareTo(nomeProximo) > 0) {
                return false;
            }
        }
        return true;
    }
}
