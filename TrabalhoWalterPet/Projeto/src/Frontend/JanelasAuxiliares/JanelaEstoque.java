package Frontend.JanelasAuxiliares;

import Frontend.Janelas.JanelaSistema;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class JanelaEstoque extends JFrame {

    // Tabela
    private JTable tabelaEstoque;
    private DefaultTableModel modeloTabela;

    // Labels
    private JLabel lblTotalProdutos;
    private JLabel lblValorTotal;
    private JLabel lblAlertas;

    // Histórico
    private DefaultListModel<String> modeloHistorico;

    // Cores
    private final Color fundo = new Color(245, 240, 255);
    private final Color roxoPastel = new Color(170, 140, 220);
    private final Color vermelhoClaro = new Color(255, 220, 220);

    public JanelaEstoque() {
        setTitle("Controle de Estoque");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Painel Principal
        JPanel painelPrincipal = new JPanel(new BorderLayout(15,15));
        painelPrincipal.setBackground(fundo);
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        // Topo
        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(fundo);

        JLabel titulo = new JLabel("Gerenciamento de Estoque");
        titulo.setFont(new Font("Arial", Font.BOLD,28));
        titulo.setForeground(roxoPastel);

        JButton btnVoltar = new JButton("Voltar");

        estilizarBotao(btnVoltar);

        btnVoltar.addActionListener(e -> {
            dispose();
            new JanelaSistema().setVisible(true);
        });
        topo.add(titulo, BorderLayout.WEST);
        topo.add(btnVoltar, BorderLayout.EAST);

        // Cards
        JPanel painelCards = new JPanel(new GridLayout(1,3,15,15));
        painelCards.setBackground(fundo);

        lblTotalProdutos = new JLabel("12 Produtos");

        lblValorTotal = new JLabel("R$ 4.580,00");

        lblAlertas = new JLabel("3 Alertas");

        painelCards.add(criarCard("Total de Produtos",lblTotalProdutos));
        painelCards.add(criarCard("Valor Total em Estoque",lblValorTotal));
        painelCards.add(criarCard("Itens com Alerta",lblAlertas));

        // Tabela
        String[] colunas = {"Produto", "Preço", "Quantidade", "Nível"};

        modeloTabela = new DefaultTableModel(colunas, 0);

        tabelaEstoque = new JTable(modeloTabela);
        tabelaEstoque.setRowHeight(30);

        tabelaEstoque.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );

        // ==================================
        // DADOS EXEMPLO
        // ==================================
        adicionarProduto(
                "Ração Golden",
                120.00,
                10
        );

        adicionarProduto(
                "Shampoo Pet",
                35.00,
                3
        );

        adicionarProduto(
                "Coleira Azul",
                20.00,
                15
        );

        adicionarProduto(
                "Brinquedo Bola",
                18.00,
                2
        );

        // ==================================
        // PROGRESS BAR
        // ==================================
        tabelaEstoque
                .getColumnModel()
                .getColumn(3)
                .setCellRenderer(
                        new ProgressRenderer()
                );

        // ==================================
        // ESTOQUE BAIXO VERMELHO
        // ==================================
        tabelaEstoque.setDefaultRenderer(
                Object.class,
                new EstoqueRenderer()
        );

        JScrollPane scrollTabela =
                new JScrollPane(tabelaEstoque);

        // ==================================
        // AÇÕES
        // ==================================
        JPanel painelAcoes =
                new JPanel();

        painelAcoes.setBackground(fundo);

        JButton btnAdicionar =
                new JButton("Adicionar Quantidade");

        JButton btnRemover =
                new JButton("Remover Quantidade");

        estilizarBotao(btnAdicionar);

        estilizarBotao(btnRemover);

        painelAcoes.add(btnAdicionar);

        painelAcoes.add(btnRemover);

        // ==================================
        // HISTÓRICO
        // ==================================
        JPanel painelHistorico =
                new JPanel(new BorderLayout());

        painelHistorico.setBackground(Color.WHITE);

        painelHistorico.setBorder(
                BorderFactory.createTitledBorder(
                        "Histórico de Movimentações"
                )
        );

        modeloHistorico =
                new DefaultListModel<>();

        JList<String> listaHistorico =
                new JList<>(modeloHistorico);

        painelHistorico.add(
                new JScrollPane(listaHistorico),
                BorderLayout.CENTER
        );

        // ==================================
        // EVENTOS
        // ==================================
        btnAdicionar.addActionListener(e -> {

            movimentarEstoque(true);
        });

        btnRemover.addActionListener(e -> {

            movimentarEstoque(false);
        });

        // ==================================
        // CENTRO
        // ==================================
        JPanel centro =
                new JPanel(new BorderLayout(15,15));

        centro.setBackground(fundo);

        centro.add(scrollTabela, BorderLayout.CENTER);

        centro.add(painelAcoes, BorderLayout.SOUTH);

        // ==================================
        // ADICIONAR COMPONENTES
        // ==================================
        painelPrincipal.add(topo, BorderLayout.NORTH);

        painelPrincipal.add(painelCards, BorderLayout.CENTER);

        painelPrincipal.add(centro, BorderLayout.SOUTH);

        painelPrincipal.add(
                painelHistorico,
                BorderLayout.EAST
        );

        add(painelPrincipal);
    }

    // ======================================
    // ADICIONAR PRODUTO
    // ======================================
    private void adicionarProduto(
            String nome,
            double preco,
            int quantidade
    ) {

        modeloTabela.addRow(new Object[]{

                nome,
                preco,
                quantidade,
                quantidade * 10
        });
    }

    // ======================================
    // MOVIMENTAÇÃO
    // ======================================
    private void movimentarEstoque(
            boolean adicionar
    ) {

        int linha =
                tabelaEstoque.getSelectedRow();

        if(linha == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Selecione um produto!"
            );

            return;
        }

        String valor =
                JOptionPane.showInputDialog(
                        this,
                        "Quantidade:"
                );

        if(valor == null) return;

        int quantidade =
                Integer.parseInt(valor);

        int atual =
                Integer.parseInt(
                        modeloTabela
                                .getValueAt(
                                        linha,
                                        2
                                ).toString()
                );

        int novoValor;

        if(adicionar) {

            novoValor = atual + quantidade;

        } else {

            novoValor = atual - quantidade;

            if(novoValor < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Quantidade inválida!"
                );

                return;
            }
        }

        modeloTabela.setValueAt(
                novoValor,
                linha,
                2
        );

        modeloTabela.setValueAt(
                novoValor * 10,
                linha,
                3
        );

        String nome =
                modeloTabela
                        .getValueAt(
                                linha,
                                0
                        ).toString();

        modeloHistorico.addElement(

                (adicionar
                        ? "ENTRADA: "
                        : "SAÍDA: ")

                        + nome

                        + " ("

                        + quantidade

                        + ")"
        );
    }

    // ======================================
    // CARD
    // ======================================
    private JPanel criarCard(
            String titulo,
            JLabel valor
    ) {

        JPanel card =
                new JPanel(new GridLayout(2,1));

        card.setBackground(Color.WHITE);

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

        JLabel lblTitulo =
                new JLabel(titulo);

        lblTitulo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        lblTitulo.setForeground(roxoPastel);

        valor.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        24
                )
        );

        card.add(lblTitulo);

        card.add(valor);

        return card;
    }

    // ======================================
    // ESTILO BOTÃO
    // ======================================
    private void estilizarBotao(
            JButton botao
    ) {

        botao.setBackground(roxoPastel);

        botao.setForeground(Color.WHITE);

        botao.setFocusPainted(false);

        botao.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );
    }

    // ======================================
    // RENDER ESTOQUE BAIXO
    // ======================================
    class EstoqueRenderer
            extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column
        ) {

            Component c =
                    super.getTableCellRendererComponent(
                            table,
                            value,
                            isSelected,
                            hasFocus,
                            row,
                            column
                    );

            int quantidade =
                    Integer.parseInt(
                            table.getValueAt(
                                    row,
                                    2
                            ).toString()
                    );

            if(quantidade < 5) {

                c.setBackground(vermelhoClaro);

            } else {

                c.setBackground(Color.WHITE);
            }

            return c;
        }
    }

    // ======================================
    // RENDER PROGRESS BAR
    // ======================================
    class ProgressRenderer
            extends JProgressBar
            implements javax.swing.table.TableCellRenderer {

        public ProgressRenderer() {

            setMinimum(0);

            setMaximum(100);
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column
        ) {

            int quantidade =
                    Integer.parseInt(
                            table.getValueAt(
                                    row,
                                    2
                            ).toString()
                    );

            setValue(
                    Math.min(
                            quantidade * 10,
                            100
                    )
            );

            return this;
        }
    }
}
