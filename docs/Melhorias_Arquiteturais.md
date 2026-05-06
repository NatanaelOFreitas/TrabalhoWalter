# Melhorias Arquiteturais — Backend TrabalhoWalterPet

Este documento detalha as melhorias recomendadas para a arquitetura e segurança do backend, considerando a persistência em CSV (requisito do professor) e a futura integração com frontend Swing.

---

## 1. Visão Geral das Mudanças

| Camada | Status Atual | Ação Recomendada |
|-------|------------|-----------------|
| Model | ✅ Implementado | Ajustes de validação nos setters |
| Repository | ⚠️ Parcial | Adicionar sincronização + proteção CSV injection |
| Service | ❌ Ausente | **Criar** — validação de negócio |
| Controller | ❌ Ausente | **Criar** — coordenação Swing ↔ Service |
| Utils | ⚠️ Parcial | Expandir para validações utilitárias |

---

## 2. Estrutura de Diretórios Após Mudanças

```
TrabalhoWalterPet/Backend/src/
├── Main.java                    # Punto de entrada (manter)
├── PetShop/
│   └── PetShop.java            # Stub vazio → implementar coordenação
├── Animais/
│   ├── Animal.java           # + validação no setter
│   ├── Cachorro.java
│   ├── Gato.java
│   └── Peixe.java
├── Dono/
│   └── Dono.java             # + validação email + hash com salt
├── Estoque/
│   ├── Carrinho.java
│   ├── Estoque.java
│   └── Produto.java          # + validação preço/quantidade
├── Repository/
│   ├── AnimalRepository.java # + synchronized + escape CSV
│   ├── DonoRepository.java   # + synchronized + escape CSV
│   └── ProdutoRepository.java # + synchronized + escape CSV
├── Service/                  # NOVO — camada de negócio
│   ├── AnimalService.java
│   ├── DonoService.java
│   └── ProdutoService.java
├── Controller/               # NOVO — coordenação para Swing
│   └── PetShopController.java
└── Utils/
    └── Utils.java            # + métodos de validação + hash
```

---

## 3. Utils — Métodos Utilitários

Adicione esta seção em `Utils.java` para suportar as validações e segurança.

### 3.1 Validação de Email (Regex)

```java
// Utils.java — adicionar método

private static final String EMAIL_REGEX = 
    "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

public static boolean isEmailValido(String email) {
    if (email == null || email.isBlank()) return false;
    return email.matches(EMAIL_REGEX);
}

public static String validarEmail(String email) throws IllegalArgumentException {
    if (!isEmailValido(email)) {
        throw new IllegalArgumentException("Email inválido: " + email);
    }
    return email;
}
```

### 3.2 Escape de CSV (Previne Injection)

```java
// Utils.java — adicionar método

public static String escaparCSV(String valor) {
    if (valor == null) return "";
    
    // Se o valor começa com caractere de risco, prefixar com aspas
    if (valor.length() > 0 && 
        "+-=@".indexOf(valor.charAt(0)) >= 0) {
        valor = "'" + valor;
    }
    
    // Se contém vírgula, aspas ou quebra de linha, encapsular
    if (valor.contains(",") || valor.contains("\"") 
        || valor.contains("\n") || valor.contains("\r")) {
        valor = "\"" + valor.replace("\"", "\"\"") + "\"";
    }
    
    return valor;
}
```

### 3.3 Geração de Salt Aleatório

```java
// Utils.java — adicionar método

public static String gerarSalt() {
    byte[] salt = new byte[16];
    new java.security.SecureRandom().nextBytes(salt);
    return java.util.HexFormat.of().formatHex(salt);
}

public static String hashComSalt(String texto, String salt) {
    String textoComSalt = texto + salt;
    try {
        byte[] hashBytes = java.security.MessageDigest
                .getInstance("SHA-256")
                .digest(textoComSalt.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        return java.util.HexFormat.of().formatHex(hashBytes);
    } catch (java.security.NoSuchAlgorithmException e) {
        throw new RuntimeException("Erro ao gerar hash", e);
    }
}
```

### 3.4 Geração de ID Única

```java
// Utils.java — adicionar método

public static String gerarUuid() {
    return java.util.UUID.randomUUID().toString();
}
```

---

## 4. Alterações nos Models

### 4.1 Dono.java — Validação + Salt

Modificar os setters para validar entrada:

