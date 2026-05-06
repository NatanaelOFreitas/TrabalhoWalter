# 🌟 Plano de Implementação - Interface Gráfica Pet Shop

## Visão Geral do Projeto

Este documento descreve passo a passo como criar uma interface gráfica moderna e bonita para o seu Pet Shop em Java. O projeto já tem um backend completo que gerencia:
- **Donos** (usuários) - cadastro, login, logout
- **Animais** (pets) - listar, adicionar, remover
- **Produtos** - catálogo, carrinho de compras
- **Persistência** - tudo salvo em arquivos CSV

---

## Tecnologias que Vamos Usar

| Tecnologia | Versão | Para que serve |
|------------|--------|----------------|
| Java | 21 | Linguagem principal |
| FlatLaf | 3.2 | Tema visual moderno (Dark) |
| Awesome Font | 1.0.3 | Íconesbonitos |

---

## Configuração do Projeto

### 1. Atualizar o pom.xml

Abra o arquivo `pom.xml` e adicione estas dependências dentro da tag `<dependencies>`:

```xml
<!-- FlatLaf - Tema moderno Dark -->
<dependency>
    <groupId>com.formdev</groupId>
    <artifactId>flatlaf</artifactId>
    <version>3.2</version>
</dependency>

<!-- Ícones Awesome Font -->
<dependency>
    <groupId>com.formdev</groupId>
    <artifactId>awesome-font</artifactId>
    <version>1.0.3</version>
</dependency>

<!-- Componentes extras do FlatLaf -->
<dependency>
    <groupId>com.formdev</groupId>
    <artifactId>flatlaf-extras</artifactId>
    <version>3.2</version>
</dependency>
```

**ATENÇÃO:** Após adicionar, clique com botão direito no projeto → Maven → Reload Project

---

## Paleta de Cores (Roxo Pastel Dark)

Vamos usar estas cores em todo o projeto:

```java
// Cores principais (defina em uma classe Theme.java)
public class Theme {
    // Fundos
    public static final Color BG_PRIMARY = new Color(0x1E, 0x1E, 0x2E);    // #1E1E2E - Fundo principal
    public static final Color BG_SECONDARY = new Color(0x2D, 0x2D, 0x44); // #2D2D44 - Cards/painéis
    public static final Color BG_TERTIARY = new Color(0x3D, 0x3D, 0x5C);  // #3D3D5C - Inputs/hover

    // Roxo Pastel (cor principal)
    public static final Color ACCENT = new Color(0xB7, 0x94, 0xF6);       // #B794F6 - Botões principais
    public static final Color ACCENT_HOVER = new Color(0x9F, 0x7A, 0xEA); // #9F7AEA - Botão pressionado

    // Textos
    public static final Color TEXT_PRIMARY = new Color(0xE8, 0xE8, 0xF0);  // #E8E8F0 - Texto principal
    public static final Color TEXT_SECONDARY = new Color(0xA0, 0xA0, 0xB8); // #A0A0B8 - Texto secundário

    // Feedback
    public static final Color SUCCESS = new Color(0x68, 0xD3, 0x91);     // #68D391 - Verde sucesso
    public static final Color ERROR = new Color(0xFC, 0x81, 0x81);        // #FC8181 - Vermelho erro

    // Bordas
    public static final Color BORDER = new Color(0x4A, 0x4A, 0x6A);      // #4A4A6A
}
```

---

## Estrutura de Arquivos da UI

Crie a seguinte estrutura de pastas dentro de `src`:

```
src/
├── Main.java                           (já existe - vamos modificar)
├── UI/
│   ├── App.java                        (classe main da UI)
│   ├── MainFrame.java                  (janela principal)
│   ├── Theme.java                      (cores do tema)
│   ├── Auth/
│   │   ├── LoginPanel.java             (tela de login)
│   │   └── RegisterPanel.java          (tela de cadastro)
│   ├── Dashboard/
│   │   ├── DashboardPanel.java         (menu principal)
│   │   └── Sidebar.java                (menu lateral)
│   ├── Pets/
│   │   ├── PetsPanel.java              (gerenciar pets)
│   │   ├── AddPetDialog.java           (adicionar pet)
│   │   └── PetCard.java                (card visual do pet)
│   ├── Products/
│   │   ├── ProductsPanel.java          (catálogo produtos)
│   │   ├── ProductCard.java            (card do produto)
│   │   └── AddProductDialog.java       (adicionar produto)
│   ├── Cart/
│   │   └── CartPanel.java              (carrinho)
│   └── Components/
│       ├── ModernButton.java           (botão estilizado)
│       ├── ModernTextField.java       (campo de texto estilizado)
│       ├── RoundedPanel.java          (painel arredondado)
│       └── ToastNotification.java     (mensagem de feedback)
```

---

## Passo a Passo da Implementação

### Passo 1: Classe Theme.java

Esta classe define todas as cores do projeto. É como a "receita de cores" que todos os outros componentes vão usar.

