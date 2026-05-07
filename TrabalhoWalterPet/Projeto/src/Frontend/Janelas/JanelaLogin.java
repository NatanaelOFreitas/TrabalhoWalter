package Frontend.Janelas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaLogin extends JFrame {
    private JPasswordField txtSenha;
    private JButton btnEntrar;
    private JLabel lblMensagem;

    public JanelaLogin() {
        setTitle("PetShop Miaujuda");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        //Cores
        Color fundo = new Color(245, 240, 255); // lilás bem claro
        Color roxoPastel = new Color(170, 140, 220);

        JPanel painelSenha = new JPanel();
        painelSenha.setBackground(fundo);

        //Título
        JLabel lblTitulo = new JLabel(" PetShop Miaujuda ");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(roxoPastel);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        painelSenha.add(lblTitulo, BorderLayout.NORTH);

        //Centro
        JPanel painelCentro = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 30));
        painelCentro.setBackground(fundo);

        //Label da senha
        JLabel lblSenha = new JLabel("Digite a senha:");
        lblSenha.setFont(new Font("Arial", Font.PLAIN, 16));
        lblSenha.setForeground(new Color(90, 70, 120));

        //Campo da senha
        txtSenha = new JPasswordField(15);
        txtSenha.setFont(new Font("Arial", Font.PLAIN, 16));
        txtSenha.setBorder(BorderFactory.createLineBorder(roxoPastel, 2));

        painelCentro.add(lblSenha);
        painelCentro.add(txtSenha);

        painelSenha.add(painelCentro, BorderLayout.CENTER);

        //Parte de baixo
        JPanel painelBaixo = new JPanel();
        painelBaixo.setBackground(fundo);
        painelBaixo.setLayout(new GridLayout(2, 1));

        //Botão entrar
        btnEntrar = new JButton("Entrar");
        btnEntrar.setBackground(roxoPastel);
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFocusPainted(false);
        btnEntrar.setFont(new Font("Arial", Font.BOLD, 16));

        //Mensagem
        lblMensagem = new JLabel("", SwingConstants.CENTER);
        lblMensagem.setForeground(Color.RED);

        // Evento botão
        btnEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String senha = new String(txtSenha.getPassword());
                if (senha.equals("1234")) {
                    dispose();
                    JanelaSistema Sistema = new JanelaSistema();
                    Sistema.setVisible(true);
                } else {
                    lblMensagem.setForeground(Color.RED);
                    lblMensagem.setText("Senha incorreta!");
                }
            }
        });

        painelBaixo.add(btnEntrar);
        painelBaixo.add(lblMensagem);

        painelSenha.add(painelBaixo, BorderLayout.SOUTH);

        add(painelSenha);
    }
}