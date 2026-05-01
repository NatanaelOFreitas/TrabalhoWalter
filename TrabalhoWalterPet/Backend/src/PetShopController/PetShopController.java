package PetShopController;

import Animais.Animal;
import Dono.Dono;
import Estoque.Produto;
import Service.AnimalService;
import Service.DonoService;
import Service.ProdutoService;
import java.util.List;

public class PetShopController {

    private AnimalService animalService;
    private DonoService donoService;
    private ProdutoService produtoService;

    private Dono donoLogado;
    private boolean logado = false;

    public PetShopController() {
        this.animalService = new AnimalService();
        this.donoService = new DonoService();
        this.produtoService = new ProdutoService();
    }

    // ==================== Authentication ====================

    public boolean login(String email, String senha) {
        try {
            this.donoLogado = donoService.login(email, senha);
            this.logado = true;
            return true;
        } catch (Exception e) {
            System.err.println("Erro no login: " + e.getMessage());
            return false;
        }
    }

    public void logout() {
        this.donoLogado = null;
        this.logado = false;
    }

    public boolean isLogado() {
        return logado;
    }

    public Dono getDonoLogado() {
        return donoLogado;
    }

    // ==================== Dono (Cadastro) ====================

    public String cadastrarDono(String nome, String email, int numero, String senha) {
        try {
            Dono novo = donoService.cadastrarDono(nome, email, numero, senha);
            return "Dono cadastrado com sucesso! ID: " + novo.getId();
        } catch (IllegalArgumentException e) {
            return "Erro: " + e.getMessage();
        } catch (Exception e) {
            return "Erro inesperado: " + e.getMessage();
        }
    }

    public List<Dono> listarDonos() {
        try {
            return donoService.listarDonos();
        } catch (Exception e) {
            System.err.println("Erro ao listar donos: " + e.getMessage());
            return List.of();
        }
    }

    public Dono buscarDono(int id) {
        try {
            return donoService.buscarDono(id);
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            return null;
        }
    }

    // ==================== Animal ====================

    public String cadastrarAnimal(String nome, double idade) {
        if (!logado) {
            return "Erro: Faça login primeiro";
        }
        try {
            Animal animal = animalService.cadastrarAnimal(
                    donoLogado.getId(),
                    nome,
                    idade
            );
            return "Animal '" + animal.getNome() + "' cadastrado com sucesso!";
        } catch (IllegalArgumentException e) {
            return "Erro: " + e.getMessage();
        } catch (Exception e) {
            return "Erro inesperado: " + e.getMessage();
        }
    }

    public List<Animal> listarMeusAnimais() {
        if (!logado) return List.of();
        try {
            return animalService.listarAnimaisDoDono(donoLogado.getId());
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            return List.of();
        }
    }

    public List<Animal> listarTodosAnimais() {
        try {
            return animalService.listarAnimais();
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            return List.of();
        }
    }

    public String removerAnimal(int idAnimal) {
        if (!logado) return "Erro: Faça login primeiro";
        try {
            // Opcional: verificar se animal pertence ao dono logado
            Animal animal = animalService.buscarAnimal(idAnimal);
            if (animal.getIdDono() != donoLogado.getId()) {
                return "Erro: Você não é o dono deste animal";
            }
            animalService.removerAnimal(idAnimal);
            return "Animal removido com sucesso!";
        } catch (IllegalArgumentException e) {
            return "Erro: " + e.getMessage();
        } catch (Exception e) {
            return "Erro inesperado: " + e.getMessage();
        }
    }

    // ==================== Produto ====================

    public String cadastrarProduto(String nome, int quantidade,
                                   float preco, String descricao) {
        if (!logado) return "Erro: Faça login primeiro";
        // idEstoque usa ID do carrinho do dono logado (0 se não existir)
        try {
            int idEstoque = donoLogado.getCarrinho() != null ?
                    donoLogado.getCarrinho().getId() : 0;
            Produto produto = produtoService.cadastrarProduto(
                    idEstoque, nome, quantidade, preco, descricao
            );
            return "Produto '" + produto.getNome() + "' cadastrado!";
        } catch (IllegalArgumentException e) {
            return "Erro: " + e.getMessage();
        } catch (Exception e) {
            return "Erro inesperado: " + e.getMessage();
        }
    }

    public List<Produto> listarProdutos() {
        try {
            return produtoService.listarProdutos();
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            return List.of();
        }
    }

    public String removerProduto(int idProduto) {
        if (!logado) return "Erro: Faça login primeiro";
        try {
            produtoService.removerProduto(idProduto);
            return "Produto removido!";
        } catch (Exception e) {
            return "Erro: " + e.getMessage();
        }
    }

    public String calcularTotalCarrinho() {
        if (!logado) return "R$ 0,00";
        try {
            int idCarrinho = donoLogado.getCarrinho() != null ?
                    donoLogado.getCarrinho().getId(): 0;
            float total = produtoService.calcularTotalCarrinho(idCarrinho);
            return String.format("R$ %.2f", total);
        } catch (Exception e) {
            return "R$ 0,00";
        }
    }

    // ==================== Utilitários ====================

    public String getMensagemboasVindas() {
        if (!logado) {
            return "Bem-vindo! Faça login para continuar.";
        }
        return "Olá, " + donoLogado.getNome() + "! O que deseja fazer?";
    }
}