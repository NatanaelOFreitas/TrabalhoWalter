package Backend.Service;

import Backend.Dono.Dono;
import Backend.Repository.DonoRepository;
import Backend.Utils.Utils;
import java.util.List;

public class DonoService {

    private DonoRepository donoRepo;

    public DonoService() {
        this.donoRepo = new DonoRepository();
    }

    public Dono cadastrarDono(String nome, String email, int numero, String senha) throws Exception {
        if (!Utils.isEmailValido(email)) {
            throw new IllegalArgumentException("Email inválido: " + email);
        }

        if (donoRepo.buscarPorEmail(email) != null) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        if (numero <= 0) {
            throw new IllegalArgumentException("Número deve ser positivo");
        }

        if (senha == null || senha.length() < 4) {
            throw new IllegalArgumentException("Senha deve ter pelo menos 4 caracteres");
        }

        Dono dono = new Dono(0, nome, email, numero, senha, "");
        donoRepo.salvar(dono);

        return dono;
    }

    public Dono login(String email, String senha) throws Exception {
        Dono dono = donoRepo.buscarPorEmail(email);

        if (dono == null) {
            throw new IllegalArgumentException("Email ou senha inválidos");
        }

        if (!dono.validarSenha(senha)) {
            throw new IllegalArgumentException("Email ou senha inválidos");
        }

        return dono;
    }

    public List<Dono> listarDonos() throws Exception {
        return donoRepo.listar();
    }

    public Dono buscarDono(int id) throws Exception {
        Dono dono = donoRepo.buscarPorId(id);
        if (dono == null) {
            throw new IllegalArgumentException("Dono com ID " + id + " não encontrado");
        }
        return dono;
    }

    public void atualizarDono(Dono dono) throws Exception {
        if (donoRepo.buscarPorId(dono.getId()) == null) {
            throw new IllegalArgumentException("Dono não encontrado");
        }

        if (!Utils.isEmailValido(dono.getEmail())) {
            throw new IllegalArgumentException("Email inválido");
        }

        Dono mesmoEmail = donoRepo.buscarPorEmail(dono.getEmail());
        if (mesmoEmail != null && mesmoEmail.getId() != dono.getId()) {
            throw new IllegalArgumentException("Email já está em uso");
        }

        donoRepo.atualizar(dono);
    }

    public void removerDono(int id) throws Exception {
        if (donoRepo.buscarPorId(id) == null) {
            throw new IllegalArgumentException("Dono não encontrado");
        }

        donoRepo.deletar(id);
    }

    public void alterarSenha(int idDono, String senhaAntiga, String senhaNova) throws Exception {
        Dono dono = donoRepo.buscarPorId(idDono);
        if (dono == null) {
            throw new IllegalArgumentException("Dono não encontrado");
        }

        if (!dono.validarSenha(senhaAntiga)) {
            throw new IllegalArgumentException("Senha atual incorreta");
        }

        if (senhaNova == null || senhaNova.length() < 4) {
            throw new IllegalArgumentException("Nova senha deve ter pelo menos 4 caracteres");
        }

        String novoSalt = Utils.gerarSalt();
        String novoHash = Utils.hashComSalt(senhaNova, novoSalt);

        dono.setSenha(novoHash);
        dono.setSalt(novoSalt);

        donoRepo.atualizar(dono);
    }
}
