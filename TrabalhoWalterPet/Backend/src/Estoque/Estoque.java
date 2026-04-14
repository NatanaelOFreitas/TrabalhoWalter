package Estoque;

import java.util.ArrayList;
import java.util.List;

public class Estoque {


    //atributes

    private List<Produto> estoque;


    //constructor

    public Estoque(){
        this.estoque = new ArrayList<>();
    }


    //methods

    public void adicionarProd(Produto p){
        this.estoque.add(p);
    }
}
