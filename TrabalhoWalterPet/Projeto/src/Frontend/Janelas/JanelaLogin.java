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

        JPanel painelSenha = new JPanel();
        painelSenha.setBackground(new Color(255, 228, 236));

        //Título
        JLabel lblTitulo = new JLabel(" PetShop Miaujuda ");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(255, 20, 147));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        painelSenha.add(lblTitulo, BorderLayout.NORTH);

        //Centro
        JPanel painelCentro = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 30));
        painelCentro.setBackground(new Color(255, 228, 236));

        //Label da senha
        JLabel lblSenha = new JLabel("Digite a senha:");
        lblSenha.setFont(new Font("Arial", Font.PLAIN, 16));

        //Campo da senha
        txtSenha = new JPasswordField(15);
        txtSenha.setFont(new Font("Arial", Font.PLAIN, 16));

        painelCentro.add(lblSenha);
        painelCentro.add(txtSenha);

        painelSenha.add(painelCentro, BorderLayout.CENTER);

        //Parte de baixo
        JPanel painelBaixo = new JPanel();
        painelBaixo.setBackground(new Color(255, 228, 236));
        painelBaixo.setLayout(new GridLayout(2, 1));

        //Botão entrar
        btnEntrar = new JButton("Entrar");
        btnEntrar.setBackground(new Color(255, 105, 180));
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