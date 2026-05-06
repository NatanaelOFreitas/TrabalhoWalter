package Backend.Estoque;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {


    // atributos

    private List<Produto> carrinho;
    private float totalCarrinho;


    // construtor

    public Carrinho() {
        this.totalCarrinho = 0.0f;
        this.carrinho = new ArrayList<>();
    }


    // setters

    public void setTotalCarrinho(float novoTotal) {
        this.totalCarrinho = novoTotal;
    }


    // getters

    public Produto getProduto(int pos) {

        if (validPos(pos)) {
            return carrinho.get(pos);
        }

        return null;
    }

    public List<Produto> getCarrinho() {
        return carrinho;
    }

    public float getTotal() {
        return totalCarrinho;
    }

    public int tamanho() {
        return carrinho.size();
    }


    // métodos

    public List<String> listarCarrinho() {

        List<String> lista = new ArrayList<>();

        for (Produto p : carrinho) {
            lista.add(p.infoProduto());
        }

        return lista;
    }

    public void adicionarProd(Produto p) {

        int pos = posInCarrinho(p);

        if (pos != -1) {

            int total = carrinho.get(pos).getQuantd() + p.getQuantd();

            adicionarTotal(p);

            carrinho.get(pos).setQuantd(total);
        }
        else {
            carrinho.add(p);
            adicionarTotal(p);
        }
    }

    public int posInCarrinho(Produto p) {

        for (int i = 0; i < carrinho.size(); i++) {

            if (p.getNome().equalsIgnoreCase(carrinho.get(i).getNome())) {
                return i;
            }
        }

        return -1;
    }

    public void adicionarTotal(Produto p) {

        float adicionar = p.getQuantd() * p.getPrecoUni();

        setTotalCarrinho(getTotal() + adicionar);
    }

    public boolean validPos(int pos) {
        return pos < carrinho.size() && pos >= 0;
    }

    public void pagar() {
        this.totalCarrinho = 0.0f;
        carrinho.clear();
    }

    public void removerProduto(String nome) {

        for (int i = 0; i < carrinho.size(); i++) {

            Produto p = carrinho.get(i);

            if (p.getNome().equalsIgnoreCase(nome)) {

                float remover = p.getQuantd() * p.getPrecoUni();

                totalCarrinho -= remover;

                carrinho.remove(i);

                return;
            }
        }

        throw new IllegalArgumentException("Produto não encontrado no carrinho");
    }

    public void alterarQuantidade(String nome, int novaQtd) {

        if (novaQtd <= 0) {
            throw new IllegalArgumentException("Quantidade inválida");
        }

        for (Produto p : carrinho) {

            if (p.getNome().equalsIgnoreCase(nome)) {

                totalCarrinho -= p.getQuantd() * p.getPrecoUni();

                p.setQuantd(novaQtd);
                
                totalCarrinho += p.getQuantd() * p.getPrecoUni();

                return;
            }
        }

        throw new IllegalArgumentException("Produto não encontrado no carrinho");
    }
}