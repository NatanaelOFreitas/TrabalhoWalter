package Frontend.Paineis;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PainelRelatorios extends JPanel{

    private JLabel MensagemRelatorios;

    public PainelRelatorios() {

        setSize(480, 270);

        JLabel MensagemRelatorios = new JLabel("Olá Mundo");
        add(MensagemRelatorios);

    }

    //abre quando clicamos em /_estoque baixo_/ na JanelaPrincipal
}
