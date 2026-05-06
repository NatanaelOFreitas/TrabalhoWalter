package Backend.PetShop;

import Backend.Estoque.*;
import Backend.Vendas.Venda;

import java.util.ArrayList;
import java.util.List;

public class PetShop {

    private Estoque estoque;
    private Carrinho carrinho;
    private List<Venda> vendas;
    private int contadorVendas;

    public PetShop() {
        this.estoque = new Estoque();
        this.carrinho = new Carrinho();
        this.vendas = new ArrayList<>();
        this.contadorVendas = 1;

        cargaInicial();
    }

    // ================= ESTOQUE =================

    public void adicionarProduto(String nome, float preco, int qtd, String desc) {
        Produto p = new Produto(nome, qtd, preco, desc);
        estoque.adicionarProd(p);
    }

    public Produto buscarProduto(String nome) {
        for (int i = 0; i < 1000; i++) {
            Produto p = estoque.getProduto(i);
            if (p == null) break;

            if (p.getNome().equalsIgnoreCase(nome)) {
                return p;
            }
        }
        return null;
    }

    public void listarProdutos() {
        estoque.listarProdNoEstoque();
    }

    // ================= CARRINHO =================

    public void adicionarAoCarrinho(String nome, int qtd) {
        Produto estoqueProd = buscarProduto(nome);

        if (estoqueProd == null) {
            throw new RuntimeException("Produto não encontrado");
        }

        if (!estoqueProd.temSuficiente(qtd)) {
            throw new RuntimeException("Estoque insuficiente");
        }

        // cria um NOVO produto para o carrinho (importante!)
        Produto carrinhoProd = new Produto(
                estoqueProd.getNome(),
                qtd,
                estoqueProd.getPrecoUni(),
                estoqueProd.getDescricao()
        );

        carrinho.adicionarProd(carrinhoProd);
        carrinho.adicionarTotal(carrinhoProd);
    }

    public void verCarrinho() {
        carrinho.listarCarrinho();
    }

    // ================= VENDA =================

    public void finalizarVenda(String formaPagamento) {

        if (carrinho.getTotal() == 0) {
            throw new RuntimeException("Carrinho vazio");
        }

        List<Produto> produtosVenda = new ArrayList<>();

        // percorre carrinho
        for (int i = 0; i < 1000; i++) {

            Produto p = carrinho.getProduto(i);
            if (p == null) break;

            Produto estoqueProd = buscarProduto(p.getNome());

            if (estoqueProd == null) continue;

            // baixa estoque
            estoque.comprarProd(estoqueProd, p.getQuantd());

            // transforma quantidade em itens individuais
            for (int j = 0; j < p.getQuantd(); j++) {
                produtosVenda.add(new Produto(
                        p.getNome(),
                        1,
                        p.getPrecoUni(),
                        p.getDescricao()
                ));
            }
        }

        Venda venda = new Venda(contadorVendas++, produtosVenda, formaPagamento);
        vendas.add(venda);

        venda.exibirComprovante();

        carrinho.pagar();
    }

    public void cancelarVenda(int id) {

        Venda venda = buscarVenda(id);

        if (venda == null) {
            throw new RuntimeException("Venda não encontrada");
        }

        // devolve estoque
        for (Produto p : venda.getProdutos()) {
            Produto estoqueProd = buscarProduto(p.getNome());

            if (estoqueProd != null) {
                estoqueProd.setQuantd(estoqueProd.getQuantd() + 1);
            }
        }

        vendas.remove(venda);

        System.out.println("Venda cancelada.");
    }

    public Venda buscarVenda(int id) {
        for (Venda v : vendas) {
            if (v.getId() == id) return v;
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