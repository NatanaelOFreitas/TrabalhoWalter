package Frontend.Paineis;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PainelProdutos extends JFrame{
    private JTable tabelaProdutos;
    private DefaultTableModel modeloTabela;
    private JTextField txtBusca;

    // Cores
    private final Color fundo = new Color(245, 240, 255);
    private final Color roxoPastel = new Color(170, 140, 220);

    public PainelProdutos() {
        setTitle("Gerenciamento de Produtos");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBackground(fundo);
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Topo da tela
        JPanel painelTopo = new JPanel(new BorderLayout(10, 10));
        painelTopo.setBackground(fundo);

        // Busca
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBusca.setBackground(fundo);
        txtBusca = new JTextField(20);
        JButton btnPesquisar = new JButton("Pesquisar");
        estilizarBotao(btnPesquisar);
        painelBusca.add(new JLabel("Buscar Produto:"));
        painelBusca.add(txtBusca);
        painelBusca.add(btnPesquisar);

        // Novo produto
        JButton btnNovoProduto = new JButton("Novo Produto");
        estilizarBotao(btnNovoProduto);
        painelTopo.add(painelBusca, BorderLayout.WEST);
        painelTopo.add(btnNovoProduto, BorderLayout.EAST);

        // Tabela
        String[] colunas = {"Nome", "Preço", "Quantidade", "Descrição"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabelaProdutos = new JTable(modeloTabela);
        tabelaProdutos.setRowHeight(25);
        tabelaProdutos.setFont(new Font("Arial", Font.PLAIN, 14));

        // Ordenação
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloTabela);
        tabelaProdutos.setRowSorter(sorter);
        JScrollPane scrollPane = new JScrollPane(tabelaProdutos);

        // Ações dos botões
        JPanel painelAcoes = new JPanel();
        painelAcoes.setBackground(fundo);

        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");

        estilizarBotao(btnEditar);
        estilizarBotao(btnExcluir);

        painelAcoes.add(btnEditar);
        painelAcoes.add(btnExcluir);

        // Evento de cadastro
        btnNovoProduto.addActionListener(e -> abrirModalProduto(false));

        // Evento de editar
        btnEditar.addActionListener(e -> editarProduto());

        // Evento de excluir
        btnExcluir.addActionListener(e -> excluirProduto());

        btnPesquisar.addActionListener((ActionEvent e) -> {
            String texto = txtBusca.getText();
            if(texto.trim().length() == 0)  {
                sorter.setRowFilter(null);
            }
            else {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
            }
        });

        painelPrincipal.add(painelTopo, BorderLayout.NORTH);
        painelPrincipal.add(scrollPane, BorderLayout.CENTER);
        painelPrincipal.add(painelAcoes, BorderLayout.SOUTH);

        add(painelPrincipal);
    }

    private void abrirModalProduto(boolean editando) {
        JDialog modal = null; new JDialog(this, true);
        modal.setTitle(editando ? "Editar Produto" : "Novo Produto");
        modal.setSize(400, 350);
        modal.setLocationRelativeTo(this);

        JPanel painel = new JPanel(new GridLayout(5, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        painel.setBackground(fundo);

        JTextField txtNome = new JTextField();
        JTextField txtPreco = new JTextField();
        JTextField txtQuantidade = new JTextField("1");
        JTextArea txtDescricao = new JTextArea();

        painel.add(new JLabel("Nome do Produto:"));
        painel.add(txtNome);

        painel.add(new JLabel("Preço Unitário:"));
        painel.add(txtPreco);

        painel.add(new JLabel("Quantidade:"));
        painel.add(txtQuantidade);

        painel.add(new JLabel("Descrição:"));
        painel.add(new JScrollPane(txtDescricao));

        // Botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setBackground(fundo);
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        JButton btnLimpar = new JButton("Limpar");

        estilizarBotao(btnSalvar);
        estilizarBotao(btnCancelar);
        estilizarBotao(btnLimpar);

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnCancelar);

        // Salvar
        btnSalvar.addActionListener(e -> {
            String nome = txtNome.getText();
            String preco = txtPreco.getText();
            String quantidade = txtQuantidade.getText();
            String descricao = txtDescricao.getText();
            if (nome.isEmpty() || preco.isEmpty()) {
                JOptionPane.showMessageDialog(modal, "Nome e preço são obrigatórios!");
                return;
            }
            modeloTabela.addRow(new Object[]{nome, preco, quantidade, descricao});
            modal.dispose();
        });

        // Cancelar
        btnCancelar.addActionListener(e -> modal.dispose());

        // Limpar
        btnLimpar.addActionListener(e -> {
            txtNome.setText("");
            txtPreco.setText("");
            txtQuantidade.setText("1");
            txtDescricao.setText("");
        });

        modal.setLayout(new BorderLayout());
        modal.add(painel, BorderLayout.CENTER);
        modal.add(painelBotoes, BorderLayout.SOUTH);
        modal.setVisible(true);
    }
    // Editar
    private void editarProduto() {
        int linha = tabelaProdutos.getSelectedRow();
        if(linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto!");
            return;
        }
        linha = tabelaProdutos.convertRowIndexToModel(linha);

        String nome = modeloTabela.getValueAt(linha, 0).toString();
        String preco = modeloTabela.getValueAt(linha, 1).toString();
        String quantidade = modeloTabela.getValueAt(linha, 2).toString();
        String descricao = modeloTabela.getValueAt(linha, 3).toString();

        JDialog modal = new JDialog(this, true);

        modal.setTitle("Editar Produto");
        modal.setSize(400, 350);
        modal.setLocationRelativeTo(this);

        JPanel painel = new JPanel(new GridLayout(5,2,10,10));
        painel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        painel.setBackground(fundo);

        JTextField txtNome = new JTextField(nome);
        JTextField txtPreco = new JTextField(preco);
        JTextField txtQuantidade = new JTextField(quantidade);
        JTextArea txtDescricao = new JTextArea(descricao);

        painel.add(new JLabel("Nome:"));
        painel.add(txtNome);

        painel.add(new JLabel("Preço:"));
        painel.add(txtPreco);

        painel.add(new JLabel("Quantidade:"));
        painel.add(txtQuantidade);

        painel.add(new JLabel("Descrição:"));
        painel.add(new JScrollPane(txtDescricao));

        JPanel painelBotoes = new JPanel();
        painelBotoes.setBackground(fundo);

        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");

        estilizarBotao(btnSalvar);
        estilizarBotao(btnCancelar);

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnCancelar);

        int linhaFinal = linha;

        btnSalvar.addActionListener(e -> {
            modeloTabela.setValueAt(txtNome.getText(), linhaFinal, 0);
            modeloTabela.setValueAt(txtPreco.getText(), linhaFinal, 1);
            modeloTabela.setValueAt(txtQuantidade.getText(), linhaFinal, 2);
            modeloTabela.setValueAt(txtDescricao.getText(), linhaFinal, 3);
            modal.dispose();
        });
        btnCancelar.addActionListener(e -> modal.dispose());
        modal.setLayout(new BorderLayout());
        modal.add(painel, BorderLayout.CENTER);
        modal.add(painelBotoes, BorderLayout.SOUTH);
        modal.setVisible(true);
    }
    // Excluir
    private void excluirProduto() {
        int linha = tabelaProdutos.getSelectedRow();
        if(linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto!");
            return;
        }
        int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir?",
                                                                                "Confirmação", JOptionPane.YES_NO_OPTION);
        if(confirmacao == JOptionPane.YES_NO_OPTION) {
            linha = tabelaProdutos.convertRowIndexToModel(linha);
            modeloTabela.removeRow(linha);
        }
    }
    // Estilo dos botões
    private void estilizarBotao(JButton botao) {
        botao.setBackground(roxoPastel);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setFont(new Font("Arial", Font.BOLD, 14));
    }
}
