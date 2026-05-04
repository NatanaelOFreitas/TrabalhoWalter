package FrontEnd.Componentes;

import FrontEnd.Tema;
import javax.swing.*;
import java.awt.*;
import javax.swing.JPasswordField;

public class TextField extends JPanel{
    private JTextField textField;
    private String placeholder;
    private Icon icon;

    public TextField(String placeholder, Icon icon) {
        this.placeholder = placeholder;
        this.icon = icon;
        setupField();
    }

    public TextField(String placeholder) {
        this(placeholder, null);
    }

    private void setupField() {
        setLayout(new BorderLayout(10, 0));
        setBackground(Tema.BG_TERTIARY);
        setPreferredSize(new Dimension(250, 45));
        setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        textField = new JTextField() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty() && !isFocusOwner()) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setColor(Tema.TEXT_SECONDARY);
                    g2.setFont(Tema.FONT_BODY);
                    g2.drawString(placeholder, 0, g.getFontMetrics().getHeight());
                }
            }
        };

        textField.setBackground(Tema.BG_TERTIARY);
        textField.setForeground(Tema.TEXT_PRIMARY);
        textField.setCaretColor(Tema.ACCENT);
        textField.setBorder(null);
        textField.setFont(Tema.FONT_BODY);

        if (icon != null) {
            JLabel iconLabel = new JLabel(icon);
            iconLabel.setForeground(Tema.TEXT_SECONDARY);
            add(iconLabel, BorderLayout.WEST);
        }

        add(textField, BorderLayout.CENTER);
    }

    public String getText() {
        return textField.getText();
    }

    public void setText(String text) {
        textField.setText(text);
    }

//    public void setEchoChar(char c) {
//        textField.set;
//    }
}
