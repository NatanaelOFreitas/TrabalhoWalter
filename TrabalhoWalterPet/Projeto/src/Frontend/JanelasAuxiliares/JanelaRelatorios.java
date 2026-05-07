package Frontend.JanelasAuxiliares;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaRelatorios extends JFrame{

    private JLabel MensagemRelatorios;

    public JanelaRelatorios() {

        setSize(480, 800);
        setLocation(320, 0);

        JLabel MensagemRelatorios = new JLabel("Olá JanelaRelatorios");
        add(MensagemRelatorios);

    }
}
