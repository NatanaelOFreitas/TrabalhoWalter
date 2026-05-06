package Backend.PetShop;

import Backend.Estoque.*;
import Backend.Vendas.Venda;

import java.util.ArrayList;
import java.util.List;

public class PetShop {


    //atributos

    private Estoque estoque;
    private Carrinho carrinho;
    private List<Venda> vendas;
    private int contadorVendas;


    //construtor

    public PetShop() {

        this.estoque = new Estoque();
        this.carrinho = new Carrinho();
        this.vendas = new ArrayList<>();
        this.contadorVendas = 1;

        cargaInicial();
    }


    // métodos de estoque

    public void adicionarProduto(String nome, float preco, int qtd, String desc) {

        Produto p = new Produto(nome, qtd, preco, desc);

        estoque.adicionarProd(p);
    }

    public Produto buscarProduto(String nome) {

        for (Produto p : estoque.getEstoque()) {

            if (p.getNome().equalsIgnoreCase(nome)) {
                return p;
            }
        }

        return null;
    }

    public List<String> listarProdutos() {
        return estoque.listarProdNoEstoque();
    }


    // métodos do carrinho

    public void adicionarAoCarrinho(String nome, int qtd) {

        Produto estoqueProd = buscarProduto(nome);

        if (estoqueProd == null) {
            throw new IllegalArgumentException("Produto não encontrado");
        }

        if (!estoqueProd.temSuficiente(qtd)) {
            throw new IllegalArgumentException("Estoque insuficiente");
        }

        Produto carrinhoProd = new Produto(
                estoqueProd.getNome(),
                qtd,
                estoqueProd.getPrecoUni(),
                estoqueProd.getDescricao()
        );

        carrinho.adicionarProd(carrinhoProd);
    }

    public List<String> verCarrinho() {
        return carrinho.listarCarrinho();
    }

    public float totalCarrinho() {
        return carrinho.getTotal();
    }


    // métodos de venda

    public String finalizarVenda(String formaPagamento) {

        if (carrinho.getCarrinho().isEmpty()) {
            throw new IllegalArgumentException("Carrinho vazio");
        }

        List<Produto> produtosVenda = new ArrayList<>();

        for (Produto p : carrinho.getCarrinho()) {

            Produto estoqueProd = buscarProduto(p.getNome());

            if (estoqueProd != null) {

                estoque.comprarProd(estoqueProd, p.getQuantd());

                for (int i = 0; i < p.getQuantd(); i++) {

                    produtosVenda.add(new Produto(
                            p.getNome(),
                            1,
                            p.getPrecoUni(),
                            p.getDescricao()
                    ));
                }
            }
        }

        Venda venda = new Venda(
                contadorVendas++,
                produtosVenda,
                formaPagamento
        );

        vendas.add(venda);

        String comprovante = venda.gerarComprovante();

        carrinho.pagar();

        return comprovante;
    }

    public void cancelarVenda(int id) {

        Venda venda = buscarVenda(id);

        if (venda == null) {
            throw new IllegalArgumentException("Venda não encontrada");
        }

        for (Produto p : venda.getProdutos()) {

            Produto estoqueProd = buscarProduto(p.getNome());

            if (estoqueProd != null) {
                estoqueProd.setQuantd(
                        estoqueProd.getQuantd() + 1
                );
            }
        }

        vendas.remove(venda);
    }

    public Venda buscarVenda(int id) {

        for (Venda v : vendas) {

            if (v.getId() == id) {
                return v;
            }
        }

        return null;
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    private void cargaInicial() {

        adicionarProduto("ração", 50f, 10, "premium");
        adicionarProduto("brinquedo", 20f, 15, "cachorro");
        adicionarProduto("coleira", 30f, 8, "ajustável");
    }
}