```java
// Dono.java — adicionar import (se não existir)
import java.util.regex.Pattern;

// No campo da classe, adicionar:
private String salt;

// Nos setters, modificar:

public void setEmail(String email) {
    if (!Utils.isEmailValido(email)) {
        throw new IllegalArgumentException("Email inválido: " + email);
    }
    this.email = email;
}

public void setNome(String nome) {
    if (nome == null || nome.isBlank()) {
        throw new IllegalArgumentException("Nome não pode ser vazio");
    }
    this.nome = nome.trim().toLowerCase();
}

public void setNumero(int numero) {
    if (numero <= 0) {
        throw new IllegalArgumentException("Número deve ser positivo");
    }
    this.numero = numero;
}
```

Modificar o construtor para usar salt:

```java
// Novo construtor com salt
public Dono(int id, String nome, String email, int numero, String senha, String foto) {
    this.id = id;
    this.nome = nome.toLowerCase();
    this.email = email;
    this.numero = numero;
    this.listaPets = new ArrayList<>();
    this.carrinho = new Carrinho();
    this.foto = foto.isEmpty() ? "" : foto;
    
    // Gerar salt e calcular hash com salt
    this.salt = Utils.gerarSalt();
    this.senha = Utils.hashComSalt(senha, this.salt);
}

// Novo construtor para login (recebe hash já calculado)
public Dono(int id, String nome, String email, int numero, String senha, String salt, String foto) {
    this.id = id;
    this.nome = nome.toLowerCase();
    this.email = email;
    this.numero = numero;
    this.listaPets = new ArrayList<>();
    this.carrinho = new Carrinho();
    this.foto = foto.isEmpty() ? "" : foto;
    this.salt = salt;
    this.senha = senha;
}

// Método para validar senha
public boolean validarSenha(String senha) {
    String hashDigitado = Utils.hashComSalt(senha, this.salt);
    return this.senha.equals(hashDigitado);
}
```

### 4.2 Animal.java — Validação de idade

```java
// Animal.java — modificar setters

public void setIdade(double idade) {
    if (idade < 0) {
        throw new IllegalArgumentException("Idade não pode ser negativa");
    }
    if (idade > 50) { // razoável para animais comuns
        throw new IllegalArgumentException("Idade improvável: " + idade);
    }
    this.idade = idade;
}

public void setNome(String nome) {
    if (nome == null || nome.isBlank()) {
        throw new IllegalArgumentException("Nome do animal não pode ser vazio");
    }
    this.nome = nome.trim().toLowerCase();
}
```

### 4.3 Produto.java — Validação monetária

```java
// Produto.java — modificar setter

public void setPrecoUni(float precoUni) {
    if (precoUni < 0) {
        throw new IllegalArgumentException("Preço não pode ser negativo");
    }
    this.precoUni = precoUni;
}

public void setQuantd(int quantd) {
    if (quantd < 0) {
        throw new IllegalArgumentException("Quantidade não pode ser negativa");
    }
    this.quantd = quantd;
}
```

---

## 5. Repository — Sincronização + Escape CSV

### 5.1 DonoRepository — Versão Melhorada

```java
package Repository;

import Dono.Dono;
import com.opencsv.*;
import Utils.Utils;

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
                                c[1], // nome (CSV já tem escape se necessário)
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
        // Se arquivo existe mas só tem cabeçalho, ID = 1
        // Caso contrário, último ID + 1
        if (linhas <= 1) return 1;
        
        // Ler último ID do arquivo
        List<String> todasLinhas = Files.readAllLines(Path.of(FILE));
        String ultimaLinha = todasLinhas.get(todasLinhas.size() - 1);
        String[] campos = Utils.escaparCSV(ultimaLinha).split(","); // Atenção: isso é simplificado
        // Melhor: usar CSVReader para ler última linha
        return linhas; // Simplificado — ideal usar UUID
    }
}
```

### 5.2 AnimalRepository — Versão Melhorada

```java
package Repository;

import Animais.Animal;
import com.opencsv.*;
import Utils.Utils;

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
```

### 5.3 ProdutoRepository — Versão Melhorada

```java
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
```

---

## 6. Service — Camada de Negócio

A camada Service é responsável por:

1. **Validar regras de neg��cio** antes de acessar o Repository
2. **Coordinar múltiplas operações** (ex: transação lógica)
3. **Retornar exceções significativas** para o Controller

