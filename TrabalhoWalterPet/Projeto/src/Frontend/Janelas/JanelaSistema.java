package Frontend.Janelas;

import Frontend.JanelasAuxiliares.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class JanelaSistema extends JFrame {

    private JButton btnEstoque;
    private JButton btnProdutos;
    private JButton btnRelatorios;
    private JButton btnVendas;
    private JFrame janelaAtual;

    public JanelaSistema() {
        setTitle("PetShop Miaujuda");
        setSize(300, 840);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(0, 0);
        setResizable(false);

        btnEstoque = new JButton("Estoque");
        btnProdutos = new JButton("Produtos");
        btnRelatorios = new JButton("Relatorios");
        btnVendas = new JButton("Vendas");

        JPanel painelBotoes = new JPanel();

        painelBotoes.setLayout(new GridLayout(4, 1));

        painelBotoes.add(btnEstoque);
        painelBotoes.add(btnProdutos);
        painelBotoes.add(btnRelatorios);
        painelBotoes.add(btnVendas);

        add(painelBotoes);

        btnEstoque.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                fecharJanelaAtual();
                janelaAtual = new JanelaEstoque();
                janelaAtual.setVisible(true);
            }
        });


        btnProdutos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                fecharJanelaAtual();
                janelaAtual = new JanelaProdutos();
                janelaAtual.setVisible(true);
            }
        });

        btnRelatorios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                fecharJanelaAtual();
                janelaAtual = new JanelaRelatorios();
                janelaAtual.setVisible(true);
            }
        });


        btnVendas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                fecharJanelaAtual();
                janelaAtual = new JanelaVendas();
                janelaAtual.setVisible(true);
            }
        });
    }

    void fecharJanelaAtual() {

        if (janelaAtual != null) {
                janelaAtual.dispose();
        }
    }
}
