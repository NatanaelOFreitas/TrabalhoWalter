package Repository;

import Animais.Animal;
import com.opencsv.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class AnimalRepository {

    private static final String FILE = "data/animais.csv";

    public void salvar(Animal a) throws Exception {
        int id = gerarId();

        try (CSVWriter writer = new CSVWriter(new FileWriter(FILE, true))) {
            writer.writeNext(new String[]{
                    String.valueOf(id),
                    String.valueOf(a.getIdDono()),
                    a.getNome(),
                    String.valueOf(a.getIdade())
            });
        }
    }

    public List<Animal> listar() throws Exception {
        List<Animal> lista = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(FILE))) {
            List<String[]> linhas = reader.readAll();

            for (int i = 1; i < linhas.size(); i++) {
                String[] c = linhas.get(i);

                lista.add(new Animal(
                        Integer.parseInt(c[0]),
                        Integer.parseInt(c[1]),
                        c[2],
                        Double.parseDouble(c[3])
                ));
            }
        }

        return lista;
    }

    public List<Animal> buscarPorDono(int idDono) throws Exception {
        List<Animal> lista = new ArrayList<>();

        for (Animal a : listar()) {
            if (a.getIdDono() == idDono) {
                lista.add(a);
            }
        }

        return lista;
    }

    public void atualizar(Animal atualizado) throws Exception {
        List<String[]> linhas;

        try (CSVReader reader = new CSVReader(new FileReader(FILE))) {
            linhas = reader.readAll();
        }

        for (int i = 1; i < linhas.size(); i++) {
            if (Integer.parseInt(linhas.get(i)[0]) == atualizado.getId()) {
                linhas.set(i, new String[]{
                        String.valueOf(atualizado.getId()),
                        String.valueOf(atualizado.getIdDono()),
                        atualizado.getNome(),
                        String.valueOf(atualizado.getIdade())
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