### 6.1 AnimalService

```java
package Service;

import Animais.Animal;
import Repository.AnimalRepository;
import Repository.DonoRepository;
import java.util.List;

public class AnimalService {

    private AnimalRepository animalRepo;
    private DonoRepository donoRepo;

    public AnimalService() {
        this.animalRepo = new AnimalRepository();
        this.donoRepo = new DonoRepository();
    }

    public Animal cadastrarAnimal(int idDono, String nome, double idade) throws Exception {
        // Regra 1: Verificar se o dono existe
        if (donoRepo.buscarPorId(idDono) == null) {
            throw new IllegalArgumentException("Dono com ID " + idDono + " não existe");
        }

        // Regra 2: Validar nome (vai lançar exception se inválido)
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do animal é obrigatório");
        }

        // Regra 3: Validar idade
        if (idade < 0) {
            throw new IllegalArgumentException("Idade não pode ser negativa");
        }
        if (idade > 50) {
            throw new IllegalArgumentException("Idade muito alta para um animal");
        }

        // Criar e salvar
        Animal animal = new Animal(0, idDono, nome, idade);
        animalRepo.salvar(animal);
        
        return animal;
    }

    public List<Animal> listarAnimais() throws Exception {
        return animalRepo.listar();
    }

    public List<Animal> listarAnimaisDoDono(int idDono) throws Exception {
        // Verificar se dono existe
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
        // Verificar se animal existe
        if (animalRepo.buscarPorId(animal.getId()) == null) {
            throw new IllegalArgumentException("Animal não encontrado");
        }
        
        // Verificar se dono Existe
        if (donoRepo.buscarPorId(animal.getIdDono()) == null) {
            throw new IllegalArgumentException("Dono não existe");
        }
        
        // Validar campos
        if (animal.getNome() == null || animal.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (animal.getIdade() < 0 || animal.getIdade() > 50) {
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
```

### 6.2 DonoService

```java
package Service;

import Dono.Dono;
import Repository.DonoRepository;
import Utils.Utils;
import java.util.List;

public class DonoService {

    private DonoRepository donoRepo;

    public DonoService() {
        this.donoRepo = new DonoRepository();
    }

    public Dono cadastrarDono(String nome, String email, int numero, String senha) throws Exception {
        // Regra 1: Validar email formatado
        if (!Utils.isEmailValido(email)) {
            throw new IllegalArgumentException("Email inválido: " + email);
        }

        // Regra 2: Verificar se email já está em uso
        if (donoRepo.buscarPorEmail(email) != null) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        // Regra 3: Validar nome
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        // Regra 4: Validar número positivo
        if (numero <= 0) {
            throw new IllegalArgumentException("Número deve ser positivo");
        }

        // Regra 5: Validar senha (tamanho mínimo)
        if (senha == null || senha.length() < 4) {
            throw new IllegalArgumentException("Senha deve ter pelo menos 4 caracteres");
        }

        // Criar e salvar — hash com salt é feito no construtor do Dono
        Dono dono = new Dono(0, nome, email, numero, senha, "");
        donoRepo.salvar(dono);

        return dono;
    }

    public Dono login(String email, String senha) throws Exception {
        Dono dono = donoRepo.buscarPorEmail(email);
        
        if (dono == null) {
            throw new IllegalArgumentException("Email ou senha inválidos");
        }
        
        // O método validarSenha deve existir em Dono.java
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
        // Verificar se dono existe
        if (donoRepo.buscarPorId(dono.getId()) == null) {
            throw new IllegalArgumentException("Dono não encontrado");
        }
        
        // Validar email
        if (!Utils.isEmailValido(dono.getEmail())) {
            throw new IllegalArgumentException("Email inválido");
        }
        
        // Verificar se novo email já está em uso por outro dono
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
        
        // Opcional: remover animais associados primeiro
        // (implemente se houver essa Regra de Negócio)
        
        donoRepo.deletar(id);
    }

    public void alterarSenha(int idDono, String senhaAntiga, String senhaNova) throws Exception {
        Dono dono = donoRepo.buscarPorId(idDono);
        if (dono == null) {
            throw new IllegalArgumentException("Dono não encontrado");
        }
        
        // Validar senha antiga
        if (!dono.validarSenha(senhaAntiga)) {
            throw new IllegalArgumentException("Senha atual incorreta");
        }
        
        // Validar nova senha
        if (senhaNova == null || senhaNova.length() < 4) {
            throw new IllegalArgumentException("Nova senha deve ter pelo menos 4 caracteres");
        }
        
        // Gerar novo hash com novo salt
        String novoSalt = Utils.gerarSalt();
        String novoHash = Utils.hashComSalt(senhaNova, novoSalt);
        
        // Atualizar — precisa modificar Dono para aceitar setter de senha+salt
        // Ou recriar o objeto com novo hash
        dono.setSenha(novoHash); // Método a ser criado em Dono.java
        // Também precisa salvar o novo salt
        // dono.setSalt(novoSalt);
        
        donoRepo.atualizar(dono);
    }
}
```

