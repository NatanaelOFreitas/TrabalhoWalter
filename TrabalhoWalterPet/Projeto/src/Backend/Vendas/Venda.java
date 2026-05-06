package Backend.Vendas;

import Backend.Estoque.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Venda {


    //atributes

    private List<Produto> vendas;


    //constructor

    public Venda(){
        this.vendas = new ArrayList<>();
    }


    //methods

    public int posInVendas(Produto p){
        for(int i = 0; i<vendas.size(); i++){
            if (p.getNome().equals(vendas.get(i).getNome())){
                return i;
            }
        }
        return -1;
    }

    public void adicionarVendas(Produto p, int q){
        if(posInVendas(p) != -1){
            int total = vendas.get(posInVendas(p)).getQuantd() + q;
            vendas.get(posInVendas(p)).setQuantd(total);
        }
        else{
            vendas.add(p);
        }
    }
}