```java
package UI;

import java.awt.*;

public class Theme {
    // Fundos
    public static final Color BG_PRIMARY = new Color(30, 30, 46);       // #1E1E2E
    public static final Color BG_SECONDARY = new Color(45, 45, 68);     // #2D2D44
    public static final Color BG_TERTIARY = new Color(61, 61, 92);      // #3D3D5C

    // Roxo Pastel - Cor principal do app
    public static final Color ACCENT = new Color(183, 148, 246);        // #B794F6
    public static final Color ACCENT_HOVER = new Color(159, 122, 234);  // #9F7AEA
    public static final Color ACCENT_PRESSED = new Color(139, 92, 246); // #8B5CF6

    // Textos
    public static final Color TEXT_PRIMARY = new Color(232, 232, 240);  // #E8E8F0
    public static final Color TEXT_SECONDARY = new Color(160, 160, 184); // #A0A0B8

    // Feedback
    public static final Color SUCCESS = new Color(104, 211, 145);       // #68D391
    public static final Color ERROR = new Color(252, 129, 129);         // #FC8181

    // Bordas
    public static final Color BORDER = new Color(74, 74, 106);          // #4A4A6A

    // Fontes
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 12);
}
```

---

### Passo 2: ModernButton.java

Um botão customizado que segue o tema roxo pastel. Substitui o botão padrão feio do Java.

```java
package UI.Components;

import UI.Theme;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ModernButton extends JButton {

    private Color currentColor = Theme.ACCENT;
    private boolean isHovering = false;

    public ModernButton(String text) {
        super(text);
        setupButton();
    }

    private void setupButton() {
        setContentAreaPainted(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);

        setForeground(Theme.TEXT_PRIMARY);
        setFont(Theme.FONT_BODY);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(120, 40));

        // Efeito hover
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovering = true;
                currentColor = Theme.ACCENT_HOVER;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovering = false;
                currentColor = Theme.ACCENT;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, true);

        // Fundo com bordas arredondadas
        g2.setColor(currentColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

        // Texto centralizado
        FontMetrics fm = g2.getFontMetrics();
        String text = getText();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() + textHeight) / 2 - 2;

        g2.setColor(Theme.TEXT_PRIMARY);
        g2.setFont(getFont());
        g2.drawString(text, x, y);
    }

    // Método para botão secundário (sem cor de fundo)
    public void setSecondary() {
        currentColor = Theme.BG_TERTIARY;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                currentColor = Theme.BORDER;
                repaint();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                currentColor = Theme.BG_TERTIARY;
                repaint();
            }
        });
    }
}
```

---

### Passo 3: ModernTextField.java

Campo de texto estilizado com ícone e placeholder.

```java
package UI.Components;

import UI.Theme;
import javax.swing.*;
import java.awt.*;

public class ModernTextField extends JPanel {
    private JTextField textField;
    private String placeholder;
    private Icon icon;

    public ModernTextField(String placeholder, Icon icon) {
        this.placeholder = placeholder;
        this.icon = icon;
        setupField();
    }

    public ModernTextField(String placeholder) {
        this(placeholder, null);
    }

    private void setupField() {
        setLayout(new BorderLayout(10, 0));
        setBackground(Theme.BG_TERTIARY);
        setPreferredSize(new Dimension(250, 45));
        setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        textField = new JTextField() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty() && !isFocusOwner()) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setColor(Theme.TEXT_SECONDARY);
                    g2.setFont(Theme.FONT_BODY);
                    g2.drawString(placeholder, 0, g.getFontMetrics().getHeight());
                }
            }
        };

        textField.setBackground(Theme.BG_TERTIARY);
        textField.setForeground(Theme.TEXT_PRIMARY);
        textField.setCaretColor(Theme.ACCENT);
        textField.setBorder(null);
        textField.setFont(Theme.FONT_BODY);

        if (icon != null) {
            JLabel iconLabel = new JLabel(icon);
            iconLabel.setForeground(Theme.TEXT_SECONDARY);
            add(iconLabel, BorderLayout.WEST);
        }

        add(textField, BorderLayout.CENTER);
    }

    public String getText() {
        return textField.getText();
    }

    public void setText(String text) {
        textField.setText(text);
    }

    public void setEchoChar(char c) {
        textField.setEchoChar(c);
    }
}
```

---

### Passo 4: RoundedPanel.java

Panel com bordas arredondadas para usar como cards.

```java
package UI.Components;

import UI.Theme;
import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {
    private int cornerRadius = 15;
    private Color backgroundColor = Theme.BG_SECONDARY;

    public RoundedPanel() {
        super();
        setupPanel();
    }

    public RoundedPanel(int radius) {
        super();
        this.cornerRadius = radius;
        setupPanel();
    }

    private void setupPanel() {
        setOpaque(false);
        setBackground(Theme.BG_SECONDARY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, true);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        super.paintComponent(g2);
    }
}
```

---

### Passo 5: ToastNotification.java

Mensagem temporária de feedback (sucesso/erro).

```java
package UI.Components;

import UI.Theme;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ToastNotification extends JWindow {
    private JLabel messageLabel;
    private Timer timer;

    public ToastNotification(JFrame parent, String message, boolean isError) {
        setSize(300, 50);
        setLocationRelativeTo(parent);

        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(isError ? Theme.ERROR : Theme.SUCCESS);
        container.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        messageLabel = new JLabel(message);
        messageLabel.setForeground(Theme.BG_PRIMARY);
        messageLabel.setFont(Theme.FONT_BODY);

        container.add(messageLabel, BorderLayout.CENTER);
        add(container);

        // Clique para fechar
        container.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                close();
            }
        });

        // Auto-fechar após 3 segundos
        timer = new Timer(3000, e -> close());
        timer.setRepeats(false);
    }

    public void show() {
        setVisible(true);
        timer.start();
    }

    private void close() {
        timer.stop();
        setVisible(false);
        dispose();
    }
}
```

