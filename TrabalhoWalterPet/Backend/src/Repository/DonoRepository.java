package Repository;

import Dono.Dono;
import com.opencsv.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class DonoRepository {

    private static final String FILE = "data/usuarios.csv";

    public void salvar(Dono dono) throws Exception {
        int id = gerarId();

        try (CSVWriter writer = new CSVWriter(new FileWriter(FILE, true))) {
            writer.writeNext(new String[]{
                    String.valueOf(id),
                    dono.getNome(),
                    dono.getEmail(),
                    String.valueOf(dono.getNumero()),
                    dono.getSenha(),
                    dono.getFoto()
            });
        }
    }

    public List<Dono> listar() throws Exception {
        List<Dono> lista = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(FILE))) {
            List<String[]> linhas = reader.readAll();

            for (int i = 1; i < linhas.size(); i++) {
                String[] c = linhas.get(i);

                lista.add(new Dono(
                        Integer.parseInt(c[0]),
                        c[1],
                        c[2],
                        Integer.parseInt(c[3]),
                        c[4],
                        c[5]
                ));
            }
        }

        return lista;
    }

    public Dono buscarPorId(int id) throws Exception {
        for (Dono d : listar()) {
            if (d.getId() == id) return d;
        }
        return null;
    }

    public void atualizar(Dono atualizado) throws Exception {
        List<String[]> linhas;

        try (CSVReader reader = new CSVReader(new FileReader(FILE))) {
            linhas = reader.readAll();
        }

        for (int i = 1; i < linhas.size(); i++) {
            if (Integer.parseInt(linhas.get(i)[0]) == atualizado.getId()) {
                linhas.set(i, new String[]{
                        String.valueOf(atualizado.getId()),
                        atualizado.getNome(),
                        atualizado.getEmail(),
                        String.valueOf(atualizado.getNumero()),
                        atualizado.getSenha(),
                        atualizado.getFoto()
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