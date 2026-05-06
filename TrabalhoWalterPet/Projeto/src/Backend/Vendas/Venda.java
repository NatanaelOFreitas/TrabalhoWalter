package Backend.Vendas;

import Backend.Estoque.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Venda {


    //atributes

    private int id;
    private LocalDateTime dataHora;
    private List<Produto> vendas;
    private float total;
    private String formaPagamento;


    //construtor


    public Venda(int id, List<Produto> vendas, String formaPagamento) {
        this.id = id;
        this.dataHora = LocalDateTime.now();
        this.vendas = new ArrayList<>(vendas);
        this.formaPagamento = formaPagamento;
        this.total = calcularTotal();
    }


    //getters

    public List<Produto> getProdutos() {
        return vendas;
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

    //methods

    private float calcularTotal() {
        float soma = 0;

        for (Produto p : vendas) {
            soma += p.getPrecoUni();
        }

        return soma;
    }

    public void exibirComprovante() {
        System.out.println("\n===== COMPROVANTE =====");
        System.out.println("Venda ID: " + id);
        System.out.println("Data: " + dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

        // agrupar manualmente (sem Map)
        List<String> nomes = new ArrayList<>();
        List<Integer> quantidades = new ArrayList<>();

        for (Produto p : vendas) {
            String nome = p.getNome();

            int index = nomes.indexOf(nome);

            if (index == -1) {
                nomes.add(nome);
                quantidades.add(1);
            } else {
                quantidades.set(index, quantidades.get(index) + 1);
            }
        }

        for (int i = 0; i < nomes.size(); i++) {
            System.out.printf("%s x%d\n", nomes.get(i), quantidades.get(i));
        }

        System.out.printf("Total: R$%.2f\n", total);
        System.out.println("Pagamento: " + formaPagamento);
        System.out.println("=======================\n");
    }

    public int posInVendas(Produto p){
        for(int i = 0; i<vendas.size(); i++){
            if (p.getNome().equals(vendas.get(i).getNome())){
                return i;
            }
        }
        return -1;
    }

    public void adicionarVendas(Produto p, int q){
        if(posInVendas(p) != -1){
            int total = vendas.get(posInVendas(p)).getQuantd() + q;
            vendas.get(posInVendas(p)).setQuantd(total);
        }
        else{
            vendas.add(p);
        }
    }
}