---

### Passo 6: MainFrame.java

A janela principal que vai gerenciar todas as telas.

```java
package UI;

import PetShopController.PetShopController;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private PetShopController controller;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // Referências para as telas
    private LoginPanel loginPanel;
    private RegisterPanel registerPanel;
    private DashboardPanel dashboardPanel;

    public MainFrame() {
        // Configurações básicas
        setTitle("Pet Shop Walter");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicializa o controller
        controller = new PetShopController();

        // Configura o tema escuro
        setupTheme();

        // Inicializa os componentes
        initComponents();

        // Mostra a tela inicial (login)
        showLogin();
    }

    private void setupTheme() {
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        // Layout de tarjetas para trocar entre telas
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(Theme.BG_PRIMARY);

        // Cria as telas
        loginPanel = new LoginPanel(this);
        registerPanel = new RegisterPanel(this);
        dashboardPanel = new DashboardPanel(this);

        // Adiciona ao painel principal
        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(registerPanel, "REGISTER");
        mainPanel.add(dashboardPanel, "DASHBOARD");

        add(mainPanel);
    }

    // Métodos para navegar entre telas
    public void showLogin() {
        cardLayout.show(mainPanel, "LOGIN");
    }

    public void showRegister() {
        cardLayout.show(mainPanel, "REGISTER");
    }

    public void showDashboard() {
        dashboardPanel.atualizarDados();
        cardLayout.show(mainPanel, "DASHBOARD");
    }

    //Getter do controller
    public PetShopController getController() {
        return controller;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
```

---

### Passo 7: LoginPanel.java

Tela de login com email e senha.

```java
package UI.Auth;

import UI.*;
import UI.Components.*;
import PetShopController.PetShopController;
import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    private MainFrame mainFrame;
    private ModernTextField emailField;
    private ModernTextField senhaField;
    private ModernButton loginButton;
    private JLabel criarContaLabel;

    public LoginPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setupPanel();
        initComponents();
    }

    private void setupPanel() {
        setBackground(Theme.BG_PRIMARY);
        setLayout(new GridBagLayout());
    }

    private void initComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Título
        JLabel titleLabel = new JLabel("🐾 Pet Shop Walter");
        titleLabel.setFont(Theme.FONT_TITLE);
        titleLabel.setForeground(Theme.ACCENT);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // Subtítulo
        JLabel subtitleLabel = new JLabel("Faça login para continuar");
        subtitleLabel.setFont(Theme.FONT_BODY);
        subtitleLabel.setForeground(Theme.TEXT_SECONDARY);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 30, 10);
        add(subtitleLabel, gbc);

        // Campos de input
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = 1;

        // Email
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Email: "), gbc);

        emailField = new ModernTextField("seu@email.com");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(emailField, gbc);

        // Senha
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Senha: "), gbc);

        senhaField = new ModernTextField("********");
        senhaField.setEchoChar('*');
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(senhaField, gbc);

        // Botão Login
        loginButton = new ModernButton("Entrar");
        loginButton.addActionListener(e -> realizarLogin());

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 10, 10, 10);
        add(loginButton, gbc);

        // Link para criar conta
        criarContaLabel = new JLabel("<html><u>Não tem conta? Cadastre-se</u></html>");
        criarContaLabel.setForeground(Theme.ACCENT);
        criarContaLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        criarContaLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                mainFrame.showRegister();
            }
        });

        gbc.gridy = 5;
        gbc.insets = new Insets(10, 10, 30, 10);
        add(criarContaLabel, gbc);
    }

    private void realizarLogin() {
        String email = emailField.getText().trim();
        String senha = senhaField.getText();

        if (email.isEmpty() || senha.isEmpty()) {
            showToast("Preencha todos os campos!", true);
            return;
        }

        PetShopController controller = mainFrame.getController();

        if (controller.login(email, senha)) {
            showToast("Login realizado com sucesso!", false);
            mainFrame.showDashboard();
        } else {
            showToast("Email ou senha inválidos!", true);
        }
    }

    private void showToast(String message, boolean isError) {
        //ToastNotification toast = new ToastNotification((JFrame) SwingUtilities.getWindowAncestor(this), message, isError);
        //toast.show();
        JOptionPane.showMessageDialog(this, message, isError ? "Erro" : "Sucesso",
            isError ? JOptionPane.ERROR_MESSAGE : JOptionPane.INFORMATION_MESSAGE);
    }
}
```

---

### Passo 8: RegisterPanel.java

Tela de cadastro de novo usuário (dono).

