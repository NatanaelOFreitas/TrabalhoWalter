package Frontend.Principal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaPrincipal extends JFrame {
    private JTextField txtSenha;
    private JButton btnEntrar;

    public JanelaPrincipal() {
        setTitle("PetShop Miaujuda");
        setSize(200,150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel painelSenha = new JPanel();
        painelSenha.setLayout(new GridLayout(0, 1, 5, 5));

        JTextArea AreaSenha = new JTextArea(1, 12);


        JPanel painelSenhaIncorreta = new JPanel();
        painelSenhaIncorreta.setLayout(new GridLayout(0,1,5,5));

        painelSenha.add(new JLabel("Senha: "));
        painelSenhaIncorreta.add(new JLabel("Senha Incorreta!"));


        add(AreaSenha, BorderLayout.CENTER);
        add(painelSenha, BorderLayout.PAGE_START);

        btnEntrar = new JButton("Entrar");
        add(btnEntrar,BorderLayout.SOUTH);

        btnEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String Senha = "1234";
                String SenhaDigitada = AreaSenha.getText();

                if (SenhaDigitada.equals(Senha)){
                    System.exit(0);                         //Validar se esse é o comando certo pra trocar de tela
                }
                else {
                    JOptionPane.showMessageDialog(null, "Senha Incorreta!");
                }
            }


        });
    }
}



//JanelaPrincipal (JFrame)
//├── Barra de Título (customizada)
//├── Menu Lateral Esquerdo (JPanel)
//│   ├── Logo/Título do App
//│   └── Botões de Navegação
//│       ├── Dashboard
//│       ├── Produtos
//│       ├── Estoque
//│       ├── Vendas
//│       └── Relatórios
//└── Área de Conteúdo (JPanel)
//    └── Painel Principal (card layout)
//        ├── PainelDashboard
//        ├── PainelProdutos
//        ├── PainelEstoque
//        ├── PainelVendas
//        └── PainelRelatorios
