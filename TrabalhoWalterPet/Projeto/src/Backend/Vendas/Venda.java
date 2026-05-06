package Backend.Vendas;

import Backend.Estoque.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Venda {


    //atributos

    private List<Produto> vendas;


    //construtor

    public Venda(){
        this.vendas = new ArrayList<>();
    }


    //metodos

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