```java
package UI.Auth;

import UI.*;
import UI.Components.*;
import PetShopController.PetShopController;
import javax.swing.*;
import java.awt.*;

public class RegisterPanel extends JPanel {

    private MainFrame mainFrame;
    private ModernTextField nomeField;
    private ModernTextField emailField;
    private ModernTextField telefoneField;
    private ModernTextField senhaField;
    private ModernTextField confirmarSenhaField;
    private ModernButton cadastrarButton;
    private JLabel loginLabel;

    public RegisterPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setupPanel();
        initComponents();
    }

    private void setupPanel() {
        setBackground(Theme.BG_PRIMARY);
        setLayout(new GridBagLayout());
    }

    private void initComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);

        // Título
        JLabel titleLabel = new JLabel("🐾 Criar Conta");
        titleLabel.setFont(Theme.FONT_TITLE);
        titleLabel.setForeground(Theme.ACCENT);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;

        // Nome
        gbc.gridy = 1;
        add(new JLabel("Nome: "), gbc);
        nomeField = new ModernTextField("Seu nome completo");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(nomeField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Email: "), gbc);
        emailField = new ModernTextField("seu@email.com");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(emailField, gbc);

        // Telefone
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Telefone: "), gbc);
        telefoneField = new ModernTextField("(00) 00000-0000");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(telefoneField, gbc);

        // Senha
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Senha: "), gbc);
        senhaField = new ModernTextField("Mínimo 4 caracteres");
        senhaField.setEchoChar('*');
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(senhaField, gbc);

        // Confirmar Senha
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Confirmar: "), gbc);
        confirmarSenhaField = new ModernTextField("Digite a senha novamente");
        confirmarSenhaField.setEchoChar('*');
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(confirmarSenhaField, gbc);

        // Botão
        cadastrarButton = new ModernButton("Cadastrar");
        cadastrarButton.addActionListener(e -> realizarCadastro());

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 10, 10);
        add(cadastrarButton, gbc);

        // Link para login
        loginLabel = new JLabel("<html><u>Já tem conta? Entre aqui</u></html>");
        loginLabel.setForeground(Theme.ACCENT);
        loginLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                mainFrame.showLogin();
            }
        });

        gbc.gridy = 7;
        gbc.insets = new Insets(10, 10, 20, 10);
        add(loginLabel, gbc);
    }

    private void realizarCadastro() {
        String nome = nomeField.getText().trim();
        String email = emailField.getText().trim();
        String telefone = telefoneField.getText().trim();
        String senha = senhaField.getText();
        String confirmarSenha = confirmarSenhaField.getText();

        // Validações
        if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            JOptionPane.showMessageDialog(this, "As senhas não coincidem!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (senha.length() < 4) {
            JOptionPane.showMessageDialog(this, "A senha deve ter pelo menos 4 caracteres!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Converte telefone para int (simplificado)
            int telefoneNumero = Integer.parseInt(telefone.replaceAll("[^0-9]", ""));

            PetShopController controller = mainFrame.getController();
            String resultado = controller.cadastrarDono(nome, email, telefoneNumero, senha);

            JOptionPane.showMessageDialog(this, resultado, "Cadastro", JOptionPane.INFORMATION_MESSAGE);

            if (resultado.contains("sucesso")) {
                mainFrame.showLogin();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
```

---

### Passo 9: DashboardPanel.java

O menu principal após o login, com sidebar para navegar entre pets, produtos e carrinho.

```java
package UI.Dashboard;

import UI.*;
import UI.Components.*;
import UI.Pets.*;
import UI.Products.*;
import UI.Cart.*;
import PetShopController.PetShopController;
import Dono.Dono;
import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {

    private MainFrame mainFrame;
    private CardLayout contentLayout;
    private JPanel contentPanel;
    private JLabel usuarioLabel;
    private JLabel logoutLabel;

    // Sub-painéis
    private PetsPanel petsPanel;
    private ProductsPanel productsPanel;
    private CartPanel cartPanel;

    // Botões da sidebar
    private JButton petsBtn, productsBtn, cartBtn;

    public DashboardPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setupPanel();
        initComponents();
        initSubPanels();
    }

    private void setupPanel() {
        setBackground(Theme.BG_PRIMARY);
        setLayout(new BorderLayout());
    }

    private void initComponents() {
        // ===== TOPO =====
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Theme.BG_SECONDARY);
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        usuarioLabel = new JLabel("Olá, Visitante!");
        usuarioLabel.setFont(Theme.FONT_SUBTITLE);
        usuarioLabel.setForeground(Theme.TEXT_PRIMARY);

        logoutLabel = new JLabel("🚪 Sair");
        logoutLabel.setForeground(Theme.ERROR);
        logoutLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                mainFrame.getController().logout();
                mainFrame.showLogin();
            }
        });

        topPanel.add(usuarioLabel, BorderLayout.WEST);
        topPanel.add(logoutLabel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // ===== SIDEBAR =====
        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(Theme.BG_SECONDARY);
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        // Logo
        JLabel logoLabel = new JLabel("🐾");
        logoLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidePanel.add(logoLabel);
        sidePanel.add(Box.createVerticalStrut(30));

        // Botões de navegação
        petsBtn = createNavButton("🐶 Meus Pets");
        productsBtn = createNavButton("📦 Produtos");
        cartBtn = createNavButton("🛒 Carrinho");

        petsBtn.addActionListener(e -> showContent("PETS"));
        productsBtn.addActionListener(e -> showContent("PRODUCTS"));
        cartBtn.addActionListener(e -> showContent("CART"));

        sidePanel.add(petsBtn);
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(productsBtn);
        sidePanel.add(Box.createVerticalStrut(10));
        sidePanel.add(cartBtn);

        add(sidePanel, BorderLayout.WEST);

        // ===== CONTEÚDO =====
        contentLayout = new CardLayout();
        contentPanel = new JPanel(contentLayout);
        contentPanel.setBackground(Theme.BG_PRIMARY);

        add(contentPanel, BorderLayout.CENTER);
    }

    private JButton createNavButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(Theme.FONT_BODY);
        btn.setForeground(Theme.TEXT_PRIMARY);
        btn.setBackground(Theme.BG_TERTIARY);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setPreferredSize(new Dimension(150, 45));
        btn.setMaximumSize(new Dimension(150, 45));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void initSubPanels() {
        petsPanel = new PetsPanel(mainFrame);
        productsPanel = new ProductsPanel(mainFrame);
        cartPanel = new CartPanel(mainFrame);

        contentPanel.add(petsPanel, "PETS");
        contentPanel.add(productsPanel, "PRODUCTS");
        contentPanel.add(cartPanel, "CART");
    }

    private void showContent(String panelName) {
        contentLayout.show(contentPanel, panelName);

        // Atualiza dados se necessário
        if (panelName.equals("CART")) {
            cartPanel.atualizarDados();
        }
    }

    public void atualizarDados() {
        PetShopController controller = mainFrame.getController();
        Dono dono = controller.getDonoLogado();

        if (dono != null) {
            usuarioLabel.setText("Olá, " + dono.getNome() + "!");
        }

        petsPanel.atualizarDados();
    }
}
```

