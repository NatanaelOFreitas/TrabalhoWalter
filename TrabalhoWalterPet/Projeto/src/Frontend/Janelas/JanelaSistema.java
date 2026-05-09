package Frontend.Janelas;

import Frontend.JanelasAuxiliares.JanelaEstoque;
import Frontend.JanelasAuxiliares.JanelaProdutos;
import Frontend.JanelasAuxiliares.JanelaVendas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JanelaSistema extends JFrame {

    // Cores
    private final Color fundo = new Color(245, 240, 255);
    private final Color roxoPastel = new Color(170, 140, 220);
    private final Color branco = Color.WHITE;

    private JFrame janelaAtual;

    public JanelaSistema() {
        setTitle("Dashboard - PetShop Miaujuda");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel Principal
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BorderLayout(15, 15));
        painelPrincipal.setBackground(fundo);
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Topo
        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBackground(fundo);

        JLabel lblTitulo = new JLabel("Bem-vindo ao PetShop Miaujuda");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(roxoPastel);

        painelTopo.add(lblTitulo, BorderLayout.WEST);

        // Cards
        JPanel painelCards = new JPanel(new GridLayout(2, 2, 15, 15));
        painelCards.setBackground(fundo);

        JPanel cardVendas =
                criarCard(
                        "Sistema Vendas",
                        "",
                        ""
                );

        JPanel cardEstoque =
                criarCard(
                        "Gerenciamento de Produtos",
                        "",
                        ""
                );

        JPanel cardReposicao =
                criarCard(
                        "Gerenciamento de Estoque",
                        "",
                        ""
                );

        JPanel cardUltimaVenda =
                criarCard(
                        "Faturamento do dia",
                        String.format("R$ %.2f", JanelaVendas.faturamento),
                        "Valor total vendido"
                );

        painelCards.add(cardVendas);
        painelCards.add(cardEstoque);
        painelCards.add(cardReposicao);
        painelCards.add(cardUltimaVenda);

        // Adicionando
        painelPrincipal.add(painelTopo, BorderLayout.NORTH);
        painelPrincipal.add(painelCards, BorderLayout.CENTER);

        add(painelPrincipal);

        // Evento dos cards

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

    // Criando os cards
    private JPanel criarCard(String titulo, String valor, String descricao) {
        JPanel card = new JPanel();
        card.setLayout(new GridLayout(3, 1));
        card.setBackground(branco);
        card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(roxoPastel,2),
                                            BorderFactory.createEmptyBorder(15,15,15,15)));
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(roxoPastel);

        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel lblDescricao = new JLabel(descricao);
        lblDescricao.setFont(new Font("Arial", Font.PLAIN, 14));

        card.add(lblTitulo);
        card.add(lblValor);
        card.add(lblDescricao);

        // Efeito hover
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(235, 225, 255));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(branco);
            }
        });
        return card;
    }
}