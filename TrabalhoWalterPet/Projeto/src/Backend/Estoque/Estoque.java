package Backend.Estoque;

import java.util.ArrayList;
import java.util.List;

public class Estoque {


    //atributes

    private List<Produto> estoque;
    private List<Produto> prodsVendidos;


    //constructor

    public Estoque(){
        this.estoque = new ArrayList<>();
        this.prodsVendidos = new ArrayList<>();
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
                p.alerta();

                //adicionar a lista de prod vendidos
            }
        }
    }

    public boolean validPos(int pos){
        return pos < estoque.size() && pos >=0;
    }

    public void listarProdNoEstoque(){
        System.out.print("\n----- Estoque -----");
        for (int i = 0; i < estoque.size(); i++){
            System.out.printf("\n[%d] - ", i+1);
            estoque.get(i).printarProd();
        }
    }

    public int posInVendas(Produto p){
        for(int i = 0; i<prodsVendidos.size(); i++){
            if (p.getNome().equals(prodsVendidos.get(i).getNome())){
                return i;
            }
        }
        return -1;
    }

    public void adicionarVendas(Produto p, int q){
        if(posInVendas(p) != -1){
            int total = prodsVendidos.get(posInVendas(p)).getQuantd() + p.getQuantd();
            prodsVendidos.get(posInVendas(p)).setQuantd(total);
        }
        else{
            prodsVendidos.add(p);
        }
    }
}