---

### Passo 10: PetsPanel.java

Painel para gerenciar os pets do usuário logado.

```java
package UI.Pets;

import UI.*;
import UI.Components.*;
import PetShopController.PetShopController;
import Animais.Animal;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PetsPanel extends JPanel {

    private MainFrame mainFrame;
    private JPanel petsContainer;
    private JScrollPane scrollPane;
    private ModernButton addButton;

    public PetsPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setupPanel();
        initComponents();
        atualizarDados();
    }

    private void setupPanel() {
        setBackground(Theme.BG_PRIMARY);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    private void initComponents() {
        // Título e botão adicionar
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("🐶 Meus Pets");
        titleLabel.setFont(Theme.FONT_TITLE);
        titleLabel.setForeground(Theme.TEXT_PRIMARY);

        addButton = new ModernButton("+ Adicionar Pet");
        addButton.addActionListener(e -> mostrarDialogAdicionar());

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(addButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Container dos pets (Grid)
        petsContainer = new JPanel(new GridLayout(0, 3, 15, 15));
        petsContainer.setOpaque(false);

        scrollPane = new JScrollPane(petsContainer);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        add(scrollPane, BorderLayout.CENTER);
    }

    public void atualizarDados() {
        petsContainer.removeAll();

        PetShopController controller = mainFrame.getController();
        List<Animal> pets = controller.listarMeusAnimais();

        if (pets.isEmpty()) {
            JLabel emptyLabel = new JLabel("Você ainda não tem pets cadastrados!");
            emptyLabel.setFont(Theme.FONT_BODY);
            emptyLabel.setForeground(Theme.TEXT_SECONDARY);
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            petsContainer.add(emptyLabel);
        } else {
            for (Animal pet : pets) {
                petsContainer.add(new PetCard(pet, this));
            }
        }

        petsContainer.revalidate();
        petsContainer.repaint();
    }

    private void mostrarDialogAdicionar() {
        AddPetDialog dialog = new AddPetDialog((JFrame) SwingUtilities.getWindowAncestor(this), mainFrame);
        dialog.setVisible(true);
        atualizarDados();
    }

    public void removerPet(Animal pet) {
        int resposta = JOptionPane.showConfirmDialog(this,
            "Tem certeza que deseja remover o pet " + pet.getNome() + "?",
            "Confirmar", JOptionPane.YES_NO_OPTION);

        if (resposta == JOptionPane.YES_OPTION) {
            PetShopController controller = mainFrame.getController();
            String resultado = controller.removerAnimal(pet.getId());
            JOptionPane.showMessageDialog(this, resultado);
            atualizarDados();
        }
    }
}
```

---

### Passo 11: PetCard.java

Card visual que representa um pet na lista.

