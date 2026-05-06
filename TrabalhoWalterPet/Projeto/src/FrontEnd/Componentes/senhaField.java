package FrontEnd.Componentes;
import FrontEnd.Tema;
import javax.swing.*;
import java.awt.*;
import javax.swing.JPasswordField;

public class senhaField extends JPanel {
    private JPasswordField passwordField;
    private String placeholder;
    private Icon icon;
    public senhaField(String placeholder, Icon icon) {
        this.placeholder = placeholder;
        this.icon = icon;
        setupField();
    }
    public senhaField(String placeholder) {
        this(placeholder, null);
    }
    private void setupField() {
        setLayout(new BorderLayout(10, 0));
        setBackground(Tema.BG_TERTIARY);
        setPreferredSize(new Dimension(250, 45));
        setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        passwordField = new JPasswordField() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getPassword().length == 0 && !isFocusOwner()) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setColor(Tema.TEXT_SECONDARY);
                    g2.setFont(Tema.FONT_BODY);
                    g2.drawString(placeholder, 0, g.getFontMetrics().getHeight());
                }
            }
        };
        passwordField.setBackground(Tema.BG_TERTIARY);
        passwordField.setForeground(Tema.TEXT_PRIMARY);
        passwordField.setCaretColor(Tema.ACCENT);
        passwordField.setBorder(null);
        passwordField.setFont(Tema.FONT_BODY);
        if (icon != null) {
            JLabel iconLabel = new JLabel(icon);
            iconLabel.setForeground(Tema.TEXT_SECONDARY);
            add(iconLabel, BorderLayout.WEST);
        }
        add(passwordField, BorderLayout.CENTER);
    }
    public String getText() {
        return new String(passwordField.getPassword());
    }
    public void setText(String text) {
        passwordField.setText(text);
    }
    public void setEchoChar(char c) {
        passwordField.setEchoChar(c);
    }
}
