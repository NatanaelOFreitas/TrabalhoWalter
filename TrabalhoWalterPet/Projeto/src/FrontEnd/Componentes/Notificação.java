package FrontEnd.Componentes;

import FrontEnd.Tema;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Notificação extends JWindow {
    private JLabel messageLabel;
    private Timer timer;

    public Notificação(JFrame parent, String message, boolean isError) {
        setSize(300, 50);
        setLocationRelativeTo(parent);

        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(isError ? Tema.ERROR : Tema.SUCCESS);
        container.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        messageLabel = new JLabel(message);
        messageLabel.setForeground(Tema.BG_PRIMARY);
        messageLabel.setFont(Tema.FONT_BODY);

        container.add(messageLabel, BorderLayout.CENTER);
        add(container);

        // Clique para fechar
        container.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                close();
            }
        });

        // Auto-fechar após 3 segundos
        timer = new Timer(3000, e -> close());
        timer.setRepeats(false);
    }

    public void show() {
        setVisible(true);
        timer.start();
    }

    private void close() {
        timer.stop();
        setVisible(false);
        dispose();
    }
}
