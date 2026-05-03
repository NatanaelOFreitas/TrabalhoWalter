package Backend.Service;

import Backend.Animais.Animal;
import Backend.Repository.AnimalRepository;
import Backend.Repository.DonoRepository;
import java.util.List;

public class AnimalService {

    private AnimalRepository animalRepo;
    private DonoRepository donoRepo;

    public AnimalService() {
        this.animalRepo = new AnimalRepository();
        this.donoRepo = new DonoRepository();
    }

    public Animal cadastrarAnimal(int idDono, String nome, double idade) throws Exception {
        if (donoRepo.buscarPorId(idDono) == null) {
            throw new IllegalArgumentException("Dono com ID " + idDono + " não existe");
        }

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do animal é obrigatório");
        }

        if (idade < 0) {
            throw new IllegalArgumentException("Idade não pode ser negativa");
        }

        Animal animal = new Animal(0, idDono, nome, idade);
        animalRepo.salvar(animal);

        return animal;
    }

    public List<Animal> listarAnimais() throws Exception {
        return animalRepo.listar();
    }

    public List<Animal> listarAnimaisDoDono(int idDono) throws Exception {
        if (donoRepo.buscarPorId(idDono) == null) {
            throw new IllegalArgumentException("Dono com ID " + idDono + " não existe");
        }
        return animalRepo.buscarPorDono(idDono);
    }

    public Animal buscarAnimal(int id) throws Exception {
        Animal animal = animalRepo.buscarPorId(id);
        if (animal == null) {
            throw new IllegalArgumentException("Animal com ID " + id + " não encontrado");
        }
        return animal;
    }

    public void atualizarAnimal(Animal animal) throws Exception {
        if (animalRepo.buscarPorId(animal.getId()) == null) {
            throw new IllegalArgumentException("Animal não encontrado");
        }

        if (donoRepo.buscarPorId(animal.getIdDono()) == null) {
            throw new IllegalArgumentException("Dono não existe");
        }

        if (animal.getNome() == null || animal.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (animal.getIdade() < 0) {
            throw new IllegalArgumentException("Idade inválida");
        }

        animalRepo.atualizar(animal);
    }

    public void removerAnimal(int id) throws Exception {
        if (animalRepo.buscarPorId(id) == null) {
            throw new IllegalArgumentException("Animal não encontrado");
        }
        animalRepo.deletar(id);
    }
}