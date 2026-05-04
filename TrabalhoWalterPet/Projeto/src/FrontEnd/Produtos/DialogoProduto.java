package FrontEnd.Produtos;

import FrontEnd.*;
import FrontEnd.Componentes.*;
import Backend.PetShopController.PetShopController;
import FrontEnd.Componentes.TextField;
import javax.swing.*;
import java.awt.*;

public class DialogoProduto extends JDialog{
    private JanelaPrincipal JanelaPrincipal;
    private TextField nomeField;
    private TextField quantidadeField;
    private TextField precoField;
    private TextField descricaoField;
    private Botão salvarButton;
    private Botão cancelarButton;

    public DialogoProduto(JFrame parent, JanelaPrincipal JanelaPrincipal) {
        super(parent, "Adicionar Produto", true);
        this.JanelaPrincipal = JanelaPrincipal;
        setupDialog();
        initComponents();
    }

    private void setupDialog() {
        setSize(450, 350);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Tema.BG_PRIMARY);
        setUndecorated(true);
    }

    private void initComponents() {
        PainelRedondo container = new PainelRedondo(20);
        container.setLayout(new GridBagLayout());
        container.setBackground(Tema.BG_SECONDARY);
        container.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("📦 Adicionar Produto");
        titleLabel.setFont(Tema.FONT_SUBTITLE);
        titleLabel.setForeground(Tema.ACCENT);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        container.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;

        // Nome
        gbc.gridy = 1;
        add(new JLabel("Nome:"), gbc);
        nomeField = new TextField("Nome do produto");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        container.add(nomeField, gbc);

        // Quantidade
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Quantidade:"), gbc);
        quantidadeField = new TextField("Ex: 10");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        container.add(quantidadeField, gbc);

        // Preço
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Preço (R$):"), gbc);
        precoField = new TextField("Ex: 29.90");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        container.add(precoField, gbc);

        // Descrição
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Descrição:"), gbc);
        descricaoField = new TextField("Descrição opcional");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        container.add(descricaoField, gbc);

        // Botões
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonsPanel.setOpaque(false);

        salvarButton = new Botão("Salvar");
        salvarButton.addActionListener(e -> salvarProduto());

        cancelarButton = new Botão("Cancelar");
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

            PetShopController controller = JanelaPrincipal.getController();
            String resultado = controller.cadastrarProduto(nome, quantidade, preco, descricao);

            JOptionPane.showMessageDialog(this, resultado, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Valores numéricos inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