```java
package UI.Pets;

import UI.Theme;
import UI.Components.*;
import Animais.Animal;
import javax.swing.*;
import java.awt.*;

public class PetCard extends RoundedPanel {

    private Animal pet;
    private PetsPanel parentPanel;

    public PetCard(Animal pet, PetsPanel parentPanel) {
        super(15);
        this.pet = pet;
        this.parentPanel = parentPanel;
        setupCard();
    }

    private void setupCard() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Theme.BG_SECONDARY);
        setPreferredSize(new Dimension(180, 150));

        // Ícone do pet (🐶, 🐱, 🐠)
        String emoji = getEmojiPet();
        JLabel emojiLabel = new JLabel(emoji, SwingConstants.CENTER);
        emojiLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        emojiLabel.setForeground(Theme.ACCENT);

        // Nome do pet
        JLabel nomeLabel = new JLabel(pet.getNome());
        nomeLabel.setFont(Theme.FONT_SUBTITLE);
        nomeLabel.setForeground(Theme.TEXT_PRIMARY);
        nomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Idade
        JLabel idadeLabel = new JLabel(String.format("%.1f anos", pet.getIdade()));
        idadeLabel.setFont(Theme.FONT_BODY);
        idadeLabel.setForeground(Theme.TEXT_SECONDARY);
        idadeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Botão remover
        JButton removerBtn = new JButton("🗑️");
        removerBtn.setFocusPainted(false);
        removerBtn.setBackground(Theme.ERROR);
        removerBtn.setForeground(Theme.TEXT_PRIMARY);
        removerBtn.addActionListener(e -> parentPanel.removerPet(pet));

        add(emojiLabel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(nomeLabel);
        infoPanel.add(idadeLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(removerBtn);

        add(infoPanel, BorderLayout.CENTER);
    }

    private String getEmojiPet() {
        // Por enquanto retorna um emoji genérico
        // Você pode estender para verificar o tipo (Cachorro, Gato, Peixe)
        return "🐾";
    }
}
```

---

### Passo 12: AddPetDialog.java

Dialog para adicionar um novo pet.

```java
package UI.Pets;

import UI.*;
import UI.Components.*;
import PetShopController.PetShopController;
import javax.swing.*;
import java.awt.*;

public class AddPetDialog extends JDialog {

    private MainFrame mainFrame;
    private ModernTextField nomeField;
    private ModernTextField idadeField;
    private ModernButton salvarButton;
    private ModernButton cancelarButton;

    public AddPetDialog(JFrame parent, MainFrame mainFrame) {
        super(parent, "Adicionar Pet", true);
        this.mainFrame = mainFrame;
        setupDialog();
        initComponents();
    }

    private void setupDialog() {
        setSize(400, 250);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Theme.BG_PRIMARY);
        setUndecorated(true);
    }

    private void initComponents() {
        // Container com bordas arredondadas
        RoundedPanel container = new RoundedPanel(20);
        container.setLayout(new GridBagLayout());
        container.setBackground(Theme.BG_SECONDARY);
        container.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel titleLabel = new JLabel("➕ Adicionar Novo Pet");
        titleLabel.setFont(Theme.FONT_SUBTITLE);
        titleLabel.setForeground(Theme.ACCENT);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        container.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;

        // Nome
        gbc.gridy = 1;
        add(new JLabel("Nome:"), gbc);

        nomeField = new ModernTextField("Nome do pet");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        container.add(nomeField, gbc);

        // Idade
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Idade:"), gbc);

        idadeField = new ModernTextField("Ex: 2.5");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        container.add(idadeField, gbc);

        // Botões
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonsPanel.setOpaque(false);

        salvarButton = new ModernButton("Salvar");
        salvarButton.addActionListener(e -> salvarPet());

        cancelarButton = new ModernButton("Cancelar");
        cancelarButton.setSecondary();
        cancelarButton.addActionListener(e -> dispose());

        buttonsPanel.add(salvarButton);
        buttonsPanel.add(cancelarButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        container.add(buttonsPanel, gbc);

        add(container, BorderLayout.CENTER);
    }

    private void salvarPet() {
        String nome = nomeField.getText().trim();
        String idadeStr = idadeField.getText().trim();

        if (nome.isEmpty() || idadeStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double idade = Double.parseDouble(idadeStr);
            PetShopController controller = mainFrame.getController();
            String resultado = controller.cadastrarAnimal(nome, idade);

            JOptionPane.showMessageDialog(this, resultado, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Idade inválida! Digite um número.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
```

---

### Passo 13: ProductsPanel.java

Painel para visualizar e adicionar produtos.

```java
package UI.Products;

import UI.*;
import UI.Components.*;
import PetShopController.PetShopController;
import Estoque.Produto;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProductsPanel extends JPanel {

    private MainFrame mainFrame;
    private JPanel productsContainer;
    private JScrollPane scrollPane;
    private ModernButton addButton;

    public ProductsPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setupPanel();
        initComponents();
        atualizarDados();
    }

    private void setupPanel() {
        setBackground(Theme.BG_PRIMARY);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    private void initComponents() {
        // Título e botão adicionar
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("📦 Produtos");
        titleLabel.setFont(Theme.FONT_TITLE);
        titleLabel.setForeground(Theme.TEXT_PRIMARY);

        addButton = new ModernButton("+ Novo Produto");
        addButton.addActionListener(e -> mostrarDialogAdicionar());

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(addButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Container dos produtos (Grid)
        productsContainer = new JPanel(new GridLayout(0, 4, 15, 15));
        productsContainer.setOpaque(false);

        scrollPane = new JScrollPane(productsContainer);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        add(scrollPane, BorderLayout.CENTER);
    }

    public void atualizarDados() {
        productsContainer.removeAll();

        PetShopController controller = mainFrame.getController();
        List<Produto> produtos = controller.listarProdutos();

        if (produtos.isEmpty()) {
            JLabel emptyLabel = new JLabel("Nenhum produto cadastrado!");
            emptyLabel.setFont(Theme.FONT_BODY);
            emptyLabel.setForeground(Theme.TEXT_SECONDARY);
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            productsContainer.add(emptyLabel);
        } else {
            for (Produto produto : produtos) {
                productsContainer.add(new ProductCard(produto));
            }
        }

        productsContainer.revalidate();
        productsContainer.repaint();
    }

    private void mostrarDialogAdicionar() {
        AddProductDialog dialog = new AddProductDialog((JFrame) SwingUtilities.getWindowAncestor(this), mainFrame);
        dialog.setVisible(true);
        atualizarDados();
    }
}
```

