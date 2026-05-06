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

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(0, 1, 5, 5));

        painel.add(new JLabel("Senha: "));
        

        add(painel, BorderLayout.NORTH);

        btnEntrar = new JButton("Entrar");
        add(btnEntrar,BorderLayout.SOUTH);

        btnEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