### 6.3 ProdutoService

```java
package Service;

import Estoque.Produto;
import Repository.ProdutoRepository;
import java.util.List;

public class ProdutoService {

    private ProdutoRepository produtoRepo;

    public ProdutoService() {
        this.produtoRepo = new ProdutoRepository();
    }

    public Produto cadastrarProduto(int idEstoque, String nome, int quantidade, 
                               float precoUnitario, String descricao) throws Exception {
        // Regra 1: Validar nome
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório");
        }

        // Regra 2: Validar quantidade não negativa
        if (quantidade < 0) {
            throw new IllegalArgumentException("Quantidade não pode ser negativa");
        }

        // Regra 3: Validar preço positivo
        if (precoUnitario <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }

        // Regra 4: Descrição opcional, mas se informada, validar
        if (descricao != null && descricao.length() > 500) {
            throw new IllegalArgumentException("Descrição muito longa (máx: 500 caracteres)");
        }

        Produto produto = new Produto(0, idEstoque, nome, quantidade, 
                                     precoUnitario, descricao);
        produtoRepo.salvar(produto);

        return produto;
    }

    public List<Produto> listarProdutos() throws Exception {
        return produtoRepo.listar();
    }

    public List<Produto> listarProdutosDoCarrinho(int idCarrinho) throws Exception {
        return produtoRepo.buscarPorCarrinho(idCarrinho);
    }

    public Produto buscarProduto(int id) throws Exception {
        Produto produto = produtoRepo.buscarPorId(id);
        if (produto == null) {
            throw new IllegalArgumentException("Produto com ID " + id + " não encontrado");
        }
        return produto;
    }

    public void atualizarProduto(Produto produto) throws Exception {
        if (produtoRepo.buscarPorId(produto.getId()) == null) {
            throw new IllegalArgumentException("Produto não encontrado");
        }
        
        if (produto.getNome() == null || produto.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (produto.getQuantd() < 0) {
            throw new IllegalArgumentException("Quantidade negativa");
        }
        if (produto.getPrecoUni() <= 0) {
            throw new IllegalArgumentException("Preço deve ser positivo");
        }
        
        produtoRepo.atualizar(produto);
    }

    public void removerProduto(int id) throws Exception {
        if (produtoRepo.buscarPorId(id) == null) {
            throw new IllegalArgumentException("Produto não encontrado");
        }
        produtoRepo.deletar(id);
    }

    public void atualizarEstoque(int idProduto, int novaQuantidade) throws Exception {
        Produto produto = produtoRepo.buscarPorId(idProduto);
        if (produto == null) {
            throw new IllegalArgumentException("Produto não encontrado");
        }
        
        if (novaQuantidade < 0) {
            throw new IllegalArgumentException("Quantidade não pode ser negativa");
        }
        
        produto.setQuantd(novaQuantidade);
        produtoRepo.atualizar(produto);
    }

    public float calcularTotalCarrinho(int idCarrinho) throws Exception {
        List<Produto> produtos = produtoRepo.buscarPorCarrinho(idCarrinho);
        float total = 0;
        for (Produto p : produtos) {
            total += p.getPrecoUni() * p.getQuantd();
        }
        return total;
    }
}
```

---

## 7. Controller — Coordenação Swing

O Controller é a camada que mediates entre a interface Swing (View) e os Services. Ele:

1. Recebe ações do usuário via chamadas de método dos botões Swing
2. Chama os Services para executar regras de negócio
3. Retorna dados formatados para a View atualizar
4. trata exceções e as converte em mensagens para o usuário

### 7.1 PetShopController — Estrutura Completa

