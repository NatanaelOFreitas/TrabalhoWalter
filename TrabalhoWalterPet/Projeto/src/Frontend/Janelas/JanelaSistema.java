package Frontend.Janelas;

import Frontend.JanelasAuxiliares.JanelaEstoque;
import Frontend.JanelasAuxiliares.JanelaProdutos;
import Frontend.JanelasAuxiliares.JanelaVendas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JanelaSistema extends JFrame {

    // ===== CORES =====
    private final Color fundo = new Color(245, 240, 255);
    private final Color roxoPastel = new Color(170, 140, 220);
    private final Color branco = Color.WHITE;
    private JFrame janelaAtual;


    public JanelaSistema() {

        // ===== JANELA =====
        setTitle("Dashboard - PetShop Miaujuda");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ===== PAINEL PRINCIPAL =====
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BorderLayout(15, 15));
        painelPrincipal.setBackground(fundo);
        painelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ===== TOPO =====
        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBackground(fundo);

        JLabel lblTitulo =
                new JLabel("Bem-vindo ao PetShop Miaujuda");

        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(roxoPastel);

        painelTopo.add(lblTitulo, BorderLayout.WEST);

        // ===== CARDS =====
        JPanel painelCards = new JPanel(new GridLayout(2, 2, 15, 15));
        painelCards.setBackground(fundo);

        JPanel cardVendas =
                criarCard(
                        "Sistema Vendas",
                        "",
                        "3 -Acoplar o Estoque real aos produtos disponíveis para vender" +
                                "" +
                                "Não permitir vender uma quantidade maior do que há livre no estoque"
                );

        JPanel cardEstoque =
                criarCard(
                        "Gerenciamento de Produtos",
                        "",
                        "1- Tornar a criação de produtos em algo permanente em quanto o programa não for totalmente encerrado "
                );

        JPanel cardReposicao =
                criarCard(
                        "Gerenciamento de Estoque",
                        "",
                        "2- Tornar a mudança dos produtos no estoque em algo permanente em quanto o código estiver aberto"
                );

        JPanel cardUltimaVenda =
                criarCard(
                        "Última Venda",
                        "R$ 89,90",
                        " (criar um jeito de falar qual foi o valor da última venda realizada) "
                );

        painelCards.add(cardVendas);
        painelCards.add(cardEstoque);
        painelCards.add(cardReposicao);
        painelCards.add(cardUltimaVenda);

        // ===== LISTAS =====
        JPanel painelListas = new JPanel(new GridLayout(1, 2, 15, 15));
        painelListas.setBackground(fundo);

        // ===== ADICIONANDO =====
        painelPrincipal.add(painelTopo, BorderLayout.NORTH);
        painelPrincipal.add(painelCards, BorderLayout.CENTER);
        painelPrincipal.add(painelListas, BorderLayout.SOUTH);

        add(painelPrincipal);

        // ===== CLIQUES DOS CARDS =====

        // Produtos
        cardEstoque.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                fecharJanelaAtual();
                janelaAtual = new JanelaProdutos();
                janelaAtual.setVisible(true);

            }
        });

        // Reposição
        cardReposicao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                fecharJanelaAtual();
                janelaAtual = new JanelaEstoque();
                janelaAtual.setVisible(true);
            }
        });

        // Vendas
        cardVendas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

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

    // ===== MÉTODO CARD =====
    private JPanel criarCard(
            String titulo,
            String valor,
            String descricao
    ) {

        JPanel card = new JPanel();

        card.setLayout(new GridLayout(3, 1));
        card.setBackground(branco);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                roxoPastel,
                                2
                        ),
                        BorderFactory.createEmptyBorder(
                                15,
                                15,
                                15,
                                15
                        )
                )
        );

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(
                new Font("Arial", Font.BOLD, 18));

        lblTitulo.setForeground(roxoPastel);

        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(
                new Font("Arial", Font.BOLD, 24));

        JLabel lblDescricao = new JLabel(descricao);
        lblDescricao.setFont(
                new Font("Arial", Font.PLAIN, 14));

        card.add(lblTitulo);
        card.add(lblValor);
        card.add(lblDescricao);

        // Efeito hover
        card.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                card.setBackground(
                        new Color(235, 225, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {

                card.setBackground(branco);
            }
        });



        return card;
    }
}