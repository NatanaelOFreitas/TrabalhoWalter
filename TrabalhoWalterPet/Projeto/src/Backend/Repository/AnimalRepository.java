package Backend.Repository;

import Backend.Animais.Animal;
import com.opencsv.*;
import Backend.Utils.Utils;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class AnimalRepository {

    private static final String FILE = "data/animais.csv";
    private static final Object LOCK = new Object();

    public void salvar(Animal a) throws Exception {
        synchronized (LOCK) {
            int id = gerarId();

            String[] campos = new String[]{
                    String.valueOf(id),
                    String.valueOf(a.getIdDono()),
                    Utils.escaparCSV(a.getNome()),
                    String.valueOf(a.getIdade())
            };

            try (CSVWriter writer = new CSVWriter(new FileWriter(FILE, true))) {
                writer.writeNext(campos);
            }
        }
    }

    public List<Animal> listar() throws Exception {
        synchronized (LOCK) {
            List<Animal> lista = new ArrayList<>();
            if (!Files.exists(Path.of(FILE))) return lista;

            try (CSVReader reader = new CSVReader(new FileReader(FILE))) {
                List<String[]> linhas = reader.readAll();
                for (int i = 1; i < linhas.size(); i++) {
                    String[] c = linhas.get(i);
                    if (c.length >= 4) {
                        lista.add(new Animal(
                                Integer.parseInt(c[0]),
                                Integer.parseInt(c[1]),
                                c[2],
                                Double.parseDouble(c[3])
                        ));
                    }
                }
            }
            return lista;
        }
    }

    public List<Animal> buscarPorDono(int idDono) throws Exception {
        List<Animal> todos = listar();
        List<Animal> filtrados = new ArrayList<>();
        for (Animal a : todos) {
            if (a.getIdDono() == idDono) {
                filtrados.add(a);
            }
        }
        return filtrados;
    }

    public Animal buscarPorId(int id) throws Exception {
        for (Animal a : listar()) {
            if (a.getId() == id) return a;
        }
        return null;
    }

    public void atualizar(Animal atualizado) throws Exception {
        synchronized (LOCK) {
            List<String[]> linhas;
            try (CSVReader reader = new CSVReader(new FileReader(FILE))) {
                linhas = reader.readAll();
            }

            for (int i = 1; i < linhas.size(); i++) {
                if (Integer.parseInt(linhas.get(i)[0]) == atualizado.getId()) {
                    linhas.set(i, new String[]{
                            String.valueOf(atualizado.getId()),
                            String.valueOf(atualizado.getIdDono()),
                            Utils.escaparCSV(atualizado.getNome()),
                            String.valueOf(atualizado.getIdade())
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
