package Frontend.JanelasAuxiliares;

import Frontend.Janelas.JanelaSistema;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.text.DecimalFormat;

public class JanelaVendas extends JFrame {

    // Tabelas
    private JTable tabelaProdutos;
    private JTable tabelaCarrinho;

    private DefaultTableModel modeloProdutos;
    private DefaultTableModel modeloCarrinho;

    // Componentes
    private JTextField txtBusca;
    private JSpinner spinnerQuantidade;
    private JLabel lblTotal;

    // Cores
    private final Color fundo = new Color(245, 240, 255);
    private final Color roxoPastel = new Color(170, 140, 220);

    // Formatador
    private final DecimalFormat df = new DecimalFormat("R$ #,##0.00");

    public JanelaVendas() {
        setTitle("Tela de Vendas");
        setSize(1200, 650);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel(new BorderLayout(15, 15));
        painelPrincipal.setBackground(fundo);

        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        // Topo
        JPanel topo = new JPanel(new BorderLayout());
        topo.setBackground(fundo);

        JLabel titulo = new JLabel("Sistema de Vendas");
        titulo.setFont( new Font("Arial",Font.BOLD,28));
        titulo.setForeground(roxoPastel);

        JButton btnVoltar = new JButton("Voltar");
        estilizarBotao(btnVoltar);

        btnVoltar.addActionListener(e -> {
            dispose();
        });
        topo.add(titulo, BorderLayout.WEST);
        topo.add(btnVoltar, BorderLayout.EAST);

        // Centro
        JPanel centro = new JPanel(new GridLayout(1,2,15,15));
        centro.setBackground(fundo);

        // Produtos
        JPanel painelProdutos =  new JPanel(new BorderLayout(10,10));
        painelProdutos.setBackground(Color.WHITE);
        painelProdutos.setBorder(BorderFactory.createTitledBorder("Produtos Disponíveis"));

        JPanel painelBusca =  new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBusca.setBackground(Color.WHITE);

        txtBusca = new JTextField(20);

        JButton btnBuscar = new JButton("Buscar");
        estilizarBotao(btnBuscar);

        painelBusca.add(new JLabel("Buscar:"));
        painelBusca.add(txtBusca);
        painelBusca.add(btnBuscar);

        String[] colunasProdutos = {"Nome", "Preço", "Quantidade"};

        modeloProdutos =  new DefaultTableModel(colunasProdutos,0);

        tabelaProdutos = new JTable(modeloProdutos);
        tabelaProdutos.setRowHeight(25);

        JScrollPane scrollProdutos = new JScrollPane(tabelaProdutos);

        // Exemplos iniciais
        modeloProdutos.addRow(new Object[]{"Ração Golden", 120.00, 10});

        modeloProdutos.addRow(new Object[]{"Shampoo Pet", 35.50, 15});

        modeloProdutos.addRow(new Object[]{"Coleira Azul", 25.90, 20});

        modeloProdutos.addRow(new Object[]{"Brinquedo Osso", 18.00, 8});

        // Ordenar e pesquisar
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloProdutos);
        tabelaProdutos.setRowSorter(sorter);

        btnBuscar.addActionListener(e -> {
            String texto =
                    txtBusca.getText();
            if(texto.trim().isEmpty()) {
                sorter.setRowFilter(null);
            }
            else {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
            }
        });

        // Adicionar no carrinho
        JPanel painelAdicionar = new JPanel(new FlowLayout());
        painelAdicionar.setBackground(Color.WHITE);

        spinnerQuantidade = new JSpinner(new SpinnerNumberModel(  1, 1,999,1));

        JButton btnAdicionar = new JButton("Adicionar ao Carrinho");
        estilizarBotao(btnAdicionar);

        painelAdicionar.add(new JLabel("Quantidade:"));
        painelAdicionar.add(spinnerQuantidade);
        painelAdicionar.add(btnAdicionar);

        painelProdutos.add(painelBusca, BorderLayout.NORTH);
        painelProdutos.add(scrollProdutos, BorderLayout.CENTER);
        painelProdutos.add(painelAdicionar, BorderLayout.SOUTH);

        // Carrinho
        JPanel painelCarrinho = new JPanel(new BorderLayout(10,10));
        painelCarrinho.setBackground(Color.WHITE);
        painelCarrinho.setBorder(BorderFactory.createTitledBorder("Carrinho"));

        String[] colunasCarrinho = {"Produto", "Qtd", "Preço", "Subtotal"};

        modeloCarrinho = new DefaultTableModel(colunasCarrinho, 0);

        tabelaCarrinho =new JTable(modeloCarrinho);
        tabelaCarrinho.setRowHeight(25);

        JScrollPane scrollCarrinho = new JScrollPane(tabelaCarrinho);

