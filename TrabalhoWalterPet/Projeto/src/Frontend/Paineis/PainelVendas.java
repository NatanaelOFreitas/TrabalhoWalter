package Frontend.Paineis;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PainelVendas extends JPanel{

    private JLabel MensagemVendas;

    public PainelVendas() {

        setSize(480, 270);

        JLabel MensagemVendas = new JLabel("Olá Mundo");
        add(MensagemVendas);

    }

    //abre quando clicamos em /_estoque baixo_/ na JanelaPrincipal
}
