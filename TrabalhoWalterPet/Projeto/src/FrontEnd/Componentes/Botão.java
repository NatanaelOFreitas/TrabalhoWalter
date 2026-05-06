package FrontEnd.Componentes;

import FrontEnd.Tema;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Botão extends JButton{
    private Color currentColor = Tema.ACCENT;
    private boolean isHovering = false;

    public Botão(String text) {
        super(text);
        setupButton();
    }

    private void setupButton() {
        //setContentAreaPainted(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);

        setForeground(Tema.TEXT_PRIMARY);
        setFont(Tema.FONT_BODY);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(120, 40));

        // Efeito hover
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovering = true;
                currentColor = Tema.ACCENT_HOVER;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovering = false;
                currentColor = Tema.ACCENT;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);

        // Fundo com bordas arredondadas
        g2.setColor(currentColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

        // Texto centralizado
        FontMetrics fm = g2.getFontMetrics();
        String text = getText();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() + textHeight) / 2 - 2;

        g2.setColor(Tema.TEXT_PRIMARY);
        g2.setFont(getFont());
        g2.drawString(text, x, y);
    }

    // Método para botão secundário (sem cor de fundo)
    public void setSecondary() {
        currentColor = Tema.BG_TERTIARY;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                currentColor = Tema.BORDER;
                repaint();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                currentColor = Tema.BG_TERTIARY;
                repaint();
            }
        });
    }
}