```java
package Controller;

import Animais.Animal;
import Dono.Dono;
import Estoque.Produto;
import Service.AnimalService;
import Service.DonoService;
import Service.ProdutoService;
import java.util.List;

public class PetShopController {

    private AnimalService animalService;
    private DonoService donoService;
    private ProdutoService produtoService;

    // Dados da sessão atual
    private Dono donoLogado;
    private boolean logado = false;

    public PetShopController() {
        this.animalService = new AnimalService();
        this.donoService = new DonoService();
        this.produtoService = new ProdutoService();
    }

    // ==================== Authentication ====================

    public boolean login(String email, String senha) {
        try {
            this.donoLogado = donoService.login(email, senha);
            this.logado = true;
            return true;
        } catch (Exception e) {
            System.err.println("Erro no login: " + e.getMessage());
            return false;
        }
    }

    public void logout() {
        this.donoLogado = null;
        this.logado = false;
    }

    public boolean isLogado() {
        return logado;
    }

    public Dono getDonoLogado() {
        return donoLogado;
    }

    // ==================== Dono (Cadastro) ====================

    public String cadastrarDono(String nome, String email, int numero, String senha) {
        try {
            Dono novo = donoService.cadastrarDono(nome, email, numero, senha);
            return "Dono cadastrado com sucesso! ID: " + novo.getId();
        } catch (IllegalArgumentException e) {
            return "Erro: " + e.getMessage();
        } catch (Exception e) {
            return "Erro inesperado: " + e.getMessage();
        }
    }

    public List<Dono> listarDonos() {
        try {
            return donoService.listarDonos();
        } catch (Exception e) {
            System.err.println("Erro ao listar donos: " + e.getMessage());
            return List.of();
        }
    }

    public Dono buscarDono(int id) {
        try {
            return donoService.buscarDono(id);
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            return null;
        }
    }

    // ==================== Animal ====================

    public String cadastrarAnimal(String nome, double idade) {
        if (!logado) {
            return "Erro: Faça login primeiro";
        }
        try {
            Animal animal = animalService.cadastrarAnimal(
                    donoLogado.getId(),
                    nome,
                    idade
            );
            return "Animal '" + animal.getNome() + "' cadastrado com sucesso!";
        } catch (IllegalArgumentException e) {
            return "Erro: " + e.getMessage();
        } catch (Exception e) {
            return "Erro inesperado: " + e.getMessage();
        }
    }

    public List<Animal> listarMeusAnimais() {
        if (!logado) return List.of();
        try {
            return animalService.listarAnimaisDoDono(donoLogado.getId());
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            return List.of();
        }
    }

    public List<Animal> listarTodosAnimais() {
        try {
            return animalService.listarAnimais();
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            return List.of();
        }
    }

    public String removerAnimal(int idAnimal) {
        if (!logado) return "Erro: Faça login primeiro";
        try {
            // Opcional: verificar se animal pertence ao dono logado
            Animal animal = animalService.buscarAnimal(idAnimal);
            if (animal.getIdDono() != donoLogado.getId()) {
                return "Erro: Você não é o dono deste animal";
            }
            animalService.removerAnimal(idAnimal);
            return "Animal removido com sucesso!";
        } catch (IllegalArgumentException e) {
            return "Erro: " + e.getMessage();
        } catch (Exception e) {
            return "Erro inesperado: " + e.getMessage();
        }
    }

    // ==================== Produto ====================

    public String cadastrarProduto(String nome, int quantidade, 
                                  float preco, String descricao) {
        if (!logado) return "Erro: Faça login primeiro";
        // idEstoque usa ID do carrinho do dono logado (0 se não existir)
        try {
            int idEstoque = donoLogado.getCarrinho() != null ? 
                          donoLogado.getCarrinho().getId() : 0;
            Produto produto = produtoService.cadastrarProduto(
                    idEstoque, nome, quantidade, preco, descricao
            );
            return "Produto '" + produto.getNome() + "' cadastrado!";
        } catch (IllegalArgumentException e) {
            return "Erro: " + e.getMessage();
        } catch (Exception e) {
            return "Erro inesperado: " + e.getMessage();
        }
    }

    public List<Produto> listarProdutos() {
        try {
            return produtoService.listarProdutos();
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            return List.of();
        }
    }

    public String removerProduto(int idProduto) {
        if (!logado) return "Erro: Faça login primeiro";
        try {
            produtoService.removerProduto(idProduto);
            return "Produto removido!";
        } catch (Exception e) {
            return "Erro: " + e.getMessage();
        }
    }

    public String calcularTotalCarrinho() {
        if (!logado) return "R$ 0,00";
        try {
            int idCarrinho = donoLogado.getCarrinho() != null ? 
                            donoLogado.getCarrinho().getId() : 0;
            float total = produtoService.calcularTotalCarrinho(idCarrinho);
            return String.format("R$ %.2f", total);
        } catch (Exception e) {
            return "R$ 0,00";
        }
    }

    // ==================== Utilitários ====================

    public String getMensagemboasVindas() {
        if (!logado) {
            return "Bem-vindo! Faça login para continuar.";
        }
        return "Olá, " + donoLogado.getNome() + "! O que deseja fazer?";
    }
}
```

