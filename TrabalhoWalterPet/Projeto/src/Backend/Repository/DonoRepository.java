package Backend.Repository;

import Backend.Dono.Dono;
import com.opencsv.*;
import Backend.Utils.Utils;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class DonoRepository {

    private static final String FILE = "data/usuarios.csv";
    private static final Object LOCK = new Object(); // Lock para synchronized

    public void salvar(Dono dono) throws Exception {
        synchronized (LOCK) {
            int id = gerarId();

            // Escapar campos antes de salvar
            String[] campos = new String[]{
                    String.valueOf(id),
                    Utils.escaparCSV(dono.getNome()),
                    Utils.escaparCSV(dono.getEmail()),
                    String.valueOf(dono.getNumero()),
                    Utils.escaparCSV(dono.getSenha()),
                    Utils.escaparCSV(dono.getSenha()) // Na verdade é o salt, ajuste conforme model
            };

            try (CSVWriter writer = new CSVWriter(new FileWriter(FILE, true))) {
                writer.writeNext(campos);
            }
        }
    }

    public List<Dono> listar() throws Exception {
        synchronized (LOCK) {
            List<Dono> lista = new ArrayList<>();
            if (!Files.exists(Path.of(FILE))) return lista;

            try (CSVReader reader = new CSVReader(new FileReader(FILE))) {
                List<String[]> linhas = reader.readAll();
                for (int i = 1; i < linhas.size(); i++) {
                    String[] c = linhas.get(i);
                    if (c.length >= 6) {
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
            }
            return lista;
        }
    }

    public Dono buscarPorId(int id) throws Exception {
        for (Dono d : listar()) {
            if (d.getId() == id) return d;
        }
        return null;
    }

    public Dono buscarPorEmail(String email) throws Exception {
        for (Dono d : listar()) {
            if (d.getEmail().equalsIgnoreCase(email)) return d;
        }
        return null;
    }

    public void atualizar(Dono atualizado) throws Exception {
        synchronized (LOCK) {
            List<String[]> linhas;
            try (CSVReader reader = new CSVReader(new FileReader(FILE))) {
                linhas = reader.readAll();
            }

            for (int i = 1; i < linhas.size(); i++) {
                if (Integer.parseInt(linhas.get(i)[0]) == atualizado.getId()) {
                    linhas.set(i, new String[]{
                            String.valueOf(atualizado.getId()),
                            Utils.escaparCSV(atualizado.getNome()),
                            Utils.escaparCSV(atualizado.getEmail()),
                            String.valueOf(atualizado.getNumero()),
                            Utils.escaparCSV(atualizado.getSenha()),
                            Utils.escaparCSV(atualizado.getFoto())
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
        int linhas = Files.readAllLines(Path.of(FILE)).size();
        if (linhas <= 1) return 1;

        List<String> todasLinhas = Files.readAllLines(Path.of(FILE));
        String ultimaLinha = todasLinhas.get(todasLinhas.size() - 1);
        String[] campos = Utils.escaparCSV(ultimaLinha).split(",");
        return linhas;
    }
}