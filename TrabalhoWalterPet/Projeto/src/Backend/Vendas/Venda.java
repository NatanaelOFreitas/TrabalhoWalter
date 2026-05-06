package Backend.Vendas;

import Backend.Estoque.Produto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Venda {


    //atributos

    private int id;
    private LocalDateTime dataHora;
    private List<Produto> produtos;
    private float total;


    //construtor

    public Venda(int id, List<Produto> produtos) {
        this.id = id;
        this.dataHora = LocalDateTime.now();
        this.produtos = new ArrayList<>(produtos);
        this.total = calcularTotal();
    }


    //métodos

    private float calcularTotal() {

        float soma = 0;

        for (Produto p : produtos) {
            soma += p.getPrecoUni();
        }

        return soma;
    }

    public String gerarComprovante() {

        StringBuilder sb = new StringBuilder();

        sb.append("===== COMPROVANTE =====\n");
        sb.append("Venda ID: ").append(id).append("\n");

        sb.append("Data: ")
                .append(dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
                .append("\n");

        List<String> nomes = new ArrayList<>();
        List<Integer> quantidades = new ArrayList<>();

        for (Produto p : produtos) {

            int index = nomes.indexOf(p.getNome());

            if (index == -1) {
                nomes.add(p.getNome());
                quantidades.add(1);
            }
            else {
                quantidades.set(index, quantidades.get(index) + 1);
            }
        }

        for (int i = 0; i < nomes.size(); i++) {
            sb.append(nomes.get(i))
                    .append(" x")
                    .append(quantidades.get(i))
                    .append("\n");
        }

        sb.append(String.format("Total: R$%.2f\n", total));

        return sb.toString();
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public float getTotal() {
        return total;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }
}