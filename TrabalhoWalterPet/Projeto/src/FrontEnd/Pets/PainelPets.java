package FrontEnd.Pets;

import FrontEnd.*;
import FrontEnd.Componentes.*;
import PetShopController.PetShopController;
import Animais.Animal;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PainelPets extends JPanel{
    private JanelaPrincipal JanelaPrincipal;
    private JPanel petsContainer;
    private JScrollPane scrollPane;
    private Botão addButton;

    public PainelPets(JanelaPrincipal JanelaPrincipal) {
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

        JLabel titleLabel = new JLabel("Meus Pets");
        titleLabel.setFont(Tema.FONT_TITLE);
        titleLabel.setForeground(Tema.TEXT_PRIMARY);

        addButton = new Botão("+ Adicionar Pet");
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

        PetShopController controller = JanelaPrincipal.getController();
        List<Animal> pets = controller.listarMeusAnimais();

        if (pets.isEmpty()) {
            JLabel emptyLabel = new JLabel("Você ainda não tem pets cadastrados!");
            emptyLabel.setFont(Tema.FONT_BODY);
            emptyLabel.setForeground(Tema.TEXT_SECONDARY);
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            petsContainer.add(emptyLabel);
        } else {
            for (Animal pet : pets) {
                petsContainer.add(new CarrinhoPet(pet, this));
            }
        }

        petsContainer.revalidate();
        petsContainer.repaint();
    }

    private void mostrarDialogAdicionar() {
        AddPetDialog dialog = new AddPetDialog((JFrame) SwingUtilities.getWindowAncestor(this), JanelaPrincipal);
        dialog.setVisible(true);
        atualizarDados();
    }

    public void removerPet(Animal pet) {
        int resposta = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja remover o pet " + pet.getNome() + "?",
                "Confirmar", JOptionPane.YES_NO_OPTION);

        if (resposta == JOptionPane.YES_OPTION) {
            PetShopController controller = JanelaPrincipal.getController();
            String resultado = controller.removerAnimal(pet.getId());
            JOptionPane.showMessageDialog(this, resultado);
            atualizarDados();
        }
    }
}
