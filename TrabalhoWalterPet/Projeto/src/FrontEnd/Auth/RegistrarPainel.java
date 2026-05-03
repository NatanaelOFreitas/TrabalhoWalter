package FrontEnd.Auth;

import FrontEnd.*;
import FrontEnd.Componentes.*;
import PetShopController.PetShopController;
import javax.swing.*;
import java.awt.*;


public class RegistrarPainel extends JPanel{
    private JanelaPrincipal JanelaPrincipal;
    private TextField nomeField;
    private TextField emailField;
    private TextField telefoneField;
    private TextField senhaField;
    private TextField confirmarSenhaField;
    private Botão cadastrarButton;
    private JLabel loginLabel;

    public RegistrarPainel(JanelaPrincipal JanelaPrincipal) {
        this.JanelaPrincipal = JanelaPrincipal;
        setupPanel();
        initComponents();
    }

    private void setupPanel() {
        setBackground(Tema.BG_PRIMARY);
        setLayout(new GridBagLayout());
    }

    private void initComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);

        // Título
        JLabel titleLabel = new JLabel("Criar Conta");
        titleLabel.setFont(Tema.FONT_TITLE);
        titleLabel.setForeground(Tema.ACCENT);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;

        // Nome
        gbc.gridy = 1;
        add(new JLabel("Nome: "), gbc);
        nomeField = new TextField("Seu nome completo");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(nomeField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Email: "), gbc);
        emailField = new TextField("seu@email.com");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(emailField, gbc);

        // Telefone
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Telefone: "), gbc);
        telefoneField = new TextField("(00) 00000-0000");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(telefoneField, gbc);

        // Senha
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Senha: "), gbc);
        senhaField = new TextField("Mínimo 4 caracteres");
        senhaField.setEchoChar('*');
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(senhaField, gbc);

        // Confirmar Senha
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Confirmar: "), gbc);
        confirmarSenhaField = new TextField("Digite a senha novamente");
        confirmarSenhaField.setEchoChar('*');
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(confirmarSenhaField, gbc);

        // Botão
        cadastrarButton = new Botão("Cadastrar");
        cadastrarButton.addActionListener(e -> realizarCadastro());

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 10, 10);
        add(cadastrarButton, gbc);

        // Link para login
        loginLabel = new JLabel("<html><u>Já tem conta? Entre aqui</u></html>");
        loginLabel.setForeground(Tema.ACCENT);
        loginLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                JanelaPrincipal.showLogin();
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

            PetShopController controller = JanelaPrincipal.getController();
            String resultado = controller.cadastrarDono(nome, email, telefoneNumero, senha);

            JOptionPane.showMessageDialog(this, resultado, "Cadastro", JOptionPane.INFORMATION_MESSAGE);

            if (resultado.contains("sucesso")) {
                JanelaPrincipal.showLogin();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
