package Backend.Relatorios;

import Backend.Estoque.Produto;
import Backend.Vendas.Venda;

import java.util.ArrayList;
import java.util.List;

public class Relatorio {


    //atributos

    private List<Venda> vendas;


    //construtor

    public Relatorio(List<Venda> vendas) {
        this.vendas = vendas;
    }


    //métodos

    public float totalFaturamento() {

        float total = 0;

        for (Venda v : vendas) {
            total += v.getTotal();
        }

        return total;
    }

    public float mediaPorVenda() {

        if (vendas.isEmpty()) {
            return 0;
        }

        return totalFaturamento() / vendas.size();
    }

    public List<String> produtosMaisVendidos() {

        List<String> nomes = new ArrayList<>();
        List<Integer> quantidades = new ArrayList<>();

        for (Venda v : vendas) {

            for (Produto p : v.getProdutos()) {

                int index = nomes.indexOf(p.getNome());

                if (index == -1) {

                    nomes.add(p.getNome());
                    quantidades.add(1);
                }
                else {

                    quantidades.set(
                            index,
                            quantidades.get(index) + 1
                    );
                }
            }
        }

        List<String> ranking = new ArrayList<>();

        for (int i = 0; i < nomes.size(); i++) {

            ranking.add(
                    nomes.get(i) + ": " + quantidades.get(i)
            );
        }

        return ranking;
    }

    public String exibirRelatorio() {

        StringBuilder sb = new StringBuilder();

        sb.append("===== RELATÓRIO =====\n");

        sb.append(
                String.format(
                        "Total faturado: R$%.2f\n",
                        totalFaturamento()
                )
        );

        sb.append(
                String.format(
                        "Média por venda: R$%.2f\n",
                        mediaPorVenda()
                )
        );

        sb.append("\nProdutos mais vendidos:\n");

        for (String s : produtosMaisVendidos()) {
            sb.append(s).append("\n");
        }

        return sb.toString();
    }
}