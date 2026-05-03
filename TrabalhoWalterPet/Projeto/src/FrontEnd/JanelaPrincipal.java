package FrontEnd;

import FrontEnd.Auth.PainelLogin;
import FrontEnd.Auth.RegistrarPainel;
import FrontEnd.Dashboard.PainelDashboard;
import PetShopController.PetShopController;
import javax.swing.*;
import java.awt.*;


public class JanelaPrincipal extends JFrame{
    private PetShopController controller;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // Referências para as telas
    private PainelLogin loginPanel;
    private RegistrarPainel registerPanel;
    private PainelDashboard dashboardPanel;

    public JanelaPrincipal() {
        // Configurações básicas
        setTitle("Pet Shop Miaujuda");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicializa o controller
        controller = new PetShopController();

        // Configura o tema escuro
        setupTema();

        // Inicializa os componentes
        initComponents();

        // Mostra a tela inicial (login)
        showLogin();
    }

    private void setupTema() {
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
        mainPanel.setBackground(Tema.BG_PRIMARY);

        // Cria as telas
        loginPanel = new PainelLogin(this);
        registerPanel = new RegistrarPainel(this);
        dashboardPanel = new PainelDashboard(this);

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
}
