package FrontEnd.Produtos;

import FrontEnd.*;
import FrontEnd.Componentes.*;
import PetShopController.PetShopController;
import Estoque.Produto;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProdutosPainel extends JPanel{
    private JanelaPrincipal JanelaPrincipal;
    private JPanel productsContainer;
    private JScrollPane scrollPane;
    private Botão addButton;

    public ProdutosPainel(JanelaPrincipal JanelaPrincipal) {
        this.JanelaPrincipal = JanelaPrincipal;
        setupPanel();
        initComponents();
        atualizarDados();
    }

    private void setupPanel() {
        setBackground(Tema.BG_PRIMARY);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    private void initComponents() {
        // Título e botão adicionar
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("📦 Produtos");
        titleLabel.setFont(Tema.FONT_TITLE);
        titleLabel.setForeground(Tema.TEXT_PRIMARY);

        addButton = new Botão("+ Novo Produto");
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

        PetShopController controller = JanelaPrincipal.getController();
        List<Produto> produtos = controller.listarProdutos();

        if (produtos.isEmpty()) {
            JLabel emptyLabel = new JLabel("Nenhum produto cadastrado!");
            emptyLabel.setFont(Tema.FONT_BODY);
            emptyLabel.setForeground(Tema.TEXT_SECONDARY);
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            productsContainer.add(emptyLabel);
        } else {
            for (Produto produto : produtos) {
                productsContainer.add(new ProdutoCarrinho(produto));
            }
        }

        productsContainer.revalidate();
        productsContainer.repaint();
    }

    private void mostrarDialogAdicionar() {
        DialogoProduto dialog = new DialogoProduto((JFrame) SwingUtilities.getWindowAncestor(this),
                                                        JanelaPrincipal);
        dialog.setVisible(true);
        atualizarDados();
    }
}
