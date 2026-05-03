package FrontEnd.Pets;

import FrontEnd.*;
import FrontEnd.Componentes.*;
import PetShopController.PetShopController;
import javax.swing.*;
import java.awt.*;

public class DialogoPets extends JDialog{
    private JanelaPrincipal JanelaPrincipal;
    private TextField nomeField;
    private TextField idadeField;
    private Botão salvarButton;
    private Botão cancelarButton;

    public DialogoPets(JFrame parent, JanelaPrincipal JanelaPrincipal) {
        super(parent, "Adicionar Pet", true);
        this.JanelaPrincipal = JanelaPrincipal;
        setupDialog();
        initComponents();
    }

    private void setupDialog() {
        setSize(400, 250);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Tema.BG_PRIMARY);
        setUndecorated(true);
    }

    private void initComponents() {
        // Container com bordas arredondadas
        PainelRedondo container = new PainelRedondo(20);
        container.setLayout(new GridBagLayout());
        container.setBackground(Tema.BG_SECONDARY);
        container.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel titleLabel = new JLabel("➕ Adicionar Novo Pet");
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

        nomeField = new TextField("Nome do pet");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        container.add(nomeField, gbc);

        // Idade
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("Idade:"), gbc);

        idadeField = new TextField("Ex: 2.5");
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        container.add(idadeField, gbc);

        // Botões
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonsPanel.setOpaque(false);

        salvarButton = new Botão("Salvar");
        salvarButton.addActionListener(e -> salvarPet());

        cancelarButton = new Botão("Cancelar");
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
            PetShopController controller = JanelaPrincipal.getController();
            String resultado = controller.cadastrarAnimal(nome, idade);

            JOptionPane.showMessageDialog(this, resultado, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Idade inválida! Digite um número.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
