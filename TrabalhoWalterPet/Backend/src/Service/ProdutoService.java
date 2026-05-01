package Service;

import Estoque.Produto;
import Repository.ProdutoRepository;
import java.util.List;

public class ProdutoService {

    private ProdutoRepository produtoRepo;

    public ProdutoService() {
        this.produtoRepo = new ProdutoRepository();
    }

    public Produto cadastrarProduto(int idEstoque, String nome, int quantidade,
                                    float precoUnitario, String descricao) throws Exception {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório");
        }

        if (quantidade < 0) {
            throw new IllegalArgumentException("Quantidade não pode ser negativa");
        }

        if (precoUnitario <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }
        
        if (descricao != null && descricao.length() > 500) {
            throw new IllegalArgumentException("Descrição muito longa (máx: 500 caracteres)");
        }

        Produto produto = new Produto(0, idEstoque, nome, quantidade,
                precoUnitario, descricao);
        produtoRepo.salvar(produto);

        return produto;
    }

    public List<Produto> listarProdutos() throws Exception {
        return produtoRepo.listar();
    }

    public List<Produto> listarProdutosDoCarrinho(int idCarrinho) throws Exception {
        return produtoRepo.buscarPorCarrinho(idCarrinho);
    }

    public Produto buscarProduto(int id) throws Exception {
        Produto produto = produtoRepo.buscarPorId(id);
        if (produto == null) {
            throw new IllegalArgumentException("Produto com ID " + id + " não encontrado");
        }
        return produto;
    }

    public void atualizarProduto(Produto produto) throws Exception {
        if (produtoRepo.buscarPorId(produto.getId()) == null) {
            throw new IllegalArgumentException("Produto não encontrado");
        }

        if (produto.getNome() == null || produto.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (produto.getQuantd() < 0) {
            throw new IllegalArgumentException("Quantidade negativa");
        }
        if (produto.getPrecoUni() <= 0) {
            throw new IllegalArgumentException("Preço deve ser positivo");
        }

        produtoRepo.atualizar(produto);
    }

    public void removerProduto(int id) throws Exception {
        if (produtoRepo.buscarPorId(id) == null) {
            throw new IllegalArgumentException("Produto não encontrado");
        }
        produtoRepo.deletar(id);
    }

    public void atualizarEstoque(int idProduto, int novaQuantidade) throws Exception {
        Produto produto = produtoRepo.buscarPorId(idProduto);
        if (produto == null) {
            throw new IllegalArgumentException("Produto não encontrado");
        }

        if (novaQuantidade < 0) {
            throw new IllegalArgumentException("Quantidade não pode ser negativa");
        }

        produto.setQuantd(novaQuantidade);
        produtoRepo.atualizar(produto);
    }

    public float calcularTotalCarrinho(int idCarrinho) throws Exception {
        List<Produto> produtos = produtoRepo.buscarPorCarrinho(idCarrinho);
        float total = 0;
        for (Produto p : produtos) {
            total += p.getPrecoUni() * p.getQuantd();
        }
        return total;
    }
}