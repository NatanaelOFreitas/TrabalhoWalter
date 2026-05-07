package Frontend.Janelas;

import Frontend.Paineis.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaSistema extends JFrame {

    PainelEstoque Estoque = new PainelEstoque();
    PainelProdutos Produtos = new PainelProdutos();
    PainelRelatorios Relatorios = new PainelRelatorios();
    PainelVendas Vendas = new PainelVendas();

    public JanelaSistema() {
        setTitle("PetShop Miaujuda");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        PainelEstoque Estoque = new PainelEstoque();
        PainelProdutos Produtos = new PainelProdutos();
        PainelRelatorios Relatorios = new PainelRelatorios();
        PainelVendas Vendas = new PainelVendas();

        setLayout(new GridLayout(2, 2, 10, 10));  // 2 linhas, 2 colunas, espaços entre painéis
        add(Estoque);
        add(Produtos);
        add(Relatorios);
        add(Vendas);

    }
}
