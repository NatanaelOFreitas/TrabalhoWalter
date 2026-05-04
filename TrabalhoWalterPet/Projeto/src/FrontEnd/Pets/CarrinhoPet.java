package FrontEnd.Pets;

import FrontEnd.Tema;
import FrontEnd.Componentes.*;
import Backend.Animais.Animal;
import javax.swing.*;
import java.awt.*;

public class CarrinhoPet extends PainelRedondo{
    private Animal pet;
    private PainelPets parentPanel;

    public CarrinhoPet(Animal pet, PainelPets parentPanel) {
        super(15);
        this.pet = pet;
        this.parentPanel = parentPanel;
        setupCard();
    }

    private void setupCard() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Tema.BG_SECONDARY);
        setPreferredSize(new Dimension(180, 150));

        // Nome do pet
        JLabel nomeLabel = new JLabel(pet.getNome());
        nomeLabel.setFont(Tema.FONT_SUBTITLE);
        nomeLabel.setForeground(Tema.TEXT_PRIMARY);
        nomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Idade
        JLabel idadeLabel = new JLabel(String.format("%.1f anos", pet.getIdade()));
        idadeLabel.setFont(Tema.FONT_BODY);
        idadeLabel.setForeground(Tema.TEXT_SECONDARY);
        idadeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Botão remover
        JButton removerBtn = new JButton("");
        removerBtn.setFocusPainted(false);
        removerBtn.setBackground(Tema.ERROR);
        removerBtn.setForeground(Tema.TEXT_PRIMARY);
        removerBtn.addActionListener(e -> parentPanel.removerPet(pet));

        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(nomeLabel);
        infoPanel.add(idadeLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(removerBtn);

        add(infoPanel, BorderLayout.CENTER);
    }

}
