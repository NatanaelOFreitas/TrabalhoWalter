package Frontend.Janelas;

import Frontend.JanelasAuxiliares.JanelaProdutos;
import Frontend.JanelasAuxiliares.JanelaVendas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JanelaSistema extends JFrame {

    // ===== CORES =====
    private final Color fundo = new Color(245, 240, 255);
    private final Color roxoPastel = new Color(170, 140, 220);
    private final Color branco = Color.WHITE;

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
                        "Vendas Hoje",
                        "15 vendas",
                        "R$ 1.250,00"
                );

        JPanel cardEstoque =
                criarCard(
                        "Produtos em Estoque",
                        "250 itens",
                        ""
                );

        JPanel cardReposicao =
                criarCard(
                        "Alertas de Reposição",
                        "8 produtos",
                        ""
                );

        JPanel cardUltimaVenda =
                criarCard(
                        "Última Venda",
                        "R$ 89,90",
                        ""
                );

        painelCards.add(cardVendas);
        painelCards.add(cardEstoque);
        painelCards.add(cardReposicao);
        painelCards.add(cardUltimaVenda);

        // ===== LISTAS =====
        JPanel painelListas = new JPanel(new GridLayout(1, 2, 15, 15));
        painelListas.setBackground(fundo);

        // ===== ÚLTIMAS VENDAS =====
        JPanel painelVendas = new JPanel(new BorderLayout());
        painelVendas.setBackground(branco);
        painelVendas.setBorder(
                BorderFactory.createLineBorder(roxoPastel, 2));

        JLabel lblVendas =
                new JLabel("Últimos Produtos Vendidos Hoje");

        lblVendas.setFont(new Font("Arial", Font.BOLD, 18));
        lblVendas.setForeground(roxoPastel);

        DefaultListModel<String> modeloVendas =
                new DefaultListModel<>();

        modeloVendas.addElement("Ração Golden - 2 unidades");
        modeloVendas.addElement("Shampoo Pet - 1 unidade");
        modeloVendas.addElement("Coleira Azul - 3 unidades");
        modeloVendas.addElement("Brinquedo Osso - 1 unidade");

        JList<String> listaVendas =
                new JList<>(modeloVendas);

        painelVendas.add(lblVendas, BorderLayout.NORTH);
        painelVendas.add(
                new JScrollPane(listaVendas),
                BorderLayout.CENTER
        );

        // ===== ESTOQUE BAIXO =====
        JPanel painelEstoqueBaixo =
                new JPanel(new BorderLayout());

        painelEstoqueBaixo.setBackground(branco);

        painelEstoqueBaixo.setBorder(
                BorderFactory.createLineBorder(Color.RED, 2));

        JLabel lblEstoqueBaixo =
                new JLabel("Produtos com Estoque Baixo");

        lblEstoqueBaixo.setFont(
                new Font("Arial", Font.BOLD, 18));

        lblEstoqueBaixo.setForeground(Color.RED);

        DefaultListModel<String> modeloEstoque =
                new DefaultListModel<>();

        modeloEstoque.addElement("Ração Premium - 2 unidades");
        modeloEstoque.addElement("Tapete Higiênico - 1 unidade");
        modeloEstoque.addElement("Antipulgas - 3 unidades");

        JList<String> listaEstoque =
                new JList<>(modeloEstoque);

        painelEstoqueBaixo.add(
                lblEstoqueBaixo,
                BorderLayout.NORTH
        );

        painelEstoqueBaixo.add(
                new JScrollPane(listaEstoque),
                BorderLayout.CENTER
        );

        painelListas.add(painelVendas);
        painelListas.add(painelEstoqueBaixo);

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

                new JanelaProdutos().setVisible(true);
            }
        });

        // Reposição
        cardReposicao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                JOptionPane.showMessageDialog(
                        null,
                        "Abrir tela de estoque!"
                );
            }
        });

        // Vendas
        cardVendas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new JanelaVendas().setVisible(true);
            }
        });

        // Estoque baixo clicável
        listaEstoque.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                new JanelaProdutos().setVisible(true);
            }
        });
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