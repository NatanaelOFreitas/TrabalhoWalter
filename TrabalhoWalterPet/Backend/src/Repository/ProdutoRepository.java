package Repository;

import Estoque.Produto;
import com.opencsv.*;
import Utils.Utils;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ProdutoRepository {

    private static final String FILE = "data/produtos_carrinho.csv";
    private static final Object LOCK = new Object();

    public void salvar(Produto p) throws Exception {
        synchronized (LOCK) {
            int id = gerarId();

            String[] campos = new String[]{
                    String.valueOf(id),
                    String.valueOf(p.getIdEstoque()),
                    Utils.escaparCSV(p.getNome()),
                    String.valueOf(p.getQuantd()),
                    String.valueOf(p.getPrecoUni()),
                    Utils.escaparCSV(p.getDescricao())
            };

            try (CSVWriter writer = new CSVWriter(new FileWriter(FILE, true))) {
                writer.writeNext(campos);
            }
        }
    }

    public List<Produto> listar() throws Exception {
        synchronized (LOCK) {
            List<Produto> lista = new ArrayList<>();
            if (!Files.exists(Path.of(FILE))) return lista;

            try (CSVReader reader = new CSVReader(new FileReader(FILE))) {
                List<String[]> linhas = reader.readAll();
                for (int i = 1; i < linhas.size(); i++) {
                    String[] c = linhas.get(i);
                    if (c.length >= 6) {
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
            }
            return lista;
        }
    }

    public List<Produto> buscarPorCarrinho(int idCarrinho) throws Exception {
        List<Produto> todos = listar();
        List<Produto> filtrados = new ArrayList<>();
        for (Produto p : todos) {
            if (p.getIdEstoque() == idCarrinho) {
                filtrados.add(p);
            }
        }
        return filtrados;
    }

    public Produto buscarPorId(int id) throws Exception {
        for (Produto p : listar()) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    public void atualizar(Produto atualizado) throws Exception {
        synchronized (LOCK) {
            List<String[]> linhas;
            try (CSVReader reader = new CSVReader(new FileReader(FILE))) {
                linhas = reader.readAll();
            }

            for (int i = 1; i < linhas.size(); i++) {
                if (Integer.parseInt(linhas.get(i)[0]) == atualizado.getId()) {
                    linhas.set(i, new String[]{
                            String.valueOf(atualizado.getId()),
                            String.valueOf(atualizado.getIdEstoque()),
                            Utils.escaparCSV(atualizado.getNome()),
                            String.valueOf(atualizado.getQuantd()),
                            String.valueOf(atualizado.getPrecoUni()),
                            Utils.escaparCSV(atualizado.getDescricao())
                    });
                }
            }

            try (CSVWriter writer = new CSVWriter(new FileWriter(FILE))) {
                writer.writeAll(linhas);
            }
        }
    }

    public void deletar(int id) throws Exception {
        synchronized (LOCK) {
            List<String[]> linhas;
            try (CSVReader reader = new CSVReader(new FileReader(FILE))) {
                linhas = reader.readAll();
            }

            linhas.removeIf(l -> l[0].equals(String.valueOf(id)));

            try (CSVWriter writer = new CSVWriter(new FileWriter(FILE))) {
                writer.writeAll(linhas);
            }
        }
    }

    private int gerarId() throws Exception {
        if (!Files.exists(Path.of(FILE))) return 1;
        return Files.readAllLines(Path.of(FILE)).size();
    }
}