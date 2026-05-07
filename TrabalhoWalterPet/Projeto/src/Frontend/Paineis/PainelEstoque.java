package Frontend.Paineis;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PainelEstoque extends JPanel{

    private JLabel MensagemEstoque;

    public PainelEstoque() {

        setSize(480, 270);

        JLabel MensagemEstoque = new JLabel("Olá Mundo");
        add(MensagemEstoque);

    }

    //abre quando clicamos em /_estoque baixo_/ na JanelaPrincipal
}