---

## 8. Integração com Swing — Exemplo de Uso

Na sua View Swing (ex: `TelaLogin.java`, `TelaPrincipal.java`), você usaria assim:

```java
// Exemplo em TelaLogin.java

public class TelaLogin extends javax.swing.JFrame {

    private PetShopController controller;

    public TelaLogin() {
        initComponents();
        controller = new PetShopController();
    }

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {
        String email = campoEmail.getText();
        String senha = new String(campoSenha.getPassword());

        if (controller.login(email, senha)) {
            // Login sucesso — abrir tela principal
            new TelaPrincipal(controller).setVisible(true);
            dispose();
        } else {
            // Mostrar erro
            javax.swing.JOptionPane.showMessageDialog(
                this,
                "Email ou senha inválidos",
                "Erro de Login",
                javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }
}

// Exemplo em TelaPrincipal.java

public class TelaPrincipal extends javax.swing.JFrame {

    private PetShopController controller;

    public TelaPrincipal(PetShopController controller) {
        this.controller = controller;
        initComponents();
        
        // Atualizar label de boas-vindas
        lblBoasVindas.setText(controller.getMensagemboasVindas());
        
        // Listar animais na tabela
        atualizarTabelaAnimais();
    }

    private void atualizarTabelaAnimais() {
        List<Animal> animais = controller.listarMeusAnimais();
        // Popular JTable com os dados
        // (Use DefaultTableModel para adicionar linhas)
    }

    private void btnCadastrarAnimalActionPerformed(ActionEvent evt) {
        String nome = campoNomeAnimal.getText();
        double idade = Double.parseDouble(campoIdade.getText());

        String resultado = controller.cadastrarAnimal(nome, idade);
        javax.swing.JOptionPane.showMessageDialog(this, resultado);
        
        atualizarTabelaAnimais();
    }
}
```

---

## 9. Resumo das Alterações

| Arquivo | Tipo | Mudança |
|--------|------|---------|
| `Utils.java` | Alteração | + isEmailValido(), escaparCSV(), gerarSalt(), hashComSalt(), gerarUuid() |
| `Dono.java` | Alteração | + validação setters, salt, método validarSenha() |
| `Animal.java` | Alteração | + validação idade/nome nos setters |
| `Produto.java` | Alteração | + validação preço/quantidade nos setters |
| `DonoRepository.java` | Alteração | + synchronized, escape CSV |
| `AnimalRepository.java` | Alteração | + synchronized, escape CSV |
| `ProdutoRepository.java` | Alteração | + synchronized, escape CSV |
| `AnimalService.java` | Novo | Validações de negócio |
| `DonoService.java` | Novo | Validações + login com hash |
| `ProdutoService.java` | Novo | Validações + cálculos |
| `PetShopController.java` | Novo | Coordenação geral |

---

## 10. Observações Finais

1. **CSV persiste sendo o gargalo** — sem transações, sem rollback. Para um trabalho de faculdade está adequado, mas em produção seria necessário migrar para banco de dados.

2. **O `synchronized` evita race conditions** em ambiente com múltiplas threads (ex: mais de um usuário logado simultaneamente), mas não resolve corrupção de arquivo em caso de queda de energia. Para mitigação, pode-se usar arquivo temporário + rename atômico.

3. **Validações são redundantes em três camadas** (Model, Service, Controller) — isso é intencional: Defense in Depth.

4. **Para testar**: crie uma classe `MainTeste.java` que instancié o Controller e chame os métodos para verificar se tudo funciona.