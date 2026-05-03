package FrontEnd.Componentes;

import FrontEnd.Tema;
import javax.swing.*;
import java.awt.*;

public class PainelRedondo extends JPanel{
    private int cornerRadius = 15;
    private Color backgroundColor = Tema.BG_SECONDARY;

    public PainelRedondo() {
        super();
        setupPanel();
    }

    public PainelRedondo(int radius) {
        super();
        this.cornerRadius = radius;
        setupPanel();
    }

    private void setupPanel() {
        setOpaque(false);
        setBackground(Tema.BG_SECONDARY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, true);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        super.paintComponent(g2);
    }
}