---

### Passo 14: ProductCard.java

Card visual de produto.

```java
package UI.Products;

import UI.Theme;
import UI.Components.*;
import Estoque.Produto;
import javax.swing.*;
import java.awt.*;

public class ProductCard extends RoundedPanel {

    private Produto produto;

    public ProductCard(Produto produto) {
        super(15);
        this.produto = produto;
        setupCard();
    }

    private void setupCard() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Theme.BG_SECONDARY);
        setPreferredSize(new Dimension(180, 180));

        // Ícone
        JLabel emojiLabel = new JLabel("📦", SwingConstants.CENTER);
        emojiLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        emojiLabel.setForeground(Theme.ACCENT);

        // Nome
        JLabel nomeLabel = new JLabel(produto.getNome());
        nomeLabel.setFont(Theme.FONT_SUBTITLE);
        nomeLabel.setForeground(Theme.TEXT_PRIMARY);
        nomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Preço
        JLabel precoLabel = new JLabel(String.format("R$ %.2f", produto.getPrecoUni()));
        precoLabel.setFont(Theme.FONT_BODY);
        precoLabel.setForeground(Theme.SUCCESS);
        precoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Estoque
        JLabel estoqueLabel = new JLabel("Estoque: " + produto.getQuantd());
        estoqueLabel.setFont(Theme.FONT_SMALL);
        estoqueLabel.setForeground(Theme.TEXT_SECONDARY);
        estoqueLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Descrição (se houver)
        String desc = produto.getDescricao();
        JLabel descLabel = new JLabel(desc.isEmpty() ? "" : desc);
        descLabel.setFont(Theme.FONT_SMALL);
        descLabel.setForeground(Theme.TEXT_SECONDARY);
        descLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(emojiLabel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(nomeLabel);
        infoPanel.add(precoLabel);
        infoPanel.add(estoqueLabel);
        if (!desc.isEmpty()) {
            infoPanel.add(descLabel);
        }

        add(infoPanel, BorderLayout.CENTER);
    }
}
```

---

### Passo 15: AddProductDialog.java

Dialog para adicionar novo produto.

```java
package UI.Products;

import UI.*;
import UI.Components.*;
import PetShopController.PetShopController;
import javax.swing.*;
import java.awt.*;

public class AddProductDialog extends JDialog {

    private MainFrame mainFrame;
    private ModernTextField nomeField;
    private ModernTextField quantidadeField;
    private ModernTextField precoField;
    private ModernTextField descricaoField;
    private ModernButton salvarButton;
    private ModernButton cancelarButton;

    public AddProductDialog(JFrame parent, MainFrame mainFrame) {
        super(parent, "Adicionar Produto", true);
        this.mainFrame = mainFrame;
        setupDialog();
        initComponents();
    }

    private void setupDialog() {
        setSize(450, 350);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Theme.BG_PRIMARY);
        setUndecorated(true);
    }

    private void initComponents() {
        RoundedPanel container = new RoundedPanel(20);
        container.setLayout(new GridBagLayout());
        container.setBackground(Theme.BG_SECONDARY);
        container.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("📦 Adicionar Produto");
        titleLabel.setFont(Theme.FONT_SUBTITLE);
        titleLabel.setForeground(Theme.ACCENT);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        container.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;

        // Nome
        gbc.gridy = 1;
        add(new JLabel("Nome:"), gbc);
        nomeField = new ModernTextField("Nome do produto");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        container.add(nomeField, gbc);

        // Quantidade
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Quantidade:"), gbc);
        quantidadeField = new ModernTextField("Ex: 10");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        container.add(quantidadeField, gbc);

        // Preço
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Preço (R$):"), gbc);
        precoField = new ModernTextField("Ex: 29.90");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        container.add(precoField, gbc);

        // Descrição
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Descrição:"), gbc);
        descricaoField = new ModernTextField("Descrição opcional");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        container.add(descricaoField, gbc);

        // Botões
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonsPanel.setOpaque(false);

        salvarButton = new ModernButton("Salvar");
        salvarButton.addActionListener(e -> salvarProduto());

        cancelarButton = new ModernButton("Cancelar");
        cancelarButton.setSecondary();
        cancelarButton.addActionListener(e -> dispose());

        buttonsPanel.add(salvarButton);
        buttonsPanel.add(cancelarButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        container.add(buttonsPanel, gbc);

        add(container, BorderLayout.CENTER);
    }

    private void salvarProduto() {
        String nome = nomeField.getText().trim();
        String quantidadeStr = quantidadeField.getText().trim();
        String precoStr = precoField.getText().trim();
        String descricao = descricaoField.getText().trim();

        if (nome.isEmpty() || quantidadeStr.isEmpty() || precoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha os campos obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int quantidade = Integer.parseInt(quantidadeStr);
            float preco = Float.parseFloat(precoStr);

            PetShopController controller = mainFrame.getController();
            String resultado = controller.cadastrarProduto(nome, quantidade, preco, descricao);

            JOptionPane.showMessageDialog(this, resultado, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Valores numéricos inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
```

