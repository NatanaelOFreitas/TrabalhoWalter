package FrontEnd.Auth;

import FrontEnd.*;
import FrontEnd.Componentes.*;
import PetShopController.PetShopController;
import javax.swing.*;
import java.awt.*;

public class PainelLogin extends JPanel{
    private JanelaPrincipal JanelaPrincipal;
    private TextField emailField;
    private TextField senhaField;
    private Botão loginButton;
    private JLabel criarContaLabel;

    public PainelLogin(JanelaPrincipal JanelaPrincipal) {
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
        gbc.insets = new Insets(10, 10, 10, 10);

        // Título
        JLabel titleLabel = new JLabel("Pet Shop Miaujuda");
        titleLabel.setFont(Tema.FONT_TITLE);
        titleLabel.setForeground(Tema.ACCENT);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // Subtítulo
        JLabel subtitleLabel = new JLabel("Faça login para continuar");
        subtitleLabel.setFont(Tema.FONT_BODY);
        subtitleLabel.setForeground(Tema.TEXT_SECONDARY);

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

        emailField = new TextField("seu@email.com");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(emailField, gbc);

        // Senha
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Senha: "), gbc);

        senhaField = new TextField("********");
        senhaField.setEchoChar('*');
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(senhaField, gbc);

        // Botão Login
        loginButton = new Botão("Entrar");
        loginButton.addActionListener(e -> realizarLogin());

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 10, 10, 10);
        add(loginButton, gbc);

        // Link para criar conta
        criarContaLabel = new JLabel("<html><u>Não tem conta? Cadastre-se</u></html>");
        criarContaLabel.setForeground(Tema.ACCENT);
        criarContaLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        criarContaLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                JanelaPrincipal.showRegister();
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

        PetShopController controller = JanelaPrincipal.getController();

        if (controller.login(email, senha)) {
            showToast("Login realizado com sucesso!", false);
            JanelaPrincipal.showDashboard();
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
