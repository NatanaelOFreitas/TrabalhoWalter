package FrontEnd.Dashboard;

import FrontEnd.*;
import FrontEnd.Componentes.*;
import FrontEnd.Pets.*;
import FrontEnd.Produtos.*;
import FrontEnd.Carrinho.*;
import PetShopController.PetShopController;
import Dono.Dono;
import javax.swing.*;
import java.awt.*;

public class PainelDashboard extends JPanel {
    private JanelaPrincipal JanelaPrincipal;
    private CardLayout contentLayout;
    private JPanel contentPanel;
    private JLabel usuarioLabel;
    private JLabel logoutLabel;

    // Sub-painéis
    private PainelPets petsPanel;
    private ProdutosPainel productsPanel;
    private PainelCarrinho cartPanel;

    // Botões da sidebar
    private JButton petsBtn, productsBtn, cartBtn;

    public PainelDashboard(JanelaPrincipal JanelaPrincipal) {
        this.JanelaPrincipal = JanelaPrincipal;
        setupPanel();
        initComponents();
        initSubPanels();
    }

    private void setupPanel() {
        setBackground(Tema.BG_PRIMARY);
        setLayout(new BorderLayout());
    }

    private void initComponents() {
        // ===== TOPO =====
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Tema.BG_SECONDARY);
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        usuarioLabel = new JLabel("Olá, Visitante!");
        usuarioLabel.setFont(Tema.FONT_SUBTITLE);
        usuarioLabel.setForeground(Tema.TEXT_PRIMARY);

        logoutLabel = new JLabel("Sair");
        logoutLabel.setForeground(Tema.ERROR);
        logoutLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                JanelaPrincipal.getController().logout();
                JanelaPrincipal.showLogin();
            }
        });

        topPanel.add(usuarioLabel, BorderLayout.WEST);
        topPanel.add(logoutLabel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // ===== SIDEBAR =====
        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(Tema.BG_SECONDARY);
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        // Logo
        JLabel logoLabel = new JLabel("");
        logoLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidePanel.add(logoLabel);
        sidePanel.add(Box.createVerticalStrut(30));

        // Botões de navegação
        petsBtn = createNavButton("Meus Pets");
        productsBtn = createNavButton("Produtos");
        cartBtn = createNavButton("Carrinho");

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
        contentPanel.setBackground(Tema.BG_PRIMARY);

        add(contentPanel, BorderLayout.CENTER);
    }

    private JButton createNavButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(Tema.FONT_BODY);
        btn.setForeground(Tema.TEXT_PRIMARY);
        btn.setBackground(Tema.BG_TERTIARY);
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
        petsPanel = new PainelPets(JanelaPrincipal);
        productsPanel = new ProdutosPainel(JanelaPrincipal);
        cartPanel = new PainelCarrinho(JanelaPrincipal);

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
        PetShopController controller = JanelaPrincipal.getController();
        Dono dono = controller.getDonoLogado();

        if (dono != null) {
            usuarioLabel.setText("Olá, " + dono.getNome() + "!");
        }

        petsPanel.atualizarDados();
    }
}
