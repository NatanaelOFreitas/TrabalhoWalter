package Repository;

import Estoque.Produto;
import com.opencsv.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ProdutoRepository {

    private static final String FILE = "data/produtos_carrinho.csv";

    public void salvar(Produto p) throws Exception {
        int id = gerarId();

        try (CSVWriter writer = new CSVWriter(new FileWriter(FILE, true))) {
            writer.writeNext(new String[]{
                    String.valueOf(id),
                    String.valueOf(p.getIdEstoque()),
                    p.getNome(),
                    String.valueOf(p.getQuantd()),
                    String.valueOf(p.getPrecoUni()),
                    p.getDescricao()
            });
        }
    }

    public List<Produto> listar() throws Exception {
        List<Produto> lista = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(FILE))) {
            List<String[]> linhas = reader.readAll();

            for (int i = 1; i < linhas.size(); i++) {
                String[] c = linhas.get(i);

                lista.add(new Produto(
                        Integer.parseInt(c[0]),
                        Integer.parseInt(c[1]),
                        c[2],
                        Integer.parseInt(c[3]),
                        Float.parseFloat(c[4]),
                        c[5]
                ));
            }
        }

        return lista;
    }

    public List<Produto> buscarPorCarrinho(int idCarrinho) throws Exception {
        List<Produto> lista = new ArrayList<>();

        for (Produto p : listar()) {
            if (p.getIdEstoque() == idCarrinho) {
                lista.add(p);
            }
        }

        return lista;
    }

    public void atualizar(Produto atualizado) throws Exception {
        List<String[]> linhas;

        try (CSVReader reader = new CSVReader(new FileReader(FILE))) {
            linhas = reader.readAll();
        }

        for (int i = 1; i < linhas.size(); i++) {
            if (Integer.parseInt(linhas.get(i)[0]) == atualizado.getId()) {
                linhas.set(i, new String[]{
                        String.valueOf(atualizado.getId()),
                        String.valueOf(atualizado.getIdEstoque()),
                        atualizado.getNome(),
                        String.valueOf(atualizado.getQuantd()),
                        String.valueOf(atualizado.getPrecoUni()),
                        atualizado.getDescricao()
                });
            }
        }

        try (CSVWriter writer = new CSVWriter(new FileWriter(FILE))) {
            writer.writeAll(linhas);
        }
    }

    public void deletar(int id) throws Exception {
        List<String[]> linhas;

        try (CSVReader reader = new CSVReader(new FileReader(FILE))) {
            linhas = reader.readAll();
        }

        linhas.removeIf(l -> l[0].equals(String.valueOf(id)));

        try (CSVWriter writer = new CSVWriter(new FileWriter(FILE))) {
            writer.writeAll(linhas);
        }
    }

    private int gerarId() throws Exception {
        if (!Files.exists(Path.of(FILE))) return 1;
        return Files.readAllLines(Path.of(FILE)).size();
    }
}