        // Total
        lblTotal = new JLabel("Total: R$ 0,00");
        lblTotal.setFont(new Font("Arial",Font.BOLD,24));
        lblTotal.setForeground(new Color(46, 204, 113));

        // Pagamento
        JPanel painelPagamento = new JPanel(new GridLayout(6,1,10,10));
        painelPagamento.setBackground(Color.WHITE);

        JComboBox<String> comboPagamento = new JComboBox<>();

        comboPagamento.addItem("Dinheiro");
        comboPagamento.addItem("Cartão");
        comboPagamento.addItem("PIX");

        JButton btnRemover = new JButton("Remover Item");
        JButton btnCancelar = new JButton("Cancelar Venda");
        JButton btnFinalizar = new JButton("Finalizar Venda");

        estilizarBotao(btnRemover);
        estilizarBotao(btnCancelar);
        estilizarBotao(btnFinalizar);

        painelPagamento.add(lblTotal);

        painelPagamento.add(new JLabel("Forma de Pagamento:"));
        painelPagamento.add(comboPagamento);
        painelPagamento.add(btnRemover);
        painelPagamento.add(btnCancelar);
        painelPagamento.add(btnFinalizar);
        painelCarrinho.add(scrollCarrinho, BorderLayout.CENTER);
        painelCarrinho.add(painelPagamento, BorderLayout.SOUTH);

        centro.add(painelProdutos);
        centro.add(painelCarrinho);

        painelPrincipal.add(topo, BorderLayout.NORTH);
        painelPrincipal.add(centro, BorderLayout.CENTER);

        add(painelPrincipal);

        // Eventos
        btnAdicionar.addActionListener(e -> {
            adicionarCarrinho();
        });

        btnRemover.addActionListener(e -> {
            removerCarrinho();
        });

        btnCancelar.addActionListener(e -> {
            cancelarVenda();
        });

        btnFinalizar.addActionListener(e -> {
            finalizarVenda(comboPagamento.getSelectedItem().toString());
        });
    }

    // Adicionar no carrinho
    private void adicionarCarrinho() {
        int linha = tabelaProdutos.getSelectedRow();
        if(linha == -1) {
            JOptionPane.showMessageDialog(this,"Selecione um produto!");
            return;
        }
        String nome = tabelaProdutos.getValueAt(linha,0).toString();
        double preco = Double.parseDouble(tabelaProdutos.getValueAt(linha,1).toString());
        int quantidade = (Integer)spinnerQuantidade.getValue();
        double subtotal = preco * quantidade;

        modeloCarrinho.addRow(new Object[]{nome, quantidade, preco, subtotal});

        atualizarTotal();
    }

    // Remover item
    private void removerCarrinho() {
        int linha = tabelaCarrinho.getSelectedRow();
        if(linha == -1) {
            JOptionPane.showMessageDialog( this,"Selecione um item!");
            return;
        }
        modeloCarrinho.removeRow(linha);

        atualizarTotal();
    }

    // Cancelar venda
    private void cancelarVenda() {
        modeloCarrinho.setRowCount(0);

        atualizarTotal();

        JOptionPane.showMessageDialog(this,"Venda cancelada!");
    }

    // Finalizar venda
    private void finalizarVenda(String pagamento) {
        if(modeloCarrinho.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,"Carrinho vazio!");
            return;
        }
        double total = 0;

        StringBuilder comprovante = new StringBuilder();

        comprovante.append("===== COMPROVANTE =====\n\n");
        for(int i = 0;i < modeloCarrinho.getRowCount();i++) {
            String nome = modeloCarrinho.getValueAt(i,0).toString();
            int qtd = Integer.parseInt(modeloCarrinho.getValueAt(i,1).toString());
            double subtotal = Double.parseDouble(modeloCarrinho.getValueAt(i,3).toString());
            total += subtotal;

            comprovante.append(nome + " x" + qtd + " - " + df.format(subtotal) + "\n");
        }

        comprovante.append("\nPagamento: " + pagamento);
        comprovante.append("\nTotal: " + df.format(total));

        JOptionPane.showMessageDialog(this, comprovante.toString(),"Venda Finalizada",JOptionPane.INFORMATION_MESSAGE);

        modeloCarrinho.setRowCount(0);

        atualizarTotal();
    }

    // Atualizar total
    private void atualizarTotal() {
        double total = 0;
        for(int i = 0;i < modeloCarrinho.getRowCount();i++) {
            total += Double.parseDouble(modeloCarrinho.getValueAt(i,3).toString());
        }
        lblTotal.setText("Total: " + df.format(total));
    }

    // Estilo do botão
    private void estilizarBotao(JButton botao) {
        botao.setBackground(roxoPastel);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setFont(new Font("Arial", Font.BOLD,14));
    }
}