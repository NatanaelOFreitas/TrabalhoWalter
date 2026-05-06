package Backend.Relatorios;

import Backend.Vendas.Venda;
import Backend.Estoque.Produto;

import java.util.ArrayList;
import java.util.List;

public class Relatorio {

    private List<Venda> vendas;

    public Relatorio(List<Venda> vendas) {
        this.vendas = vendas;
    }

    public float totalFaturamento() {
        float total = 0;

        for (Venda v : vendas) {
            total += v.getTotal();
        }

        return total;
    }

    public void produtosMaisVendidos() {

        List<String> nomes = new ArrayList<>();
        List<Integer> quantidades = new ArrayList<>();

        for (Venda v : vendas) {

            for (Produto p : v.getProdutos()) {

                String nome = p.getNome();

                int index = nomes.indexOf(nome);

                if (index == -1) {
                    nomes.add(nome);
                    quantidades.add(1);
                } else {
                    quantidades.set(index, quantidades.get(index) + 1);
                }
            }
        }

        System.out.println("\nProdutos mais vendidos:");

        for (int i = 0; i < nomes.size(); i++) {
            System.out.println(nomes.get(i) + ": " + quantidades.get(i));
        }
    }

    public void exibirRelatorio() {
        System.out.println("\n===== RELATÓRIO =====");

        System.out.printf("Total faturado: R$%.2f\n", totalFaturamento());

        produtosMaisVendidos();
    }
}