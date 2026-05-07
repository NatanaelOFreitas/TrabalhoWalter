package Frontend.JanelasAuxiliares;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaVendas extends JFrame{

    private JLabel MensagemVendas;

    public JanelaVendas() {

        setSize(480, 800);
        setLocation(320, 0);
        JLabel MensagemVendas = new JLabel("Olá JanelaVendas");
        add(MensagemVendas);

    }
}