---

### Passo 16: CartPanel.java

Painel do carrinho de compras.

```java
package UI.Cart;

import UI.*;
import UI.Components.*;
import PetShopController.PetShopController;
import Estoque.Produto;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CartPanel extends JPanel {

    private MainFrame mainFrame;
    private JLabel totalLabel;
    private ModernButton finalizarButton;
    private JList<String> cartList;
    private DefaultListModel<String> listModel;

    public CartPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setupPanel();
        initComponents();
    }

    private void setupPanel() {
        setBackground(Theme.BG_PRIMARY);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    private void initComponents() {
        // Título
        JLabel titleLabel = new JLabel("🛒 Carrinho");
        titleLabel.setFont(Theme.FONT_TITLE);
        titleLabel.setForeground(Theme.TEXT_PRIMARY);

        // Lista do carrinho
        listModel = new DefaultListModel<>();
        cartList = new JList<>(listModel);
        cartList.setBackground(Theme.BG_SECONDARY);
        cartList.setForeground(Theme.TEXT_PRIMARY);
        cartList.setFont(Theme.FONT_BODY);

        JScrollPane scrollPane = new JScrollPane(cartList);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        // Total e botão
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        totalLabel = new JLabel("Total: R$ 0,00");
        totalLabel.setFont(Theme.FONT_SUBTITLE);
        totalLabel.setForeground(Theme.SUCCESS);

        finalizarButton = new ModernButton("Finalizar Compra");
        finalizarButton.addActionListener(e -> finalizarCompra());

        bottomPanel.add(totalLabel, BorderLayout.WEST);
        bottomPanel.add(finalizarButton, BorderLayout.EAST);

        // Montar o painel
        setLayout(new BorderLayout(10, 10));
        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void atualizarDados() {
        listModel.clear();

        PetShopController controller = mainFrame.getController();
        List<Produto> produtos = controller.listarProdutos();

        if (produtos.isEmpty()) {
            listModel.addElement("Carrinho vazio!");
        } else {
            for (Produto p : produtos) {
                String item = String.format("%s - Qtd: %d - R$ %.2f",
                    p.getNome(), p.getQuantd(), p.getPrecoUni() * p.getQuantd());
                listModel.addElement(item);
            }
        }

        // Atualiza o total
        String total = controller.calcularTotalCarrinho();
        totalLabel.setText("Total: " + total);
    }

    private void finalizarCompra() {
        int resposta = JOptionPane.showConfirmDialog(this,
            "Deseja finalizar a compra?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION);

        if (resposta == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "Compra finalizada com sucesso! 🎉", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            atualizarDados();
        }
    }
}
```

---

## Conexão com o Backend

Todos os componentes usam o `PetShopController` existente. O fluxo funciona assim:

```
MainFrame (cria o controller)
    ↓
    ├── LoginPanel → controller.login()
    ├── RegisterPanel → controller.cadastrarDono()
    ├── DashboardPanel → controller.getDonoLogado()
    ├── PetsPanel → controller.listarMeusAnimais() / cadastrarAnimal()
    ├── ProductsPanel → controller.listarProdutos() / cadastrarProduto()
    └── CartPanel → controller.calcularTotalCarrinho()
```

---

## Resumo dos Arquivos a Criar

| Arquivo | O que faz |
|---------|-----------|
| `Theme.java` | Define cores e fontes |
| `ModernButton.java` | Botão estilizado roxo |
| `ModernTextField.java` | Campo de texto estilizado |
| `RoundedPanel.java` | Painel com bordas arredondadas |
| `ToastNotification.java` | Mensagem temporária |
| `MainFrame.java` | Janela principal + navegação |
| `LoginPanel.java` | Tela de login |
| `RegisterPanel.java` | Tela de cadastro |
| `DashboardPanel.java` | Menu principal pós-login |
| `PetsPanel.java` | Gerenciar pets |
| `PetCard.java` | Cardvisual de pet |
| `AddPetDialog.java` | Dialog para adicionar pet |
| `ProductsPanel.java` | Catálogo de produtos |
| `ProductCard.java` | Card visual de produto |
| `AddProductDialog.java` | Dialog para adicionar produto |
| `CartPanel.java` | Carrinho de compras |

---

## Dicas para os Amigos

1. **Sempre importe as classes certas** - ex: `import UI.Theme;`
2. **Compile frequentemente** - se der erro, olhe a mensagem, geralmente falta um import
3. **Crie os pacotes primeiro** - crie as pastas `Auth`, `Dashboard`, `Pets`, `Products`, `Cart`, `Components` dentro de `src/UI/`
4. **FlatLaf precisa ser carregado no MainFrame** - é o método `setupTheme()` que faz isso

---

## Próximos Passos (Apôs Implementar)

1. Testar o login com um usuário existente
2. Testar o cadastro de novo usuário
3. Adicionar alguns pets
4. Adicionar alguns produtos
5. Verificar o carrinho
6. Ajustar cores se necessário

---

**Bons estudos! 🚀**

Este plano está pronto para implementação. Quando quiser começar, é só avisar!