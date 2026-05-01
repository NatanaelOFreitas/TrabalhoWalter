package Dono;

import Animais.*;
import Estoque.Carrinho;
import Utils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.regex.Pattern;

public class Dono {


    //atributes

    private int id;
    private String nome;
    private String email;
    private int numero;
    private List<Animal> listaPets;
    private Carrinho carrinho;
    private String foto;
    private String senha;
    private String salt;


    //constructor

    public Dono(int id, String nome, String email, int numero, String senha, String foto) {
        this.id = id;
        this.nome = nome.toLowerCase();
        this.email = email;
        this.numero = numero;
        this.listaPets = new ArrayList<>();
        this.carrinho = new Carrinho();
        this.foto = foto.isEmpty() ? "" : foto;
        this.salt = Utils.gerarSalt();
        this.senha = Utils.hashComSalt(senha, this.salt);
    }

    public Dono(int id, String nome, String email, int numero, String senha, String salt, String foto) {
        this.id = id;
        this.nome = nome.toLowerCase();
        this.email = email;
        this.numero = numero;
        this.listaPets = new ArrayList<>();
        this.carrinho = new Carrinho();
        this.foto = foto.isEmpty() ? "" : foto;
        this.salt = salt;
        this.senha = senha;
    }


    //getters

    public int getId(){
        return id;
    }

    public String getNome() {
        return nome.substring(0, 1).toUpperCase() + nome.substring(1).toLowerCase();
    }

    public String getEmail() {
        return email;
    }

    public int getNumero() {
        return numero;
    }

    public String getFoto() {
        return foto;
    }

    public String getSenha(){
        return senha;
    }
    

    //setters

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        this.nome = nome.trim().toLowerCase();
    }

    public void setEmail(String email) {
        if (!Utils.isEmailValido(email)) {
            throw new IllegalArgumentException("Email inválido: " + email);
        }
        this.email = email;
    }

    public void setNumero(int numero) {
        if (numero <= 0) {
            throw new IllegalArgumentException("Número deve ser positivo");
        }
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

    public String gerarHash256(String texto) {
        try {
            byte[] hashBytes = java.security.MessageDigest
                    .getInstance("SHA-256")
                    .digest(texto.getBytes(java.nio.charset.StandardCharsets.UTF_8));

            return java.util.HexFormat.of().formatHex(hashBytes);
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao processar o hash: Algoritmo não encontrado", e);
        }
    }
}
