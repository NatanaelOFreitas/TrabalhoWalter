package Frontend.JanelasAuxiliares;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaEstoque extends JFrame{

    private JLabel MensagemEstoque;

    public JanelaEstoque() {

        setSize(480, 800);
        setLocation(320, 0);
        JLabel MensagemEstoque = new JLabel("Olá JanelaEstoque");
        add(MensagemEstoque);

    }
}
