package Backend.Estoque;

import java.util.ArrayList;
import java.util.List;

public class Estoque {


    //atributes

    private List<Produto> estoque;


    //constructor

    public Estoque(){
        this.estoque = new ArrayList<>();
    }


    //getters

    public Produto getProduto(int pos){
        if(validPos(pos)){
            return estoque.get(pos);
        }
        else{
            return null;
        }
    }


    //methods

    public void adicionarProd(Produto p){
        if(posInEstoque(p) != -1){
            int total = estoque.get(posInEstoque(p)).getQuantd() + p.getQuantd();
            estoque.get(posInEstoque(p)).setQuantd(total);
        }
        else{
            estoque.add(p);
        }
    }

    public int posInEstoque(Produto p){
       for(int i = 0; i<estoque.size(); i++){
           if (p.getNome().equals(estoque.get(i).getNome())){
               return i;
           }
       }
       return -1;
    }

    public void comprarProd(Produto p, int quantd){
        if(posInEstoque(p) != -1){
            if(estoque.get(posInEstoque(p)).temSuficiente(quantd)){
                int restante = estoque.get(posInEstoque(p)).getQuantd() - p.getQuantd();
                if (restante == 0){
                    estoque.remove(posInEstoque(p));
                }
                else{
                    estoque.get(posInEstoque(p)).setQuantd(restante);
                }
            }
        }
    }

    public boolean validPos(int pos){
        return pos < estoque.size() && pos >=0;
    }
}
