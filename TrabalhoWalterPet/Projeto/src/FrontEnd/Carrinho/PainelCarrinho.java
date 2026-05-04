package FrontEnd.Carrinho;

import FrontEnd.*;
import FrontEnd.Componentes.*;
import Backend.PetShopController.PetShopController;
import Backend.Estoque.Produto;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PainelCarrinho extends JPanel{
    private JanelaPrincipal JanelaPrincipal;
    private JLabel totalLabel;
    private Botão finalizarButton;
    private JList<String> cartList;
    private DefaultListModel<String> listModel;

    public PainelCarrinho(JanelaPrincipal JanelaPrincipal) {
        this.JanelaPrincipal = JanelaPrincipal;
        setupPanel();
        initComponents();
    }

    private void setupPanel() {
        setBackground(Tema.BG_PRIMARY);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    private void initComponents() {
        // Título
        JLabel titleLabel = new JLabel("🛒 Carrinho");
        titleLabel.setFont(Tema.FONT_TITLE);
        titleLabel.setForeground(Tema.TEXT_PRIMARY);

        // Lista do carrinho
        listModel = new DefaultListModel<>();
        cartList = new JList<>(listModel);
        cartList.setBackground(Tema.BG_SECONDARY);
        cartList.setForeground(Tema.TEXT_PRIMARY);
        cartList.setFont(Tema.FONT_BODY);

        JScrollPane scrollPane = new JScrollPane(cartList);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        // Total e botão
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        totalLabel = new JLabel("Total: R$ 0,00");
        totalLabel.setFont(Tema.FONT_SUBTITLE);
        totalLabel.setForeground(Tema.SUCCESS);

        finalizarButton = new Botão("Finalizar Compra");
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

        PetShopController controller = JanelaPrincipal.getController();
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
