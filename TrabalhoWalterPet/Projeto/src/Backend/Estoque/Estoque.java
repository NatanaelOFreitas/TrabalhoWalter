package Backend.Estoque;

import java.util.ArrayList;
import java.util.List;

public class Estoque {


    // atributos

    private List<Produto> estoque;


    // construtor

    public Estoque() {
        this.estoque = new ArrayList<>();
    }


    // getters

    public Produto getProduto(int pos) {
        if (validPos(pos)) {
            return estoque.get(pos);
        }

        return null;
    }

    public List<Produto> getEstoque() {
        return estoque;
    }

    public int tamanho() {
        return estoque.size();
    }


    // methods

    public void adicionarProd(Produto p) {

        int pos = posInEstoque(p);

        if (pos != -1) {
            int total = estoque.get(pos).getQuantd() + p.getQuantd();
            estoque.get(pos).setQuantd(total);
        }
        else {
            estoque.add(p);
        }
    }

    public int posInEstoque(Produto p) {

        for (int i = 0; i < estoque.size(); i++) {

            if (p.getNome().equalsIgnoreCase(estoque.get(i).getNome())) {
                return i;
            }
        }

        return -1;
    }

    public void comprarProd(Produto p, int quantd) {

        int pos = posInEstoque(p);

        if (pos != -1) {

            Produto estoqueProd = estoque.get(pos);

            if (estoqueProd.temSuficiente(quantd)) {

                int restante = estoqueProd.getQuantd() - quantd;

                if (restante == 0) {
                    estoque.remove(pos);
                }
                else {
                    estoqueProd.setQuantd(restante);
                }
            }
            else {
                throw new IllegalArgumentException("Quantidade insuficiente no estoque");
            }
        }
    }

    public boolean validPos(int pos) {
        return pos < estoque.size() && pos >= 0;
    }

    public List<String> listarProdNoEstoque() {

        List<String> produtos = new ArrayList<>();

        for (Produto p : estoque) {
            produtos.add(p.infoProduto());
        }

        return produtos;
    }
}