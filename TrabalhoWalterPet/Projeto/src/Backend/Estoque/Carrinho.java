package Backend.Estoque;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {


    //atributes

    private List<Produto> carrinho;
    private float totalCarrinho;


    //Constructor

    public Carrinho(){
        this.totalCarrinho = 0.0f;
        this.carrinho = new ArrayList<>();
    }


    //setters

    public void setTotalCarrinho(float novoTotal){
        this.totalCarrinho = novoTotal;
    }


    //getters

    public Produto getProduto(int pos){
        if(validPos(pos)){
            return carrinho.get(pos);
        }
        else{
            return null;
        }
    }

    public void listarCarrinho(){
        for (int i = 0; i < carrinho.size(); i++){
            if (getProduto(i).getDescricao().isBlank()){
                System.out.printf("[%d] - %s - %.2f - %d uni(s);",
                        i+1,
                        getProduto(i).getNome(),
                        getProduto(i).getPrecoUni(),
                        getProduto(i).getQuantd());
            }
            else{
                System.out.printf("[%d] - %s - %.2f - %d uni(s) - %s;",
                        i+1,
                        getProduto(i).getNome(),
                        getProduto(i).getPrecoUni(),
                        getProduto(i).getQuantd(),
                        getProduto(i).getDescricao());
            }
        }
    }

    public float getTotal(){
        return totalCarrinho;
    }


    //methods

    public void adicionarProd(Produto p){
        if(posInCarrinho(p) != -1){
            int total = carrinho.get(posInCarrinho(p)).getQuantd() + p.getQuantd();
            adicionarTotal(p);
            carrinho.get(posInCarrinho(p)).setQuantd(total);
        }
        else{
            carrinho.add(p);
        }
    }

    public int posInCarrinho(Produto p){
        for(int i = 0; i<carrinho.size(); i++){
            if (p.getNome().equals(carrinho.get(i).getNome())){
                return i;
            }
        }
        return -1;
    }

    public void adicionarTotal(Produto p){
        float adicionar = p.getQuantd() * p.getPrecoUni();
        setTotalCarrinho(getTotal() + adicionar);
    }

    public boolean validPos(int pos){
        return pos < carrinho.size() && pos >=0;
    }

    public void pagar(){
        this.totalCarrinho = 0.0f;
        carrinho.clear();
    }
}
